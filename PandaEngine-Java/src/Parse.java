import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.Current;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.println;

public class Parse {
    private Map<String, ArrayList<Pair<String, Integer>>> wordByDoc;
    private static HashMap<String, Pair<String, Integer>> WordsInDoc;
    private static HashMap<String, Pair<String, Integer>> PossibleEntities;
    private static HashMap<String, TermInfo> Dictionary;
    private static HashMap<String, String> StopWords;
    private String Currpath;
    private int SkipWords;
    private int Filenum;
    private int DocCounter;
    private static HashMap<String, String> DatestoNums;
    private static HashMap<String, Integer>  NumTerms;
    private ArrayList<String> Words;
    private CorpusDoc CurrDoc;
    private boolean StemPlz;
    private int TermCounterMax;
    private Indexer Index;

    public Parse(Boolean StemWords, String PostingPath) {
        Currpath = PostingPath;
        Filenum = 0;
        DocCounter = 0;
        if (StemWords)
            StemPlz = true;
        if (WordsInDoc == null)
            WordsInDoc = new HashMap<String,Pair<String, Integer>>();
        if (Dictionary == null)
            Dictionary = new HashMap<String,TermInfo>();
        PossibleEntities = new HashMap<String,Pair<String, Integer>>();
       // ConfirmedEntities = new HashMap<String, TermInfo>();
        InitializeDates();
        InitializeNumTerms();
    }

    private void InitializeDates() {
        DatestoNums = new HashMap<>();
        DatestoNums.put("JANUARY","01");
        DatestoNums.put("January","01");
        DatestoNums.put("JAN","01");
        DatestoNums.put("Jan","01");
        DatestoNums.put("FEBRUARY","02");
        DatestoNums.put("February","02");
        DatestoNums.put("FEB","02");
        DatestoNums.put("Feb","02");
        DatestoNums.put("MARCH","03");
        DatestoNums.put("March","03");
        DatestoNums.put("MAR","03");
        DatestoNums.put("Mar","03");
        DatestoNums.put("APRIL","04");
        DatestoNums.put("April","04");
        DatestoNums.put("APR","04");
        DatestoNums.put("Apr","04");
        DatestoNums.put("MAY","05");
        DatestoNums.put("May","05");
        DatestoNums.put("JUN","06");
        DatestoNums.put("Jun","06");
        DatestoNums.put("JUNE","06");
        DatestoNums.put("June","06");
        DatestoNums.put("JULY","07");
        DatestoNums.put("July","07");
        DatestoNums.put("JUL","07");
        DatestoNums.put("Jul","07");
        DatestoNums.put("AUGUST","08");
        DatestoNums.put("August","08");
        DatestoNums.put("AUG","08");
        DatestoNums.put("Aug","08");
        DatestoNums.put("SEPTEMBER","09");
        DatestoNums.put("September","09");
        DatestoNums.put("SEP","09");
        DatestoNums.put("Sep","09");
        DatestoNums.put("OCTOBER","10");
        DatestoNums.put("October","10");
        DatestoNums.put("OCT","10");
        DatestoNums.put("Oct","10");
        DatestoNums.put("NOV","11");
        DatestoNums.put("Nov","11");
        DatestoNums.put("NOVEMBER","11");
        DatestoNums.put("November","11");
        DatestoNums.put("DECEMBER","12");
        DatestoNums.put("December","12");
        DatestoNums.put("DEC","12");
        DatestoNums.put("Dec","12");
    }

    private void InitializeNumTerms() {
        NumTerms = new HashMap<>();
        NumTerms.put("Percent",1);
        NumTerms.put("percent",1);
        NumTerms.put("Percentage",1);
        NumTerms.put("percentage",1);
        NumTerms.put("Dollars",2);
        NumTerms.put("dollars",2);
        NumTerms.put("U.S.",2);
        NumTerms.put("US",2);
        NumTerms.put("U.S",2);
        NumTerms.put("million",3);
        NumTerms.put("Million",3);
        NumTerms.put("Millions",3);
        NumTerms.put("millions",3);
        NumTerms.put("M",3);
        NumTerms.put("m",3);
        NumTerms.put("bn",3);
        NumTerms.put("BN",3);
        NumTerms.put("Billion",3);
        NumTerms.put("Billions",3);
        NumTerms.put("billions",3);
        NumTerms.put("billion",3);
        NumTerms.put("Trillion",3);
        NumTerms.put("trillion",3);
        NumTerms.put("Thousand",3);
        NumTerms.put("thousand",3);
        NumTerms.put("thousands",3);
        NumTerms.put("Thousands",3);
    }

