package Model;

import View.MazeDisplayer;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;


public interface IModel {
    //Maze
    void generateMaze(int width, int height);
    Solution SolveMaze();
    int[][] getMaze();

    //Character
    void moveCharacter(KeyCode movement, MazeDisplayer Displayer);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    boolean LeavesFound();
    public void pushedSave(File file)throws IOException;
    public void pushedLoad(File file) throws IOException, ClassNotFoundException;

    //
    void close();
}
