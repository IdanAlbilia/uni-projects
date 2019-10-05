package ViewModel;

import Model.IModel;
import View.MazeDisplayer;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class ViewModel extends Observable implements Observer {

    private IModel model;

    public ViewModel(IModel model){
        this.model = model;
    }

    //<editor-fold desc="Take care Observable">
    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            //Notify my observer (View) that I have changed
            setChanged();
            notifyObservers();
        }
    }
    //</editor-fold>

    //<editor-fold desc="ViewModel Functionality">
    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public Solution SolveMaze(){
        Solution MazeSolution = model.SolveMaze();
        return MazeSolution;
    }

    public void moveCharacter(KeyCode movement, MazeDisplayer Displayer){
        model.moveCharacter(movement,Displayer);
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public int[][] getMaze() {
        int[][] test = model.getMaze();
        return test;
    }

    public boolean LeavesFound(){
        return model.LeavesFound();
    }

    public void pushedSave(File SaveMe) throws IOException {
        model.pushedSave(SaveMe);
    }

    public void pushedLoad(File LoadMe) throws IOException, ClassNotFoundException {
        model.pushedLoad(LoadMe);
    }


    public int getCharacterPositionRow() {
        //return characterPositionRowIndex;
        return model.getCharacterPositionRow();
    }

    public int getCharacterPositionColumn() {
        //return characterPositionColumnIndex;
        return model.getCharacterPositionColumn();
    }
    //</editor-fold>
}