    public void ScanText(String Text, CorpusDoc doc) throws ParseException, IOException, ClassNotFoundException {
        if (WordsInDoc == null)
            WordsInDoc = new HashMap<String,Pair<String,Integer>>();
        DocCounter++;
        TermCounterMax = 1;
        CurrDoc = doc;
        Text = Text.replaceAll(",|\\(|\\)|'|\"|`|\\{|}|\\[|]|\\\\|#|--|\\+|---|&|\\.\\.\\.|\\.\\.|\\||=|>|<|//|\\\\|\\|//|\\/|~", "");
        Words = new ArrayList(Arrays.asList(Text.split("\\n|\\s+|\\t|;|\\?|!|:|@|\\[|]|\\(|\\)|\\{|}|_|\\*")));
        SkipWords = 0;
        for (int i = 0; i < Words.size(); i++) {
            String CurrWord = Words.get(i);
            if (CurrWord.length() == 0)
                continue;
            if ((!CurrWord.equals("")&&((!checkIfStopWord(CheckForDots(CurrWord)))||checkIfStopWord(CheckForDots(CurrWord)) && Character.isUpperCase(CurrWord.charAt(0))) && !CurrWord.equals("-"))) {
                if (i < Words.size() - 2 && Words.get(i + 1).equals("-")) {
                    if (i < Words.size() - 4 && Words.get(i + 3).equals("-"))
                        CurrWord = ConvertPhrase(CurrWord, Words.get(i + 2), Words.get(i + 4));
                    else
                        CurrWord = ConvertPhrase(CurrWord, Words.get(i + 2));
                    AddWord(CheckForDots(CurrWord));
                    continue;
                }
                if (CurrWord.length()>0 && (isNumeric(CurrWord) || (CurrWord.charAt(0) == '$' && isNumeric(CurrWord.substring(1))) || (CurrWord.charAt(CurrWord.length() - 1) == '%' && isNumeric(CurrWord.substring(0, CurrWord.length() - 1))))) {
                    AddNumericWord(CurrWord, i);
                    continue;
                }
                if (DatestoNums.containsKey(CurrWord) && (i < Words.size() - 1)) {
                    AddWord(ConvertDate(CurrWord, Words.get(i + 1)));
                    continue;
                }
                CurrWord = CheckForDots(CurrWord);
                if (i < Words.size() - 4 && CurrWord.toLowerCase().equals("between") && isNumeric(Words.get(i + 1)) && isNumeric(Words.get(i + 3)) && (Words.get(i + 2).toLowerCase().equals("and"))) {
                    AddWord(CurrWord + " " + Words.get(i + 1) + " " + Words.get(i + 2) + " " + Words.get(i + 3));
                    SkipWords = 3;
                }
                if (CurrWord.length() >= 1)
                    CurrWord = checkAscii(CurrWord);
                if (CurrWord.length() >= 1) {
                    if (Character.isUpperCase(CurrWord.charAt(0)))
                        CheckForEntity(CurrWord, i);
                    else
                        AddWord(CurrWord);
                }
            }
            i += SkipWords;
            SkipWords = 0;
        }

        AddToTree();
        CurrDoc.setUniqueWords(WordsInDoc.size());
        CurrDoc.setTotalWords(Words.size());
        CurrDoc.setMax_tf(TermCounterMax);
        WordsInDoc = null;
    }

    public int getDictionarySize(){
        return Dictionary.size();
    }

    public int getDocCounter(){
        return DocCounter;
    }

