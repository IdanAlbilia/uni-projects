public class Query
{
    private String query;
    private String queryNum;
    private String queryDesc;

    public Query(String query, String queryNum){
        this.query = query;
        this.queryNum = queryNum;
    }


    public String getQueryNum() {
        return queryNum;
    }

    public void setQueryNum(String queryNum) {
        this.queryNum = queryNum;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryDesc() {
        return queryDesc;
    }

    public void setQueryDesc(String queryDesc) {
        this.queryDesc = queryDesc;
    }
}
