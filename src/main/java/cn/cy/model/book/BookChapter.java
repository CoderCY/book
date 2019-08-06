package cn.cy.model.book;

/**
 * Created by 30721 on 2019/8/1.
 */
public class BookChapter {

    private int id;
    private String chapter;
    private String url;
    private int bookId;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "BookChapter{" +
                "id=" + id +
                ", chapter='" + chapter + '\'' +
                ", url='" + url + '\'' +
                ", bookId=" + bookId +
                ", sort=" + sort +
                '}';
    }
}
