package cn.cy.model;

/**
 * Created by 30721 on 2019/3/25.
 */
public class Image {

    private String id;

    private String className;

    private String title;

    private int height;

    private int width;

    private String ismap;

    private String longdesc;

    private String usemap;

    private String src;

    private long sizeB;//字节大小

    private double sizeKb;//kb大小

    private String downloadPath;

    private String format;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getIsmap() {
        return ismap;
    }

    public void setIsmap(String ismap) {
        this.ismap = ismap;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getUsemap() {
        return usemap;
    }

    public void setUsemap(String usemap) {
        this.usemap = usemap;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public long getSizeB() {
        return sizeB;
    }

    public void setSizeB(long sizeB) {
        this.sizeB = sizeB;
    }

    public double getSizeKb() {
        return sizeKb;
    }

    public void setSizeKb(double sizeKb) {
        this.sizeKb = sizeKb;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
