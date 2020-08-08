import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Main extends Application {
    private Stage window;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Model model = new Model();
        primaryStage.setTitle("SearchEngine");
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = fxmlLoader.load(getClass().getResource("./View.fxml").openStream());



        Scene scene = new Scene(root, 450, 320);

        primaryStage.setX(400);
        primaryStage.setY(400);
        primaryStage.setWidth(scene.getWidth());
        primaryStage.setHeight(scene.getHeight());
        primaryStage.setScene(scene);
        ViewController view = fxmlLoader.getController();
        view.setStage(primaryStage);
        view.setModel(model);
        model.addObserver(view);
        primaryStage.show();
    }
}