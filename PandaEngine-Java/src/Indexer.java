import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Indexer {

    private int SaveNum;
    private Map<String, ArrayList<Pair<String, Integer>>> TermsToSave;
    private String Currpath;
    private PrintWriter Writer;
    private boolean NewTerm;
    private boolean FirstTerm;
    private Merger merger;


    public Indexer(int NumofSave, Map<String, ArrayList<Pair<String, Integer>>> WordsInDoc, String currPath) throws ParseException, IOException, ClassNotFoundException {
        TermsToSave = WordsInDoc;
        SaveNum = NumofSave;
        Currpath = currPath;
    }


    public void savePostingFile(boolean Stemplz) throws IOException, ParseException, ClassNotFoundException {
        String Path = Currpath+ "/Postings";
        if (Stemplz)
            Path +="Stemplz";
        File theDir = new File(Path);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        theDir = new File(Path + "/" + SaveNum + ".txt");
        CreateWriter(theDir);
        FirstTerm = true;
        for (String key : TermsToSave.keySet()) {
            NewTerm = true;
            ArrayList<Pair<String, Integer>> DocsWhichUsed = TermsToSave.get(key);
            for (Pair<String, Integer> DocAndFreq : DocsWhichUsed) {
                if (NewTerm) {
                    NewTerm(key, DocAndFreq);
                    NewTerm = false;
                } else
                    AddDocToTerm(key, DocAndFreq);
            }
            NewTerm = true;
        }
        Writer.close();
        SavePostingFile(Stemplz);
    }

    private void SavePostingFile(boolean Stemplz) throws ParseException, IOException, ClassNotFoundException {
        Writer.flush();
        String Path = Currpath + "/Postings";
        if (Stemplz)
            Path += "Stemplz";
        if (SaveNum == 2) {
            merger = new Merger(Path+"/1.txt", Path+"/2.txt", Currpath, SaveNum);
            merger.MergeFiles(Stemplz);
        }
        if (SaveNum > 2) {
            merger = new Merger(Path+"/" + SaveNum + ".txt", Path+"/Merged" + (SaveNum) + ".txt", Currpath, SaveNum);
            merger.MergeFiles(Stemplz);
        }
        Writer.close();
    }

    private void CreateWriter(File theDir) {
        try {
            Writer = new PrintWriter(new FileWriter(theDir, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void savePostingFile() throws IOException {
//        File theDir = new File(Currpath + "/Postings");
//        if (!theDir.exists()) {
//            theDir.mkdir();
//        }
//        String chr = "";
//        for (String key : TermsToSave.keySet()) {
//            if(chr.equals("")) {
//                chr = key.substring(0, 1);
//                theDir = new File(Currpath + "/Postings/" + chr + ".txt");
//            }
//            if(chr.equals(key.substring(0,1))) {
//                saveTerm(theDir,key);
//            }
//            else {
//                chr = key.substring(0,1);
//                theDir = new File(Currpath + "/Postings/" + chr + ".txt");
//                saveTerm(theDir,key);
//            }
//        }
//    }

    private void AddDocToTerm(String term, Pair<String, Integer> DocNo) {
        Writer.append(" " + DocNo.getKey() + "->" + DocNo.getValue().toString());
    }


    private void NewTerm(String term, Pair<String, Integer> DocNo) {
        if (!FirstTerm)
            Writer.println();
        else
            FirstTerm = false;
        Writer.append(term + "#" + " " + DocNo.getKey() + "->" + DocNo.getValue().toString());
    }

    public void DeleteAll() {
        File PostingFolder = new File(Currpath + "/Postings");
        File[] files = PostingFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
            PostingFolder.delete();
        }
        PostingFolder = new File(Currpath + "/Dictionary");
        files = PostingFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
            PostingFolder.delete();
        }
        PostingFolder = new File(Currpath + "/DictionaryStemplz");
        files = PostingFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
            PostingFolder.delete();
        }
        PostingFolder = new File(Currpath + "/PostingsStemplz");
        files = PostingFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
            PostingFolder.delete();
        }
    }

    public void saveDic(boolean Stemplz, HashMap<String, TermInfo> dictionary) {
        String Path = Currpath+"/Dictionary";
        if (Stemplz)
            Path +="Stemplz";
        File theDir = new File(Path);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        theDir = new File(Path+"/Dictionary.txt");
        CreateWriter(theDir);
        for (String key : dictionary.keySet()) {
            Writer.append(dictionary.get(key).toString());
            Writer.println();
        }
        Writer.flush();
        Writer.close();
    }

    public HashMap<String, TermInfo> LoadDic(boolean Stemplz, String postingPath) {
        String Path = Currpath+"/Dictionary/Dictionary.txt";
        if (Stemplz)
            Path = Currpath+"/DictionaryStemplz/Dictionary.txt";
        File DictionaryFile = new File(Path);
        HashMap<String,TermInfo> LoadedDic = new HashMap<String,TermInfo>();
        try {
            BufferedReader Reader = new BufferedReader(new FileReader(DictionaryFile));
            String line;
            while ((line = Reader.readLine()) != null) {
                String[] TermLine = line.split("~");
                LoadedDic.put(TermLine[0],new TermInfo(TermLine[0],TermLine[1],TermLine[2], TermLine[3] ,TermLine[4]));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return LoadedDic;
    }
}

////
//
//    File[] files = folder.listFiles();
//    if(files!=null) { //some JVMs return null for empty dirs
//            for(File f: files) {
//            if(f.isDirectory()) {
//            deleteFolder(f);
//            } else {
//            f.delete();
//            }
//            }
//            }
//            folder.delete();
//  }
//
//          File theDir = new File(newPath);
//          if (!theDir.exists()) {
//          theDir.mkdir();
//          }
//          else{
//          theDir.delete();
//          theDir.mkdir();
//          }
//          File docsFile = new File(newPath + "/DocsDicFile.txt");
//          File termsFile = new File(newPath + "/TermsDicFile.txt");
//
//          try {
//
//          PrintWriter out = new PrintWriter(new FileWriter(docsFile, true));
//          PrintWriter out2 = new PrintWriter(new FileWriter(termsFile, true));
//
//          for (String DocNum : DocsData.keySet()) {
//          out.append(DocNum+DocsData.get(DocNum).toString());
//          out.println();
//          }
//          for(String term : TermsData.keySet()){
//          out2.append(TermsData.get(term).toString()+"\n");
//          out2.println();
//          }
//          }
//          catch (IOException e) {
//          e.printStackTrace();
//
//          }
//          }
