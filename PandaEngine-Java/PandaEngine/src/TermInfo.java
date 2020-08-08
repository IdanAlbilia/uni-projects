import javafx.util.Pair;

import java.io.Serializable;

public class TermInfo implements Serializable {
    private String Term;
    int NumofDocs;
    private int Appearances;
    int MaxAppearanceInDoc;
    String MaxAppDoc;

    public TermInfo(String NewTerm)
    {
        Term = NewTerm;
        NumofDocs = 0;
        Appearances = 1;
    }

// ZHU YUCHENG~2~1~1~FBIS3-1913
    public TermInfo(String NewTerm, Pair<String, Integer> InfoPair)
    {
        Term = NewTerm;
        NumofDocs = 0;
        Appearances = InfoPair.getValue();
        MaxAppearanceInDoc = Appearances;
        MaxAppDoc = InfoPair.getKey();
    }

    public TermInfo(String term,String NofDocs,String appearances, String MaxApp, String maxAppDoc)
    {
        Term = term;
        NumofDocs = Integer.parseInt(NofDocs);
        Appearances = Integer.parseInt(appearances);
        MaxAppearanceInDoc = Integer.parseInt(MaxApp);
        MaxAppDoc = maxAppDoc;
    }

    public void AddtoNumofDocs(int numofDocs)
    {
        NumofDocs += numofDocs;
    }

    public void AddAppearances()
    {
        Appearances++;
    }

    public void SetMaxAppearanceInDoc(int TotalTimesInDoc,String Docno) {
        if (TotalTimesInDoc > MaxAppearanceInDoc)
        {
            MaxAppearanceInDoc = TotalTimesInDoc;
            MaxAppDoc =Docno;
        }
    }

    public String getTerm()
    {
        return Term;
    }

    public int getNumofDocs()
    {
        return NumofDocs;
    }
    public int getAppearances()
    {
        return Appearances;
    }
    public String toString(){
        return Term+"~"+ NumofDocs+"~"+ Appearances + "~" + MaxAppearanceInDoc+ "~" +MaxAppDoc;
    }
}
