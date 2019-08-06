package cn.cy.model.book;

/**
 * Created by 30721 on 2019/8/2.
 */
public class CrawlingError {

    private int id;
    private String url;
    private int level;
    private String errorMsg;
    private int relationId;
    private int sort;

    public CrawlingError() {
    }

    public CrawlingError(String url, int level, String errorMsg) {
        this.url = url;
        this.level = level;
        this.errorMsg = errorMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "CrawlingError{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", errorMsg='" + errorMsg + '\'' +
                ", relationId=" + relationId +
                ", sort=" + sort +
                '}';
    }
}
