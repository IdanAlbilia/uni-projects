

public class CorpusDoc {
    private String Date;
    private String Title;
    private String DocNo;
    private Integer uniqueWords;
    private Integer max_tf;
    private Integer TextLength;

    public CorpusDoc(String docNo, String date, String title){
        Date = date;
        DocNo = docNo;
        Title = title;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setUniqueWords(int numOfWords) {
        uniqueWords = numOfWords;
    }

    public void setMax_tf(int maxTerm) {
        max_tf = maxTerm;
    }

    public void setTotalWords(int size) {
        TextLength = size;
    }
}
