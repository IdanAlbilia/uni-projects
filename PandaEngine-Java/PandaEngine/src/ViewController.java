import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

public class ViewController implements Observer {

    private Model model;
    public String corpusPath ="empty";
    public String postingFilePath= "empty";
    private Stage stage;
    public CheckBox useStemmer;
    public Label CorpusLabel;
    public Label PostingLabel;
    public TableView<TermInfo> dictionary;
    boolean Stemplz;


    public void setModel(Model m){
        this.model=m;
    }

    public void setStage(Stage s){
        this.stage = s;
    }

    @Override
    public void update(Observable observable, Object o) {
    }

    public void setCorpusPath() {
        DirectoryChooser ChoosePath = new DirectoryChooser();
        File FileChosen = ChoosePath.showDialog(stage);
        if (FileChosen!=null)
        {
            corpusPath = FileChosen.getPath();
            CorpusLabel.setText(corpusPath);
        }
    }

    public void setPostingFilePath() {
        DirectoryChooser ChoosePath = new DirectoryChooser();
        File FileChosen = ChoosePath.showDialog(stage);
        if (FileChosen!=null) {
            postingFilePath = FileChosen.getPath();
            PostingLabel.setText(postingFilePath);
        }
    }

    public void deletePosting() throws ParseException, IOException, ClassNotFoundException {
        if(postingFilePath.equals("empty")) {
            Alert("Posting Path is empty, try adding a path");
        }
        else{
            model.DeleteDatabase(postingFilePath);
        }
    }

    public void loadPosting() throws ParseException, IOException, ClassNotFoundException {
        if (postingFilePath.equals("empty")) {
            Alert("Posting path is empty");
        } else {
            model.SetFileReader(postingFilePath);
            boolean isLoaded = model.LoadPostingFiles(Stemplz,postingFilePath);
            if(!isLoaded){
                Alert("Folder is empty");
            }
        }
    }

//    public void dictionaryDisplay(){
//        Stage dictionaryStage = new Stage();
//        dictionaryStage.setTitle("Dictionary");
//        TableColumn<TermInfo,String> terms = new TableColumn<TermInfo,String>("term");
//        terms.setCellValueFactory(new PropertyValueFactory<TermInfo, String>("Term"));
//        TableColumn<TermInfo, String> appearances = new TableColumn<TermInfo, String>("appearances");
//        appearances.setCellValueFactory(new PropertyValueFactory<TermInfo, String>("Appearances"));
//        TableView<TermInfo> dictionary= new TableView<>();
//        dictionary.setItems(model.getTermslist());
//        dictionary.getColumns().addAll(terms, appearances);
//        dictionary.getSortOrder().add(terms);
//        Scene scene = new Scene(dictionary);
//        dictionaryStage.setScene(scene);
//        dictionaryStage.show();
//    }

    public void dictionaryDisplay() {

        Stage stage = new Stage();
        stage.setTitle("DisplayDic");

        TableColumn<TermInfo,String> termsNames = new TableColumn<>("Term");
        termsNames.setCellValueFactory(new PropertyValueFactory<TermInfo,String>("Term"));
        Comparator<String> c  = new Comparator<String>() {
            @Override
            public int compare(String term1, String term2) {
                for(int i=0 ; i<term1.length() && i<term2.length() ; i++){
                    if(term1.charAt(i)>term2.charAt(i)){
                        return 1;
                    }
                    if(term1.charAt(i)<term2.charAt(i)){
                        return -1;
                    }
                }
                return 0;
            }
        };
        termsNames.setComparator(c);
        TableColumn<TermInfo,String> termsCounter = new TableColumn<>("Total Appearance");
        termsCounter.setCellValueFactory(new PropertyValueFactory<>("Appearances"));
        TableView<TermInfo> dictionary = new TableView<>();
        ObservableList<TermInfo> terms = model.getTermslist();
        SortedList<TermInfo> sortedData = new SortedList<>(terms);
        sortedData.comparatorProperty().bind(dictionary.comparatorProperty());
        dictionary.setItems(sortedData);
        dictionary.getColumns().addAll(termsNames,termsCounter);
        dictionary.getSortOrder().add(termsNames);
        Scene scene = new Scene(dictionary);
        stage.setScene(scene);
        stage.show();
    }

    private void Alert(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMsg);
        alert.show();
    }

    public void selectStart() throws ParseException, IOException, ClassNotFoundException {
        if(postingFilePath.equals("empty") || corpusPath.equals("empty")){
            Alert("You have not initialized the corpus path or posting file path");
        }
        else{
            if(!model.Vacant(postingFilePath)){
                Alert("A parsing file exists already, delete it first in order to start");
            }else {
                model.SetFileReader(Stemplz, corpusPath, postingFilePath);
            }
        }
    }

    public void ToggleStemming() {
        if (Stemplz)
            Stemplz = false;
        else
            Stemplz = true;
    }
}