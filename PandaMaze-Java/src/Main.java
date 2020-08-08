import Model.*;
import View.View;
import View.MainScreenController;
import ViewModel.ViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);
        model.addObserver(viewModel);
        primaryStage.setTitle("Panda Maze! - Panda Productions");
        primaryStage.setWidth(1150);
        primaryStage.setHeight(750);
        FXMLLoader FXMainMenu = new FXMLLoader(getClass().getResource("View/mainScreen.fxml"));
        FXMLLoader FXPlaying = new FXMLLoader(getClass().getResource("View/MyView.fxml"));
        FXMLLoader FXWinning = new FXMLLoader(getClass().getResource("View/WinMenu.fxml"));
        Parent PMenu = FXMainMenu.load();
        Parent PPlaying = FXPlaying.load();
        Parent PWinning = FXWinning.load();
//        Parent root = fxmlLoader.load(getClass().getResource("View/MyView.fxml").openStream());
//        Parent root = fxmlLoader.load(getClass().getResource("View/mainScreen.fxml").openStream());
        Scene sMenu = new Scene(PMenu, 1150   , 750);
        Scene sPlaying = new Scene(PPlaying, 1150   , 750);
        Scene sWinning = new Scene(PWinning, 1150   , 750);
//        sMenu.getStylesheets().add(getClass().getResource("View/MainScreen.css").toExternalForm());
        sMenu.getStylesheets().add("MainScreen.css");
        sPlaying.getStylesheets().add("View/View.css");
        sWinning.getStylesheets().add("View/WinMenu.css");
//        sPlaying.getStylesheets().add(getClass().getResource("View/View.css").toExternalForm());
//        sWinning.getStylesheets().add(getClass().getResource("View/WinMenu.css").toExternalForm());
//        sWinning.getStylesheets().add(getClass().getResource("View/View.css").toExternalForm());
//        scene.getStylesheets().add(getClass().getResource("View/MainScreen.css").toExternalForm());
        MainScreenController MenuController = FXMainMenu.getController();
        View PlayingController = FXPlaying.getController();
        MainScreenController WinningController = FXWinning.getController();
        MenuController.initialize(sPlaying,primaryStage,sMenu);
        PlayingController.initialize(viewModel,primaryStage,sPlaying, sWinning,WinningController);
        WinningController.initialize(sPlaying,primaryStage,sWinning);
        viewModel.addObserver(PlayingController);
        setStageCloseEvent(primaryStage, model);
        primaryStage.setScene(sMenu);
        primaryStage.show();
    }

    private void setStageCloseEvent(Stage primaryStage, Model model) {
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // Close the program properlyr
                model.close();
            } else {
                // ... user chose CANCEL or closed the dialog
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
