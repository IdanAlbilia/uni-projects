package View;

import ViewModel.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class MainScreenController {

    @FXML
    private ViewModel viewModel;
    private Scene mainScene;
    private Stage mainStage;
    private Scene NextScene;
    public static MediaPlayer mediaPlayer;
    public static boolean Sound = false;



    public void initialize(Scene nextScene, Stage mainStage, Scene mainScene) {
        this.viewModel = viewModel;
        this.mainStage = mainStage;
        this.NextScene = nextScene;
    }

    public static void playMusic(int Order) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.pause();
            Sound = false;
        }
        String PathtoSound = "";
        if (Order == 1) {
            PathtoSound = "resources/Sounds/Winning.mp3";
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

public void pushedPlayGame(ActionEvent event) throws IOException
{
    if (mediaPlayer != null)
        this.playMusic(2);
    mainStage.setScene(NextScene);
//
}
}
