import javafx.util.Pair;
import org.jsoup.Jsoup;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.*;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import javax.xml.soap.Text;

public class ReadFile {

    private Parse WordCollection;
    private HashMap<String,CorpusDoc> AllDocs;
    private int Files = 0;
    public static long startTime = System.nanoTime();
    private int hara = 0;
    //private HashMap<String, String> Texts;

    public ReadFile(String PostingPath) throws IOException, ClassNotFoundException, ParseException {
        WordCollection = new Parse(true,PostingPath);
    }


    public ReadFile(String CorpusPath,String PostingPath, boolean Stemplz) throws IOException, ClassNotFoundException, ParseException {
        WordCollection = new Parse(Stemplz,PostingPath);
        AllDocs = new HashMap<String,CorpusDoc>();
        File Resources = new File(CorpusPath);
        File[] CorpusStopW = Resources.listFiles();
        File[] AllFolders = CorpusStopW[0].listFiles();
//        int KamaLarutz =  AllFolders.length;
        int KamaLarutz = AllFolders.length;
        WordCollection.LoadStopWords(CorpusStopW[1]);
        for (int i = 0; i < KamaLarutz; i++) {
            if (i == 8) {
                System.out.println("Woooahhhh settle down boy");
             //   WordCollection.saveDic();
            //    WordCollection.loadDic();
            }
            System.out.println(i);
            File CurrFolder = new File(AllFolders[i].getPath());
            File[] CurrFile = CurrFolder.listFiles();
            String[] Path = CurrFile[0].getPath().split(" ");
            GetDocs(CurrFile[0]);
        }
        System.out.println("Files: "+hara+" Start " +(System.nanoTime() - startTime) /1000000000);
        WordCollection.Save();
        System.out.println("Files "+hara+" End " + (System.nanoTime() - startTime) /1000000000 + ", Database Stats-> Total Docs Scanned:" +WordCollection.getDocCounter()+", Total Terms in Dictionary:"+WordCollection.getDictionarySize());
        WordCollection.SaveDic();
        System.out.println("Satla");
    }

    public Collection<TermInfo> getTermsList() {
        return WordCollection.GetTerms();
    }



    private void GetDocs(File file) throws ParseException, IOException, ClassNotFoundException {
        Document JFile;
        try {
            JFile = Jsoup.parse(file, "UTF-8");
        } catch (IOException IOE) {
            return;

        }
        Elements Docs = JFile.getElementsByTag("DOC");
        for (Element element : Docs) {
            String DocNo = GetDocNo(element,"DOCNO");
            String title = GetDocNo(element,"TI");
            String date = GetDocNo(element,"DATE1");
            String text = GetDocNo(element,"TEXT");
            CorpusDoc CurrDoc = new CorpusDoc(DocNo,date,title);
            AllDocs.put(DocNo,CurrDoc);
            WordCollection.ScanText(text,CurrDoc);
        }
        if (Files == 6){
            System.out.println("Files: "+hara+" Start " +(System.nanoTime() - startTime) /1000000000);
            WordCollection.Save();
            System.out.println("Files "+hara+" End " + (System.nanoTime() - startTime) /1000000000 + ", Database Stats-> Total Docs Scanned:" +WordCollection.getDocCounter()+", Total Terms in Dictionary:"+WordCollection.getDictionarySize());
            Files = 0;
            hara++;
        }
        else
        {
            Files++;
            hara++;
        }

    }

    private void AddtoHashMap(String Key, String DocNo,HashMap<String, HashMap<String, String>> HashMap)
    {
        if (HashMap.containsKey(Key))
        {
            HashMap<String,String> FoundHashMap = HashMap.get(Key);
            FoundHashMap.put(DocNo,DocNo);
        }
        else
        {
            HashMap<String,String> NewHashMap = new HashMap<String,String>();
            NewHashMap.put(DocNo,DocNo);
            HashMap.put(Key,NewHashMap);
        }
    }

    private String GetDocNo(Element Doc,String tag) {
        return Doc.getElementsByTag(tag).text();
    }

    public void LoadDic(boolean Stemplz, String postingPath) throws ParseException, IOException, ClassNotFoundException {
        WordCollection.LoadDic(Stemplz, postingPath);
    }
}







/*
 * 
 * 
 * 
 * 
 *  public void extractDocs(){
        loadPaths(corpus);
        for(String path : FilePaths){
            File toExtract = new File(path);
            extractByTag(toExtract,"TEXT", Texts);
            extractByTag(toExtract,"TI", Titles);
            extractByTag(toExtract, "DATE1", Dates);
            extractByTag(toExtract, "DOCNO", DocNumbers);
        }
    }

     *
     * @param toExtract
         private void extractByTag(File toExtract, String tag, HashMap hashMap)
         {
             Document document;
             try{
                      document = Jsoup.parse(toExtract,"UTF-8");
            }
            * catch (IOException IOE){
            return;
             }
         Elements elements = document.getElementsByTag("DOC");
         for(Element element : elements){
                hashMap.put(extractDocKey(element),element.getElementsByTag(tag).text());
        }
}

                */
