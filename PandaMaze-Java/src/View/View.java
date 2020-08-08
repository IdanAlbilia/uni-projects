package View;

import ViewModel.ViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.applet.Main;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Stack;

public class View<scroll> implements Observer, IView {
    //Controls
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.TextField txtfld_rowsNum;
    public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button btn_solveMaze;

    //Properties - For Binding
    public StringProperty characterPositionRow = new SimpleStringProperty("1");
    public StringProperty characterPositionColumn = new SimpleStringProperty("1");

    @FXML
    private ViewModel viewModel;
    private Scene mainScene;
    private Stage mainStage;
    private Scene WinScene;
    MainScreenController WinController;
    private Timeline TL = new Timeline(60);

    public void initialize(ViewModel viewModel, Stage mainStage, Scene mainScene, Scene winScene, MainScreenController WinController) {
        this.viewModel = viewModel;
        this.mainScene = mainScene;
        this.mainStage = mainStage;
        this.WinScene = winScene;
        this.WinController = WinController;
        bindProperties();
        setResizeEvent();
    }

    private void bindProperties() {
        lbl_rowsNum.textProperty().bind(this.characterPositionRow);
        lbl_columnsNum.textProperty().bind(this.characterPositionColumn);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            btn_generateMaze.setDisable(false);
        }
    }

    //<editor-fold desc="View Functionality">
    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
        btn_solveMaze.setDisable(false);
    }
    //</editor-fold>

    //<editor-fold desc="View -> ViewModel">
    public void generateMaze() {
        int heigth = Integer.valueOf(txtfld_rowsNum.getText());
        int width = Integer.valueOf(txtfld_columnsNum.getText());
        if (heigth <=2 || width <= 2)
        {
            String AlertMessage = "Maze Size has to be bigger or equal to 3 in Width and Height each!";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(AlertMessage);
            alert.show();
        }
        else{
            btn_generateMaze.setDisable(true);
            btn_solveMaze.setDisable(true);
            viewModel.generateMaze(width, heigth);
            MazeDisplayer.playMusic(1);
        }
    }

    public void SolveMaze(ActionEvent actionEvent) {
        Solution MazeSolution = viewModel.SolveMaze();
        mazeDisplayer.redraw();
    }


    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode(), mazeDisplayer);
        keyEvent.consume();
        LeavesFound();
    }

    public void LeavesFound(){
        if ( viewModel.LeavesFound()) {
            MazeDisplayer.playMusic(2);
            WinController.playMusic(1);
            mainStage.setScene(WinScene);
        }
    }

    public void setResizeEvent() {
        this.mainScene.widthProperty().addListener((observable, oldValue, newValue) -> {
        });

        this.mainScene.heightProperty().addListener((observable, oldValue, newValue) -> {
        });
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
        }
    }

    public void Help(ActionEvent actionEvent) {
        Pane pane = new Pane();
        Stage newStage = new Stage();
        String path = "resources/Images/Help Screen.jpg";
        Image help = new Image(Paths.get(path).toUri().toString());
        pane.getChildren().add(new ImageView(help));
        Scene scene = new Scene(pane);
        newStage.setScene(scene);
        newStage.show();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

    public void ZoomZoomZoom(ScrollEvent scroll) {
        double zoomScale;
        if (scroll.isControlDown()) {
            zoomScale = 1.5;
            double deltaY = scroll.getDeltaY();
            if(deltaY < 0){
                zoomScale = 1/ zoomScale;
            }
            zoom(mazeDisplayer, zoomScale, scroll.getSceneX(), scroll.getSceneY());
            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoomScale);
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoomScale);
            scroll.consume();
        }
    }

    void zoom(Node node, double factor, double x, double y) {
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;
        Bounds bounds = node.localToScene(node.getLayoutBounds(), true);
        double dx = (x-(bounds.getWidth() / 2+bounds.getMinX()));
        double dy = (y-(bounds.getHeight() / 2+bounds.getMinY()));
        TL.getKeyFrames().clear();
        TL.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), node.getTranslateX()-f * dx)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateYProperty(), node.getTranslateY()-f * dy)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.scaleYProperty(), scale))
        );
        TL.play();
    }

    public void showAbout() throws Exception {
        Pane tempPane = new Pane();
        Stage TempStage = new Stage();
        String PathToImage = "resources/Images/AboutScreen.png";
        Image help = new Image(Paths.get(PathToImage).toUri().toString());
        tempPane.getChildren().add(new ImageView(help));
        Scene scene = new Scene(tempPane);
        TempStage.setScene(scene);
        TempStage.show();
    }

    public void showProperties() throws Exception {
        Pane tempPane = new Pane();
        Stage TempStage = new Stage();
        String PathToImage = "resources/Images/Properties.jpg";
        Image help = new Image(Paths.get(PathToImage).toUri().toString());
        tempPane.getChildren().add(new ImageView(help));
        Scene scene = new Scene(tempPane);
        TempStage.setScene(scene);
        TempStage.show();
    }

    public void pushedPlayGame(ActionEvent event) throws IOException {
        Parent gamePlay = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene gameP = new Scene(gamePlay);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameP);
        window.show();
    }

    public void pushedSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Mazes", "*.Panda"));
        fileChooser.setInitialFileName("PandaMaze");
        File SaveMe = fileChooser.showSaveDialog(mainStage);
        if (SaveMe != null) {
            viewModel.pushedSave(SaveMe);
        }
    }

    public void pushedLoad()throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze", "*.Panda"));
        File LoadMe = fileChooser.showOpenDialog(mainStage);
        if (LoadMe != null) {
            viewModel.pushedLoad(LoadMe);
        } else {
            System.out.println("Error trying to load Panda file");
        }
        mazeDisplayer.setMaze(viewModel.getMaze());
        mazeDisplayer.setCharacterPosition(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
        displayMaze(viewModel.getMaze());
    }

}