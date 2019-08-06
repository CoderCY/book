package cn.cy.util;

import cn.cy.model.Image;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 30721 on 2019/3/25.
 */
public class ParsingImgUtil {

    //获取图片标签的信息
    public static List<Image> getImgSrc(Element element){
        Elements elements = element.select("img");
        return getAllImg(elements);
    }

    //获取图片标签的信息
    public static List<Image> getImgSrc(Element element, String className){
        Elements elements = element.select("img."+className);
        return getAllImg(elements);
    }

    //获取图片标签的信息
    public static List<Image> getAllImg(Elements elements){
        List<Image> images = new ArrayList<>();
        for (Element ele: elements) {
            Image image = new Image();
            String src = ele.attr("src");
            if (src != null && !src.isEmpty()) {
                image.setSrc(src);
                image.setClassName(ele.className());
                image.setId(ele.id());
                image.setTitle(ele.attr("title"));
                image.setIsmap(ele.attr("ismap"));
                image.setLongdesc(ele.attr("longdesc"));
                image.setUsemap(ele.attr("usemap"));
                images.add(image);
            }
        }
        return images;
    }

    //获取src图片信息
    public static Map<String, Object> getImgInfoBySrc(String src) throws IOException {
        BufferedImage image = bufferedImage(src);
        int srcWidth = image .getWidth();      // 源图宽度
        int srcHeight = image .getHeight();    // 源图高度

        Map<String, Object> map = new HashMap<>();
        map.put("src", src);
        map.put("width", srcWidth);
        map.put("height", srcHeight);
        return map;
    }

    //获取图片IO流
    public static BufferedImage bufferedImage(String src) throws IOException {
        URLConnection connection = ParsingHtmlUtil.urlConnection(src);
        connection.setDoOutput(true);
        return ImageIO.read(connection.getInputStream());
    }

    public static String getImageFormat(String src) {
        if (src.contains("?")) {
            src = src.split("\\?")[0];
        }
        String[] strs = src.split("\\.");
        if (strs.length>1){
            return strs[strs.length-1];
        }
        return "unknown";
    }

}
