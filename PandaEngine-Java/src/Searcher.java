import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Searcher {

    private String query;
    private boolean stemmer;
    private Parse parser;
    private CorpusDoc queryDoc;
    private HashMap<String, TermInfo> queryDic;
    private File postingFile;
    private HashMap<String, ArrayList<Pair<String,Integer>>> queryLog;
    private HashMap<String, TermInfo> currDic;


    public Searcher(String query, boolean stemmer,HashMap<String, TermInfo> CurrDic) throws ParseException, IOException, ClassNotFoundException {
        queryLog = new HashMap<String, ArrayList<Pair<String,Integer>>>();
        postingFile = new File("C:\\Uni\\Ichzur\\Posting\\Postings\\");
        File stopWords = new File("C:\\Uni\\Ichzur\\Resources\\stop_words.txt");
        parser = new Parse(stemmer, "");
        parser.LoadStopWords(stopWords);
        parser.ScanQuery(query, stemmer);
        queryDic = parser.getDictionary();
//        CaseWords(queryDic);
        this.query = query;
        currDic = CurrDic;
    }

    public void SetQuery(String Q) throws ParseException, IOException, ClassNotFoundException {
        query = Q;
        File stopWords = new File("C:\\Uni\\Ichzur\\Resources\\stop_words.txt");
        parser = new Parse(stemmer, "");
        parser.LoadStopWords(stopWords);
        parser.ScanQuery(query, stemmer);
        queryDic = parser.getDictionary();
        queryLog = new HashMap<String, ArrayList<Pair<String,Integer>>>();
    }

    public HashMap<String, ArrayList<Pair<String,Integer>>> lookForDocs() throws FileNotFoundException {
        int index = 0;
        for (String key : queryDic.keySet()) {
            System.out.println(key);
//            if (currDic.containsKey(key)){
            //&& currDic.get(key).getNumofDocs() < 5000
            if (currDic.containsKey(key)) {
                int Fileindex = currDic.get(key).getPostingIndex();
                int FileNum = Fileindex/100000;
                int FileSkip = Fileindex%100000;
                postingFile =  new File("C:\\Uni\\Ichzur\\Posting\\Postings\\"+(FileNum+1)+".txt");
                try (Stream<String> lines = Files.lines(Paths.get(postingFile.getPath()))) {
                    String TermLine = lines.skip(FileSkip).findFirst().get();
                    String[] SplitLine = TermLine.split("#");
                    if (SplitLine[0].equals(key)) {
                        String restOfLine = SplitLine[1];
                        ArrayList<Pair<String, Integer>> DocsUsing = splitToDocs(restOfLine);
                        if (DocsUsing.size() > 0)
                            queryLog.put(key, DocsUsing);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                System.out.println(key + " is too much for da panda :(");
        }
        return queryLog;
    }


    public ArrayList<Pair<String,Integer>> splitToDocs(String postings) {
        ArrayList<Pair<String, Integer>> postsForWord = new ArrayList<Pair<String, Integer>>();
        String[] posts;
        String doc;
        int timesInDoc;
        posts = postings.split(" ");
        for (String post : posts) {
            if (post.equals(""))
                continue;
            doc = post.split("->")[0];
            timesInDoc = Integer.parseInt(post.split("->")[1]);
            Pair<String, Integer> myPair = new Pair<String, Integer>(doc, timesInDoc);
            postsForWord.add(myPair);
        }
        return postsForWord;
    }

    public ArrayList<Query> lookForQuerysInFile(String Qpath) throws FileNotFoundException {
        String queryNumber = "";
        String queryPhrase = "";
        ArrayList<Query> queries = new ArrayList<Query>();
        File queryFile = new File(Qpath+"\\queries.txt");
        Scanner sc = new Scanner(queryFile);
        int index = 0;
        while (sc.hasNextLine()) {
            String next = sc.nextLine();
            if (next.contains("<num>")) {
                String[] IDLine = next.split(" ");
                queryNumber = IDLine[2];
                next = sc.nextLine();
                String TitleLine = next.substring(8);
                next = sc.nextLine();
                next = sc.nextLine();
                next = sc.nextLine();
                while(!next.equals("") && sc.hasNextLine() && !next.contains("<narr>")) {
                    TitleLine += " "+next;
                    next = sc.nextLine();
                }
                queries.add(new Query(TitleLine, queryNumber));
            }
        }
        return queries;
    }

}
