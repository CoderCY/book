package cn.cy.model;

import cn.cy.util.ParsingHtmlUtil;
import cn.cy.util.ParsingImgUtil;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import javax.lang.model.util.Elements;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by 30721 on 2019/3/25.
 */
public class Template {

    private String url;

    private Element element;

    private List<Anchor> anchors;

    private List<Image> images;

    private Map<String, Object> map;

    private Elements elements;

    public Template() {

    }

    public Template(String url) {

    }

    public Element getElement() {
        return element;
    }

    public List<Anchor> getAnchors() {
        return anchors;
    }

    public List<Image> getImages() {
        return images;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Elements getElements() {
        return elements;
    }

    /**
     * 根据url设值body的element
     * @param url
     * @return
     * @throws IOException
     */
    public Template geBody(String url) throws IOException {
        this.url = url;
        element = ParsingHtmlUtil.getBody(url);
        return this;
    }

    /**
     *设值anchors
     * @return
     */
    public Template getAllAnchor() {
        if (element==null)
            throw new NullPointerException("Template子属性element为空");
        anchors = ParsingHtmlUtil.anchorList(element);
        return this;
    }

    /**
     * 根据className设值anchors
     * @param className
     * @return
     */
    public Template getAnchorsByClassName(String className) {
        if (element==null)
            throw new NullPointerException("Template子属性element为空");
        anchors = ParsingHtmlUtil.anchorList(element, className);
        return this;
    }

    /**
     * 设值images
     * @return
     */
    public Template getAllImg() {
        if (element==null)
            throw new NullPointerException("Template子属性element为空");
        images = ParsingImgUtil.getImgSrc(element);
        return this;
    }

    /**
     * 根据className设值images
     * @param className
     * @return
     */
    public Template getAllImgByClassName(String className) {
        if (element==null)
            throw new NullPointerException("Template子属性element为空");
        images = ParsingImgUtil.getImgSrc(element, className);
        return this;
    }

    /**
     * 设值图片的width 和 height
     * @return
     */
    public Template connectImgSrcSetWidthAndHeight() throws IOException {
        if (images==null)
            throw new NullPointerException("Template子属性images为空");
        for (Image image: images) {
            String src = image.getSrc();
            if (src.startsWith("//")) {
                if (url.startsWith("http:") || url.startsWith("HTTP:")) {
                    src = src.replace("//", "http://");
                } else if (url.startsWith("https:") || url.startsWith("HTTPS:")) {
                    src = src.replace("//", "https://");
                }
            } else if (!src.contains("http") && !src.contains("HTTP")) {
//                String[] strs = this.url.split("//");
//
//                if (src.startsWith("/")) {
//
//                }
                continue;
            }
            try {
                URLConnection connection = ParsingHtmlUtil.urlConnection(src);
                connection.setDoOutput(true);
                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                image.setWidth(bufferedImage.getWidth());
                image.setHeight(bufferedImage.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            image.setFormat(ParsingImgUtil.getImageFormat(src));
            image.setSrc(src);
        }
        return this;
    }

    /**
     * @param path 下载到的文件夹路径
     * @return
     */
    public Template downloadImage(String path) throws IOException {
        if (images==null)
            throw new NullPointerException("Template子属性images为空");
        for (Image image: images) {
            if (image.getSrc().startsWith("http") || image.getSrc().startsWith("HTTP")) {
                URLConnection connection = ParsingHtmlUtil.urlConnection(image.getSrc());
                connection.setDoOutput(true);
                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                image.setWidth(bufferedImage.getWidth());
                image.setHeight(bufferedImage.getHeight());
                image.setFormat(ParsingImgUtil.getImageFormat(image.getSrc()));
                String[] strs = image.getSrc().split("/");
                image.setDownloadPath(path+"/"+strs[strs.length-1]);
                image.setFormat(ParsingImgUtil.getImageFormat(image.getSrc()));
                File file = new File(image.getDownloadPath());
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                //设置请求超时为5s
                connection.setConnectTimeout(5*1000);
                InputStream is = connection.getInputStream();
                byte[] bs = new byte[1024];
                OutputStream os = new FileOutputStream(image.getDownloadPath());
                // 开始读取
                int len;
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                os.close();
                is.close();
            }
        }
        return this;
    }
}