    private String checkAscii(String word){
        String fixedWord = "";
        char[] wordByChars= word.toCharArray();
        for(char c:wordByChars){
            if(!( (int)c > 300) )
                fixedWord = fixedWord + c;
        }
        return fixedWord;
    }

    private void CheckForEntity(String currWord, int i) {
        boolean StillUpper = true;
        int index = i+1;
        String PossibleEntity = currWord;
        while(StillUpper && Words.size()>index)
        {
            String nextWord = Words.get(index);
            if (nextWord.length()>0 && Character.isUpperCase(nextWord.charAt(0)))
            {
                PossibleEntity += " " + nextWord;
                index++;
                SkipWords++;
            }
            else
                StillUpper = false;
        }
        if (SkipWords >= 1)
        {
            PossibleEntity = PossibleEntity.toUpperCase();
            if (wordByDoc == null) {
                wordByDoc = new TreeMap<>(
                        new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {

                                return o1.toUpperCase().compareTo(o2.toUpperCase());
                            }
                        }
                );
            }
            if (wordByDoc.containsKey(PossibleEntity)||Dictionary.containsKey(PossibleEntity))
            {
                AddWord(PossibleEntity);
            }
            else if(PossibleEntities.containsKey(PossibleEntity))
            {
                String SpottedIn = PossibleEntities.get(PossibleEntity).getKey();
                if (!SpottedIn.equals(CurrDoc.getDocNo()))
                {
                    Pair<String,Integer> EntityPair = PossibleEntities.remove(PossibleEntity);
                    ArrayList<Pair<String,Integer>> NewTerminfoList = new ArrayList<Pair<String,Integer>>();
                    NewTerminfoList.add(EntityPair);

                    wordByDoc.put(PossibleEntity, NewTerminfoList);
                    Dictionary.put(PossibleEntity,new TermInfo(PossibleEntity,EntityPair));
                    AddWord(PossibleEntity);
//                    AddToConfirmedEntities(PossibleEntity,EntityPair);

                }
                else
                {
                    Pair<String,Integer> OldPair= PossibleEntities.get(PossibleEntity);
                    Pair<String,Integer> NewPair = new Pair<String,Integer>(CurrDoc.getDocNo(),OldPair.getValue()+1);
                    PossibleEntities.replace(PossibleEntity,NewPair);
                }
            }
            else
            {
                Pair<String,Integer> NewPair = new Pair<String,Integer>(CurrDoc.getDocNo(),1);
                PossibleEntities.put(PossibleEntity,NewPair);
            }
        }
    }

    private void AddNumericWord(String currNum, int i) throws ParseException {
        boolean IsDollar = false;
        boolean IsPercentage = false;
        String[] newWord;
        if (currNum.charAt(0) == '$')
        {
            currNum = currNum.substring(1);
            IsDollar = true;
        }
        else if (currNum.charAt(currNum.length()-1) == '%')
        {
            currNum = currNum.substring(0, currNum.length()-1);
            IsPercentage = true;
        }
        String ToAdd = "";
        Integer Type = 0;
        if (i < Words.size() - 1) {
            String NextWord = Words.get(i + 1);
            if (NumTerms.containsKey(CheckForDots(NextWord))){
                Type = NumTerms.get(CheckForDots(NextWord));
                if (IsDollar) {
                    Type = 2;
                    IsDollar = false;
                }
                if (IsPercentage){
                    Type = 1;
                    IsPercentage = false;
                }
                switch (Type) {
                    case 1: {
                        AddWord(ConvertPercentage(currNum));
                        break;
                    }
                    case 2: {
                        if (i < Words.size() - 1)
                            if (NumTerms.containsKey(NextWord))
                                AddWord(ConvertDollar(currNum));
                        break;
                    }
                    case 3: {
                        if (i < Words.size() - 3 && NumTerms.containsKey(Words.get(i+2)) && NumTerms.get(Words.get(i+2)).equals(2))
                            AddWord(ConvertDollar(currNum,NextWord));
                        else
                            AddWord(ConvertRegularNum(currNum,NextWord));
                        break;
                    }
                }
            }
            if (IsDollar) {
                IsDollar = false;
                if (currNum.contains("-")) {
                    newWord = currNum.split("-");
                    if (NumTerms.containsKey(newWord[1]))
                        AddWord(ConvertDollar(newWord[0], newWord[1]));
                }
            }
            else if (DatestoNums.containsKey(CheckForDots(NextWord)))
                AddWord(ConvertDate(currNum, NextWord));
            else
                if(currNum.length() > 0)
                    AddWord(ConvertRegularNum(currNum));
        }
    }

    private String ConvertPhrase(String First,String Second, String Third) {
        String NewPhrase = "";
        NewPhrase = First + "-" + Second + "-" + CheckForDots(Third);
        SkipWords = 4;
        return NewPhrase;
    }

    private String ConvertPhrase(String First,String Second)
    {
        String NewPhrase  = First + "-" + CheckForDots(Second);
        SkipWords=2;
        return NewPhrase;
    }

    private String Stemm(char[] WordbyChars)
    {
        Stemmer stm = new Stemmer();
        for (char c: WordbyChars) {
            stm.add(c);
        }
        stm.stem();
        return stm.toString();
    }

    private String CheckForDots(String Word)
    {
        if (Word.length()==0)
            return Word;
        if (Word.charAt(Word.length()-1)=='.')
            Word = Word.substring(0,Word.length()-1);
        if (Word.length() > 0) {
            if (Word.charAt(0) == '.' || Word.charAt(0) == '/')
                return Word.substring(1);
        }
        return Word;
    }

    private void AddWord(String WordToAdd) {
        if (WordToAdd.length()>0)
        {
            boolean Uppercase;
            if(StemPlz)
            {
                Uppercase = Character.isUpperCase(WordToAdd.charAt(0));
                WordToAdd = Stemm(WordToAdd.toLowerCase().toCharArray());
                if (Uppercase)
                    WordToAdd = WordToAdd.toUpperCase();
            }
            WordToAdd = transWord(WordToAdd);
            if (!WordsInDoc.containsKey(WordToAdd)) {
                WordsInDoc.put(WordToAdd,new Pair(CurrDoc.getDocNo(), 1));
            } else {
                Integer CurrCounter = WordsInDoc.get(WordToAdd).getValue();
                WordsInDoc.replace(WordToAdd,new Pair(CurrDoc.getDocNo(),CurrCounter+1));
                CurrCounter = WordsInDoc.get(WordToAdd).getValue();
                if(CurrCounter > TermCounterMax)
                    TermCounterMax = CurrCounter;
            }
            CountTerm(WordToAdd);
        }
    }

    private boolean checkIfStopWord(String WordToCheck)
    {
        if (StopWords.containsKey(WordToCheck))
            return true;
        else
            return false;
    }

    public void LoadStopWords(File data) throws FileNotFoundException {
        Scanner sc = new Scanner(data);
        if (StopWords == null)
            StopWords = new HashMap<String,String>();
        while(sc.hasNext())
        {
            String NewStopWord = sc.nextLine();
            StopWords.put(NewStopWord,NewStopWord);
        }

    }

    private String lookUpLower(String WordToAdd) {
        if (WordsInDoc.containsKey(WordToAdd.toLowerCase()))
            return WordToAdd.toLowerCase();
        return WordToAdd;
    }

    private void lookUpUpper(String WordToAdd){
        Pair<String,Integer> Temp;
        if(WordsInDoc.containsKey(WordToAdd.toUpperCase())) {
            Temp = WordsInDoc.remove(WordToAdd.toUpperCase());
            WordsInDoc.put(WordToAdd,Temp);
        }
    }

    private String transWord(String word){
        char firstLetter = word.charAt(0);
        if (isNumeric(word.substring(0,1)))
            return word;
        if(Character.isUpperCase(firstLetter)) {
            word = lookUpLower(word);
            return word;
        }
        else {
            lookUpUpper(word);
            return word.toLowerCase();
        }
    }

    private String ConvertDollar (String Number) {
        Double ConvertedNum = 0.0;
        if (Number.length() > 0)
        {
            if (Number.substring(0, 1).equals("$"))
                Number = Number.substring(1, Number.length());
            if (Number.length() > 2)
            {
                if (Number.substring(Number.length()-2,Number.length()).equals("bn")) {
                    return Number.substring(0,Number.length()-2)+"000 M Dollars";
                }
                if (Number.substring(Number.length()-1,Number.length()).equals("m")) {
                    return Number.substring(0,Number.length()-1)+" M Dollars";
                }
                if (Double.parseDouble(Number) > 1000000) {
                    ConvertedNum = Double.parseDouble(Number) / 1000000;
                    Number = ConvertedNum.toString() + " M Dollars";
                    return Number;
                }
            }
            return Number +" Dollars";
        }
        return Number;
    }

    private String ConvertDollar (String Number, String Scale) {
        if (Number.substring(0, 1).equals("$"))
        {
            Number = Number.substring(1, Number.length());
            SkipWords = 1;
            if (Scale.equals("billion")) {
                return Number +"000 M Dollars";
            }
            else if (Scale.equals("million")) {
                return Number +" M Dollars";
            }
        }
        if (Scale.equals("bn")) {
            SkipWords = 2;
            return Number +"000 M Dollars";
        }
        if (Scale.equals("billion")) {
            SkipWords = 3;
            return Number +"000 M Dollars";
        }
        if (Scale.equals("m")) {
            SkipWords = 2;
            return Number +" M Dollars";
        }
        if (Scale.equals("million")) {
            SkipWords = 3;
            return Number +" M Dollars";
        }
        if (Scale.equals("trillion")) {
            SkipWords = 3;
            return Number +"000000 M Dollars";
        }
        return Number;
    }

    private String ConvertRegularNum (String Number) {
        Double ConvertedNum = 0.0;
        if (Double.parseDouble(Number) < 1000) {
            return ConvertDecimal(Number);
        }
        if (Double.parseDouble(Number) < 1000000) {
            ConvertedNum = Double.parseDouble(Number) / 1000;
            Number = ConvertDecimal(ConvertedNum.toString());
            return Number + "K";
        }
        if (Double.parseDouble(Number) < 1000000000) {
            ConvertedNum = Double.parseDouble(Number) / 1000000;
            Number = ConvertDecimal(ConvertedNum.toString());
            return Number + "M";
        }
        if (Double.parseDouble(Number) > 1000000000) {
            ConvertedNum = Double.parseDouble(Number) / 1000000000;
            Number = ConvertDecimal(ConvertedNum.toString());
            return Number + "B";
        }
        return ConvertDecimal(Number);
    }

    private String ConvertRegularNum (String Number, String Scale) {
        SkipWords = 1;
        if (Scale.equals("Billion")) {
            return Number +"B";
        }
        if (Scale.equals("Thousand")) {
            return Number +"K";
        }
        if (Scale.equals("Million")) {
            return Number +"M";
        }
        return Number;
    }

    private String ConvertPercentage (String Number) {
        if (Number.contains("%"))
            return Number;
        else
            SkipWords = 1;
        return Number+"%";
    }

    private String ConvertDecimal (String Number) {
        Double NumberDouble = Double.parseDouble(Number);
        DecimalFormat df = new DecimalFormat("#.###");
        df.format(NumberDouble);
        return  NumberDouble.toString();
    }

    private String ConvertDate (String First, String Second) throws ParseException {
        if (isNumericInt(Second)||isNumericInt(First))
        {
            if (DatestoNums.containsKey(First))
            {
                if (Integer.parseInt((Second)) < 31)
                    return DatestoNums.get(First)+"-"+Second;
                else
                    return Second+"-"+DatestoNums.get(First);
            }
            else
            {
                if (Integer.parseInt((First)) < 31)
                    return DatestoNums.get(Second)+"-"+First;
                else
                    return First+"-"+DatestoNums.get(Second);
            }
        }
        return First;
    }


    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


