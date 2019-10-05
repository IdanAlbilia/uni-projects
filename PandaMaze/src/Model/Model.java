package Model;

import Client.Client;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.IServerStrategy;
import Server.ServerStrategySolveSearchProblem;
import Server.Server;
import Client.IClientStrategy;
import Server.ServerStrategyGenerateMaze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import com.sun.rowset.internal.Row;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;

//import IO.MyCompressorOutputStream;
//import java.net.URL;
//import java.nio.file.Files;
//import java.util.Random;
//import java.util.ResourceBundle;
//import IO.MyDecompressorInputStream;
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.Position;
//import javafx.scene.input.KeyCode;
//import Client.Client;
//import Client.IClientStrategy;
//import IO.MyDecompressorInputStream;
//import Server.Server;
//import Server.ServerStrategyGenerateMaze;
//import Server.ServerStrategySolveSearchProblem;
//import algorithms.mazeGenerators.EmptyMazeGenerator;
//import algorithms.mazeGenerators.Maze;
//import algorithms.search.AState;
//import algorithms.search.Solution;
//import Server.IServerStrategy;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import org.omg.CORBA.Current;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Model extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private Maze maze;
    int[][] AdjustedMaze;
    static Server MazeGen;
    static Server MazeSol;
    private boolean ProjectingSolution;
    private Position currPos;
    private Position StartPos;
    private Position EndPos;
    private Solution mazeSolution;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    public Model() {
    }

    public static void Connect(IServerStrategy server){
        if(server instanceof ServerStrategyGenerateMaze) {
            MazeGen = new Server(5400, 1000, new ServerStrategyGenerateMaze());
            MazeGen.start();
        }
        else if(server instanceof ServerStrategySolveSearchProblem){
            MazeSol = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
            MazeSol.start();
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        if (!ProjectingSolution) {
            IServerStrategy server = new ServerStrategySolveSearchProblem();
            Connect(server);
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            toServer.writeObject(maze); //send maze to server
                            toServer.flush();
                            mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.communicateWithServer();
                ShowProjection();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            UndoProjection();
            if (MazeSol != null)
                MazeSol.stop();
        }

    }

    private void ShowProjection(){
        ArrayList<AState> SolPath = mazeSolution.getSolutionPath();
        for (int i = 0; i < SolPath.size()-1; i++) {
            MazeState tmp = (MazeState) SolPath.get(i);
            Position tmpPos = tmp.getCurrPositon();
            AdjustedMaze[tmpPos.getRowIndex()][tmpPos.getColumnIndex()] = 5;
        }
        ProjectingSolution = true;
    }

    private void UndoProjection(){
        ArrayList<AState> SolPath = mazeSolution.getSolutionPath();
        for (int i = 0; i < SolPath.size()-1; i++) {
            MazeState tmp = (MazeState) SolPath.get(i);
            Position tmpPos = tmp.getCurrPositon();
            AdjustedMaze[tmpPos.getRowIndex()][tmpPos.getColumnIndex()] = 0;
        }
        ProjectingSolution = false;
    }



    public void pushedSave(File file) throws IOException {
        byte [] ByteMaze = maze.toByteArray();
        byte [] saveMe = new byte[maze.toByteArray().length + 2];
        for(int i=0;i<ByteMaze.length;i++){
            saveMe[i]=ByteMaze[i];
        }
        saveMe[ByteMaze.length]= (byte)currPos.getRowIndex();
        saveMe[ByteMaze.length+1]= (byte)currPos.getColumnIndex();
        try{
            file.createNewFile();
            FileOutputStream SaveMe = new FileOutputStream(file);
            SaveMe.write(saveMe);
        }
        catch (IOException e){}

    }

    public void pushedLoad(File file) throws IOException, ClassNotFoundException {
        byte[] LoadedBytes = Files.readAllBytes(file.toPath());
        byte[] ByteMaze = new byte[LoadedBytes.length-2];
        for (int i = 0; i < ByteMaze.length; i++) {
            ByteMaze[i] = LoadedBytes[i];
        }
        Maze Lmaze = new Maze(ByteMaze);
        this.maze = Lmaze;
        EndPos = maze.getGoalPosition();
        AdjustedMaze = maze.getMaze();
        AdjustedMaze[EndPos.getRowIndex()][EndPos.getColumnIndex()] = 4;
        characterPositionRow = (int)LoadedBytes[ByteMaze.length];
        characterPositionColumn = (int)LoadedBytes[ByteMaze.length+1];
        setChanged();
        notifyObservers();
    }



    private void CommunicateWithServer_MazeGenerating(int row,int col) {
        if (MazeGen!=null)
            MazeGen.stop();
        IServerStrategy server =new ServerStrategyGenerateMaze();
        Connect(server);
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[col*row +12]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        currPos = maze.getStartPosition();
                        StartPos = maze.getStartPosition();
                        EndPos= maze.getGoalPosition();
                        AdjustedMaze = maze.getMaze();
                        AdjustedMaze[EndPos.getRowIndex()][EndPos.getColumnIndex()] = 4;
                        characterPositionColumn = currPos.getColumnIndex();
                        characterPositionRow = currPos.getRowIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }



    //<editor-fold desc="Getters">
    @Override
    public int[][] getMaze() {
        return maze.getMaze();
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    @Override
    public void close() {
        try {
            if (MazeGen != null)
                MazeGen.stop();
            if (MazeSol != null)
                MazeSol.stop();
            threadPool.shutdown();
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="Model Functionality">
    @Override
    public void generateMaze(int width, int height) {
        //Generate maze
        ProjectingSolution = false;
        CommunicateWithServer_MazeGenerating(width, height);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged(); //Raise a flag that I have changed
        notifyObservers(maze); //Wave the flag so the observers will notice
    }

    public Solution SolveMaze() {
        //Generate maze
        CommunicateWithServer_SolveSearchProblem();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged(); //Raise a flag that I have changed
        notifyObservers(); //Wave the flag so the observers will notice
        return mazeSolution;
    }

    public boolean LeavesFound() {
        if (characterPositionRow == maze.getGoalPosition().getRowIndex() && characterPositionColumn == maze.getGoalPosition().getColumnIndex())
            return true;
        return false;
    }

    @Override
    public void moveCharacter(KeyCode movement,MazeDisplayer Displayer) {
        int[][] CurrentMaze = Displayer.getMaze();
        switch (movement) {
            case NUMPAD8:
                if (characterPositionRow>0 && CurrentMaze[characterPositionRow-1][characterPositionColumn]!=1)
                    characterPositionRow--;
                break;
            case NUMPAD2:
                if (characterPositionRow<CurrentMaze.length-1 && CurrentMaze[characterPositionRow+1][characterPositionColumn]!=1)
                    characterPositionRow++;
                break;
            case NUMPAD6:
                if (characterPositionColumn<CurrentMaze[0].length-1 && CurrentMaze[characterPositionRow][characterPositionColumn+1]!=1)
                    characterPositionColumn++;
                break;
            case NUMPAD4:
                if (characterPositionColumn>0 && CurrentMaze[characterPositionRow][characterPositionColumn-1]!=1)
                    characterPositionColumn--;
                break;
            case NUMPAD7:
                if (characterPositionColumn>0 && characterPositionRow>0 && CurrentMaze[characterPositionRow-1][characterPositionColumn-1]!=1)
                {
                    characterPositionColumn--;
                    characterPositionRow--;
                }
                break;
            case NUMPAD9:
                if (characterPositionRow>0 && characterPositionColumn<CurrentMaze[0].length-1 && CurrentMaze[characterPositionRow-1][characterPositionColumn+1]!=1)
                {
                    characterPositionColumn++;
                    characterPositionRow--;
                }
                break;
            case NUMPAD1:
                if (characterPositionRow<CurrentMaze.length-1 && characterPositionColumn>0 &&  CurrentMaze[characterPositionRow+1][characterPositionColumn-1]!=1)
                {
                    characterPositionColumn--;
                    characterPositionRow++;
                }
                break;
            case NUMPAD3:
                if (characterPositionRow<CurrentMaze.length-1 && characterPositionColumn<CurrentMaze[0].length-1 && CurrentMaze[characterPositionRow+1][characterPositionColumn+1]!=1)
                {
                    characterPositionColumn++;
                    characterPositionRow++;
                }
                break;
            case HOME:
                characterPositionRow = 0;
                characterPositionColumn = 0;
        }
        Position newStart = new Position(characterPositionRow,characterPositionColumn);
        maze.setStart(newStart);
        setChanged();
        notifyObservers(/*Can forward an Object*/);
    }
    //</editor-fold>

}
