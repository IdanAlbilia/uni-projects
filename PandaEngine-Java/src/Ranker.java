import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Ranker {
    HashMap<String,CorpusDoc> AllDocs;
    boolean Semantics;
    private double k;
    private double AvgDocL;
    String currQID;

    public Ranker( HashMap<String,CorpusDoc> allDocs, boolean semantics)
    {
        AllDocs = allDocs;
        Semantics = semantics;
        AvgDocL = ReadFile.AvgDocL;
        k = 1.2;
    }

    private double CalcScore(int termFreq, int docLen, int titleTermFreq, int titleLen, int anchorTermFreq, int anchorLen, double avgAnchorLen, long NumofDocs, long numofDocsContainingTerm) {
        if (termFreq <= 0) return 0.0;

        double Mone = termFreq * (k+1);

        double Mahane = termFreq+k*(1-0.4+0.4*(docLen/AvgDocL));

        double IDF = Math.log((NumofDocs-numofDocsContainingTerm+0.5)/(numofDocsContainingTerm+0.5));

        return IDF*((Mone / Mahane));
    }

    public double getScore(String term, int termfreq, String docNo, int DocsContaining)
    {
        CorpusDoc CurrDoc = AllDocs.get(docNo);
        int DocLen = CurrDoc.getTextLength();
        String title = CurrDoc.getTitle();
        int titlefreq = CheckTermInTitle(term,title);
        int titleLen = title.length();
        long numOfDocs = AllDocs.size();
        long docsContaining = DocsContaining;
        return CalcScore(termfreq,DocLen,titlefreq,title.length(),0,0,0,numOfDocs,docsContaining);
    }

    public ArrayList<Pair<String,Double>> RankDocs(HashMap<String, ArrayList<Pair<String,Integer>>> WordsAndDocs, String Qid)
    {
        currQID = Qid;
        ArrayList<Pair<String,Double>> FinalList = new  ArrayList<Pair<String,Double>>();
        HashMap<String,Pair<String,Double>> TempMap = new   HashMap<String,Pair<String,Double>>();
        for (String key : WordsAndDocs.keySet()) {
            String currdoc = "";
            ArrayList<Pair<String, Integer>> ListForWord = WordsAndDocs.get(key);
            for (Pair<String, Integer> DocAndFreq : ListForWord) {
                currdoc = DocAndFreq.getKey();
                double Rank = getScore(key, DocAndFreq.getValue(),currdoc , ListForWord.size());
                AddToList(TempMap, new Pair<String,Double>(currdoc,Rank));
            }
        }
        System.out.println("Done ranking");
        MoveMaptoList(TempMap,FinalList);
        Collections.sort(FinalList, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(final Pair<String, Double> o1, final Pair<String, Double> o2) {
                if(Double.compare(o2.getValue(),o1.getValue())>0)
                    return 1;
                else if(Double.compare(o2.getValue(),o1.getValue())==0)
                    return 0;
                else
                    return -1;
            }
        });
        System.out.println("Done sorting");
        SaveRanks(FinalList);
        return FinalList;
    }

    private void MoveMaptoList(HashMap<String,Pair<String,Double>> TempMap, ArrayList<Pair<String,Double>> FinalList) {
        for(String Doc : TempMap.keySet())
        {
            FinalList.add(TempMap.get(Doc));
        }
    }

    private void SaveRanks(ArrayList<Pair<String, Double>> finalList) {
        try {
            PrintWriter Writer = new PrintWriter(new FileWriter("C:/Uni/Ichzur/Trec Eval/results.txt", true));
            for (int i = 0; i < 50 && i < finalList.size(); i++) {
                Writer.append(currQID+" 0 "+finalList.get(i).getKey()+" "+i+1+" 10 gaia\n");
            }
            Writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AddToList(HashMap<String,Pair<String, Double>> tempMap, Pair<String, Double> DocRankPair) {
        int index = 0;
        Pair<String, Double> newP;
        if(tempMap.containsKey(DocRankPair.getKey()))
        {
            Pair<String, Double> oldPair = tempMap.remove(DocRankPair.getKey());
            newP = new Pair<String, Double>(DocRankPair.getKey(),DocRankPair.getValue()+oldPair.getValue());
            tempMap.put(DocRankPair.getKey(), newP);
            return;
        }
        tempMap.put(DocRankPair.getKey(), DocRankPair);
    }


    private int CheckTermInTitle(String term, String title) {
        int counter = 0;
        if (title.contains(term))
        {
            counter = countTerm(title,term);
        }
        return counter;
    }

    private int countTerm(String title, String term) {
        String[] WordsInTitle = title.split(" ");
        int counter = 0;
        for (String Word : WordsInTitle)
            if (Word.equals(term))
                counter++;
        return counter;
    }


}
