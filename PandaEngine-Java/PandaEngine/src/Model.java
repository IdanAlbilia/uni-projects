import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;

public class Model extends Observable {
    private ReadFile MyFileReader;

    public Model(){

    }

    public void SetFileReader(String PostingPath) throws ParseException, IOException, ClassNotFoundException {
        MyFileReader = new ReadFile(PostingPath);
    }

    public void SetFileReader(boolean Stemplz, String CorpusPath,String PostingPath) throws ParseException, IOException, ClassNotFoundException {
        MyFileReader = new ReadFile(CorpusPath, PostingPath, Stemplz);
    }

    public boolean LoadPostingFiles(boolean Stemplz, String PostingPath) throws ParseException, IOException, ClassNotFoundException {
        MyFileReader.LoadDic(Stemplz, PostingPath);
        return true;
    }

    public void DeleteDatabase(String PostingPath) throws ParseException, IOException, ClassNotFoundException {
        Indexer TheDeleter = new Indexer(0,null,PostingPath);
        TheDeleter.DeleteAll();
        TheDeleter = null;
    }

    public void GetTerms(){
    }

    public boolean Vacant(String Path) throws ParseException, IOException, ClassNotFoundException {
        return true;
    }

    public ObservableList<TermInfo> getTermslist(){
        ObservableList<TermInfo> TermList = FXCollections.observableArrayList();
        if(MyFileReader!=null){
            TermList.addAll(MyFileReader.getTermsList());
        }
        return TermList;
    }
}
