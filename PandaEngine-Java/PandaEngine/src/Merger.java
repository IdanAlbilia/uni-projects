import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Merger {

    private int SaveNum;
    private String Path1;
    private String Path2;
    private String Currpath;
    private ArrayList<String> R1Terms;
    private ArrayList<String> R2Terms;
    private PrintWriter Writer;
    private boolean LastTerm1;
    private boolean LastTerm2;


    public Merger(String path1, String path2, String currpath,int saveNum) throws ParseException, IOException, ClassNotFoundException {
        Path1 = path1;
        Path2 = path2;
        Currpath = currpath;
        SaveNum = 0;
        LastTerm1 = true;
        LastTerm2 = true;
        SaveNum = saveNum+1;
    }


    public void MergeFiles(boolean Stemplz) throws IOException {
        File File1 = new File(Path1);
        BufferedReader Reader1 = new BufferedReader(new FileReader(File1));
        File File2 = new File(Path2);
        BufferedReader Reader2 = new BufferedReader(new FileReader(File2));
        String Path = Currpath + "/Postings";
        if (Stemplz)
            Path += "Stemplz";
        File NewMergedFile = new File(Path+"/Merged"+SaveNum+".txt");
        CreateWriter(NewMergedFile);
        StartMerge(Reader1,Reader2);
        Reader1.close();
        Reader2.close();
        try
        {
            Files.deleteIfExists(Paths.get(Path1));
            Files.deleteIfExists(Paths.get(Path2));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
    }

    private void StartMerge(BufferedReader R1, BufferedReader R2) throws IOException {
//        String Text = GetTerms(R1);
          R1Terms = new ArrayList(Arrays.asList(R1.readLine().split("#")));
//        Text = GetTerms(R2);
          R2Terms = new ArrayList(Arrays.asList(R2.readLine().split("#")));
        int R1index = 0;
//        int R2index = 0;
        int Judge = 0;
        Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
        while(R1.ready()||R2.ready())
        {
            if (R1Terms.get(0).equals("zoo"))
                Judge = Judge;
            switch (Judge)
            {
                case 1:
                    Writer.append(R1Terms.get(0)+"#");
                    Writer.append(R1Terms.get(1));
                    Writer.println();
                    if(!R2.ready()&&R1.ready()) {
                        R1Terms = new ArrayList(Arrays.asList(R1.readLine().split("#")));
                        if (LastTerm2)
                        {
                            Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                            if (Judge == 2  || Judge == 3)
                                LastTerm2 = false;
                            break;
                        }
                        else
                            Judge = 1;

                        break;
                    }
                    else if(R1.ready())
                    {
                        R1Terms = new ArrayList(Arrays.asList(R1.readLine().split("#")));
                        Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                        break;
                    }
                    else
                    {
                        LastTerm1 = false;
                        Judge = 2;
                    }
                    break;
                case 2:
                    Writer.append(R2Terms.get(0)+"#");
                    Writer.append(R2Terms.get(1));
                    Writer.println();
                    if(!R1.ready()&&R2.ready()) {
                        R2Terms = new ArrayList(Arrays.asList(R2.readLine().split("#")));
                        if (LastTerm1)
                        {
                            Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                            if (Judge == 1 || Judge == 3)
                                LastTerm1 = false;
                            break;
                        }
                        else
                            Judge = 2;

                        break;
                    }
                    else if(R2.ready()) {
                        R2Terms = new ArrayList(Arrays.asList(R2.readLine().split("#")));
                        Judge = WhichFirst(R1Terms.get(0), R2Terms.get(0));
                        break;
                    }
                    else
                    {
                        LastTerm2 = false;
                        Judge = 1;
                    }
                    break;
                case 3:
                    Writer.append(R1Terms.get(0)+"#");
                    Writer.append(R1Terms.get(1));
                    Writer.append(R2Terms.get(1));
                    Writer.println();
                    if(R1.ready()) {
                        R1Terms = new ArrayList(Arrays.asList(R1.readLine().split("#")));
                    }
                    else
                    {
                        if(R2.ready())
                            R2Terms = new ArrayList(Arrays.asList(R2.readLine().split("#")));
                        if (LastTerm1)
                        {
                            Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                            if (Judge == 1 || Judge == 3)
                                LastTerm1 = false;
                            break;
                        }
                        else
                            Judge = 2;

                        break;
                    }
                    if(R2.ready()) {
                        R2Terms = new ArrayList(Arrays.asList(R2.readLine().split("#")));
                    }
                    else
                    {
                        if(R1.ready())
                            R1Terms = new ArrayList(Arrays.asList(R1.readLine().split("#")));
                        if (LastTerm2)
                        {
                            Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                            if (Judge == 2 || Judge == 3)
                                LastTerm2 = false;
                            break;
                        }
                        else
                            Judge = 1;

                        break;
                    }
                    Judge = WhichFirst(R1Terms.get(0),R2Terms.get(0));
                    break;
            }
        }
        if (Judge == 1)
        {
            Writer.append(R1Terms.get(0)+"#");
            Writer.append(R1Terms.get(1));
        }
        else
        {
            Writer.append(R2Terms.get(0)+"#");
            Writer.append(R2Terms.get(1));
        }
        Writer.flush();
        Writer.close();
    }

    private String GetTerms(BufferedReader Reader){
        String Terms = "";
        try
        {

            String sCurrentLine;
            while ((sCurrentLine = Reader.readLine()) != null)
            {
                Terms += sCurrentLine ;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Terms;
    }

    private int WhichFirst(String r1Line, String r2Line) {

        int compare = r1Line.toLowerCase().compareTo(r2Line.toLowerCase());
        if (compare < 0)
            return 1;
        else if (compare > 0)
            return 2;
        else
            return 3;
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


}

//
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