//
//    private boolean isNumeric(String strNum) {
//        if (strNum == null) {
//            return false;
//        }
//        try {
//            double d = Double.parseDouble(strNum);
//        } catch (NumberFormatException nfe) {
//            return false;
//        }
//        return true;
//    }

    private boolean isNumericInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void AddToDic(){
        for (String key : wordByDoc.keySet()) {
            if(Dictionary.containsKey(key))
            {
                ArrayList<Pair<String,Integer>> TermAppList = wordByDoc.get(key);
                TermInfo NewTermInfo = Dictionary.get(key);
                NewTermInfo.AddtoNumofDocs(TermAppList.size());
            }
        }
    }
// ZHU YUCHENG
//    private void AddToConfirmedEntities(String Term, Pair<String, Integer> otherPair){
//
//            if(ConfirmedEntities.containsKey(Term))
//            {
//                TermInfo EntityInfo = ConfirmedEntities.get(Term);
//                if(EntityInfo.GetLastDocSeen().equals(CurrDoc.getDocNo()))
//                {
//                    EntityInfo.AddAppearances();
//                }
//                else
//                {
//                    EntityInfo.AddtoNumofDocs(1);
//                    EntityInfo.AddAppearances();
//                    EntityInfo.SetLastDoc(CurrDoc.getDocNo());
//                }
//            }
//            else
//            {
//                TermInfo NewTermInfo = new TermInfo(Term, otherPair,CurrDoc.getDocNo());
//                ConfirmedEntities.put(Term,NewTermInfo);
//            }
//    }

    private void CountTerm(String Term){
            if(Dictionary.containsKey(Term))
            {
                TermInfo NewTermInfo = Dictionary.get(Term);
                NewTermInfo.AddAppearances();
            }
            else
            {
                TermInfo NewTermInfo = new TermInfo(Term);
                Dictionary.put(Term, NewTermInfo);
            }
    }

    private void AddToTree() {
        if (wordByDoc == null) {
            wordByDoc = new TreeMap<>(
                    new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {

                            return o1.toUpperCase().compareTo(o2.toUpperCase());
                        }
                    }
            );
        }
        Pair<String, Integer> CurrDocInfo = new Pair<String, Integer>("", 0);
        ArrayList<Pair<String, Integer>> currDocList = new ArrayList<Pair<String, Integer>>();
        for (String key : WordsInDoc.keySet()) {
            currDocList = new ArrayList<Pair<String, Integer>>();
            if (wordByDoc.containsKey(key)) {
                CurrDocInfo = WordsInDoc.get(key);
//                if(CurrDocInfo.getValue()*100 <= WordsInDoc.size())
//                    continue;
                currDocList = wordByDoc.remove(key);
                currDocList.add(CurrDocInfo);
                wordByDoc.put(key, currDocList);
            } else {
//                if(WordsInDoc.get(key).getValue()*100 <= WordsInDoc.size())
//                    continue;
                currDocList.add(WordsInDoc.get(key));
                wordByDoc.put(key, currDocList);
            }
            Dictionary.get(key).SetMaxAppearanceInDoc(WordsInDoc.get(key).getValue(),CurrDoc.getDocNo());
        }
    }

    public void Save() throws ParseException, IOException, ClassNotFoundException {
        Filenum++;
        Index = new Indexer(Filenum,wordByDoc,Currpath);
        Index.savePostingFile(StemPlz);
        AddToDic();
        WordsInDoc = null;
        wordByDoc = null;
    }

    public Collection<TermInfo> GetTerms() {
        return Dictionary.values();
    }

    public void SaveDic() throws ParseException, IOException, ClassNotFoundException {
        Index = new Indexer(Filenum,wordByDoc,Currpath);
        Index.saveDic(StemPlz, Dictionary);
    }

    public void LoadDic(boolean stemPlz, String postingPath) throws ParseException, IOException, ClassNotFoundException {
        Index = new Indexer(Filenum,wordByDoc,Currpath);
        Dictionary = Index.LoadDic(stemPlz, postingPath);
    }

}
