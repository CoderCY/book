package cn.cy.util;

import cn.cy.model.Anchor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 30721 on 2019/3/25.
 */
public class ParsingHtmlUtil {

    public final static boolean proxyBool = false;//是否代理

    public final static String proxyHost="192.168.13.19";

    public final static String proxyPort="7777";

    public final static String proxyUser="chenyou";

    public final static String proxyPassword="you123@ff";

    //获取URLConnection 有处理代理
    public static URLConnection urlConnection(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        URLConnection connection = null;
        if (ParsingHtmlUtil.proxyBool) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(ParsingHtmlUtil.proxyUser, ParsingHtmlUtil.proxyPassword.toCharArray());
                }
            });
            SocketAddress sa = new InetSocketAddress(ParsingHtmlUtil.proxyHost, Integer.parseInt(ParsingHtmlUtil.proxyPort));
            Proxy proxy = new Proxy(Proxy.Type.HTTP, sa);
            connection = url.openConnection(proxy);
        } else {
            connection = url.openConnection();
        }
        return connection;
    }

    //获取body标签
    public static Element getBody(String url) throws IOException {
        if (proxyBool) {
            HttpURLConnection conn = (HttpURLConnection) urlConnection(url);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line=br.readLine())!=null) {
                builder.append(line);
            }
            br.close();
            Element element = Jsoup.parse(builder.toString()).body();
            return element;
        } else {
            Connection connection = Jsoup.connect(url).timeout(50000);
            Document doc = connection.get();
            return doc.body();
        }

    }

    //获取整个页面
//    public static Element getHtml(String url) {
//        Connection connection = Jsoup.connect(url).timeout(5000);
//        Document doc = connection.get();
//        return doc.body();
//    }

    //获取a标签信息
    public static List<Anchor> anchorList(Element element) {
        Elements elements = element.select("a");
        return getAnchors(elements);
    }

    public static List<Anchor> anchorList(Element element, String className) {
        Elements elements = element.select("a."+className);
        return getAnchors(elements);
    }

    public static List<Anchor> getAnchors(Elements elements) {
        List<Anchor> anchors = new ArrayList<>();
        for (Element ele: elements) {
            Anchor anchor = new Anchor();
            anchor.setClassName(ele.className());
            anchor.setContent(ele.text());
            anchor.setId(ele.id());
            anchor.setStyle(ele.attr("style"));
            anchor.setHref(ele.attr("href"));
            anchor.setTarget(ele.attr("target"));
            anchor.setTitle(ele.attr("title"));
            anchors.add(anchor);
        }
        return anchors;
    }

}
