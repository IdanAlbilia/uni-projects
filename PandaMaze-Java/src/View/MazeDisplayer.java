package View;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int End = 1;
    private int Start = 1;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    public static MediaPlayer mediaPlayer;
    public static boolean Sound = false;

    public void setMaze(int[][] maze) {
        this.maze = maze;
        redraw();
    }

    public static void playMusic(int Order) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.pause();
            Sound = false;
        }
        String PathtoSound = "resources/Sounds/Panda Music.mp3";
        if (Order == 1) {
            Sound = true;
            Media player = new Media(Paths.get(PathtoSound).toUri().toString());
            mediaPlayer = new MediaPlayer(player);
            mediaPlayer.play();
        }
        if (Order == 2) {
            mediaPlayer.stop();
            mediaPlayer.pause();
            Sound = false;
        }
    }

    public int[][] getMaze()
    {
        return maze;
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public void ShowSolution(Solution sol) {
        ArrayList<AState> solpath = sol.getSolutionPath();
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;
        for (int i = 0; i < solpath.size(); i++) {
            MazeState tmp = (MazeState) solpath.get(i);
            Position tmpPos = tmp.getCurrPositon();
        }
    }
//    public void ShowSolution(Solution sol) {
//        ArrayList<AState> solpath = sol.getSolutionPath();
//        double canvasHeight = getHeight();
//        double canvasWidth = getWidth();
//        double cellHeight = canvasHeight / maze.length;
//        double cellWidth = canvasWidth / maze[0].length;
//        GraphicsContext GC2d = getGraphicsContext2D();
//        GC2d.setFill(Color.RED);
//        for (int i = 0; i < solpath.size(); i++) {
//            MazeState tmp = (MazeState) solpath.get(i);
//            Position tmpPos = tmp.getCurrPositon();
//            GC2d.fillRect(tmpPos.getColumnIndex() * cellWidth, (tmpPos.getRowIndex()) * cellHeight, cellWidth, cellHeight);
//        }
//    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                Image Leaves = new Image(new FileInputStream(ImageFileNameEnd.get()));
                Image Path = new Image(new FileInputStream(ImageFileNamePath.get()));
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        if (maze[i][j] == 4) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(Leaves, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        if (maze[i][j] == 5) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(Path, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }

                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameEnd = new SimpleStringProperty();
    private StringProperty ImageFileNamePath = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNamePath(String imageFileNamePath) {
        this.ImageFileNamePath.set(imageFileNamePath);
    }

    public String getImageFileNamePath() {
        return ImageFileNamePath.get();
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameEnd() {
        return ImageFileNameEnd.get();
    }

    public void setImageFileNameEnd(String imageFileNameEnd) {
        this.ImageFileNameEnd.set(imageFileNameEnd);
    }
    //endregion

}
