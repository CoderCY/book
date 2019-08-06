package cn.cy.service.book;

import cn.cy.dao.book.IBookChapterDao;
import cn.cy.dao.book.IBookDao;
import cn.cy.dao.book.IBookImgDao;
import cn.cy.dao.book.ICrawlingErrorDao;
import cn.cy.model.Anchor;
import cn.cy.model.Template;
import cn.cy.model.book.*;
import cn.cy.util.ParsingHtmlUtil;
import cn.cy.util.ScriptUtil;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 30721 on 2019/8/1.
 */
@Service
public class BookService {

    @Resource
    private IBookDao bookDao;

    @Resource
    private IBookChapterDao bookChapterDao;

    @Resource
    private IBookImgDao bookImgDao;

    @Resource
    private ICrawlingErrorDao crawlingErrorDao;

    private Pattern pattern = Pattern.compile("cp=\".*\";");

    /**
     * 获取书本详情及记录章节
     * @param url
     * @param ctx
     */
    public void crawlingDetail(String url, String ctx) throws IOException {
        Template template = new Template();
        template.geBody(url);
        Element element = template.getElement();
        //获取书籍基本信息
        Elements elements  = element.select(".book-detail dl>dd");
        Book book = new Book();
        book.setLatestChapter(elements.size()>0?elements.get(0).text().trim():"");
        book.setUpdateTime(elements.size()>1?elements.get(1).text().trim():"");
        book.setAuthor(elements.size()>2?elements.get(2).text().trim():"");
        book.setType(elements.size()>3?elements.get(3).text().trim():"");
        book.setUrl(url);
        book.setCentent(element.select("#bookIntro p").text().trim());
        book.setTitle(element.select("h1").text().trim());
        book.setImgUrl(element.select(".thumb>img").attr("src").trim());
        System.out.println("书名：《" + element.select("h1").text().trim() + "》 类型：" + book.getType());
        Book bookDetail = bookDao.getByTitle(book.getTitle(), book.getUrl());
        if (bookDetail==null) {
            bookDao.addBook(book);
        } else {
            book.setId(bookDetail.getId());
            bookDao.updateBook(book);
        }

        Elements eleList  = template.getElement().select("#chapterList2 a");
        List<Anchor> anchors = ParsingHtmlUtil.getAnchors(eleList);
        int size = anchors.size();
        for (Anchor anchor: anchors) {//获取到章节
            String detailUrl = ctx + anchor.getHref();
            try {
                BookChapter bookChapter = new BookChapter();
                bookChapter.setChapter(anchor.getContent());
                System.out.println("章节名称——" + anchor.getContent());
                bookChapter.setUrl(detailUrl);
                bookChapter.setBookId(book.getId());
                bookChapter.setSort(size);
                BookChapter bc = bookChapterDao.getByChapterAndBookId(bookChapter);

                if (bc==null) {
                    bookChapterDao.addChapter(bookChapter);
                    crawlingChapterDetail(detailUrl,ctx,bookChapter);
                } else if (bookImgDao.listByChapterId(bc.getId()).size()==0) {
                    bookChapter.setId(bc.getId());
                    crawlingChapterDetail(detailUrl,ctx,bookChapter);
                    bookChapterDao.updateSortById(bookChapter);
                } else if (bc.getSort()==0){
                    bookChapter.setId(bc.getId());
                    bookChapterDao.updateSortById(bookChapter);
                } else if (bookImgDao.listByChapterId(bc.getId()).size()>0 && bookDao.getStatusById(book.getId())==1) {
                    break;
                }
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                CrawlingError error = new CrawlingError(detailUrl,3, e.getMessage());
                error.setRelationId(book.getId());
                error.setSort(size);
                crawlingErrorDao.addCrawl(error);
                e.printStackTrace();
            }
            size--;
        }

        bookDao.updateStatusById(book.getId(), 1);
    }

    /**
     * 获取章节图片
     * @param url
     * @param ctx
     * @param bookChapter
     */
    @Transactional
    public void crawlingChapterDetail(String url, String ctx, BookChapter bookChapter) throws IOException {
        Template template = new Template();
        template.geBody(url);
        Element element = template.getElement();
        Elements elements = element.select("script");
        for (Element ele: elements) {
            String str = ele.data();
            if (str.contains("base64EncodeChars") && str.contains("base64DecodeChars")
                    && str.contains("cp")) {
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    Object object = ScriptUtil.getJavaScript(matcher.group());
                    if (object != null) {
                        JSONObject json = JSONObject.fromObject(object);
                        for (Object obj: json.keySet()) {
                            BookImg bookImg = new BookImg();
                            bookImg.setUrl(ConstantBean.IMG_CTX+json.getString(obj.toString()));
                            bookImg.setSort(Integer.parseInt(obj.toString()));
                            bookImg.setChapterId(bookChapter.getId());
                            bookImg.setTitle(bookChapter.getChapter());
                            bookImgDao.addBookImg(bookImg);
                        }
                        break;
                    }
                }
            }
        }
    }

    public List<Book> bookList(Map<String, Object> map) {
        return bookDao.bookList(map);
    }

}
