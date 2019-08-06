package cn.cy.model.book;

/**
 * Created by 30721 on 2019/8/1.
 */
public class Book {

    private int id;
    private String title;
    private String type;
    private String centent;
    private String author;
    private String url;
    private String latestChapter;
    private String updateTime;
    private String clientType;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCentent() {
        return centent;
    }

    public void setCentent(String centent) {
        this.centent = centent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", centent='" + centent + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", latestChapter='" + latestChapter + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", clientType='" + clientType + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
