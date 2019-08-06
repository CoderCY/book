package cn.cy.service.book;

import cn.cy.dao.book.IBookDao;
import cn.cy.dao.book.ICrawlingErrorDao;
import cn.cy.model.Anchor;
import cn.cy.model.Template;
import cn.cy.model.book.Book;
import cn.cy.model.book.CrawlingError;
import cn.cy.util.ParsingHtmlUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by 30721 on 2019/8/2.
 */
@Service
public class CrawlingService {

    @Resource
    private ICrawlingErrorDao crawlingErrorDao;

    @Resource
    private BookService bookService;

    @Resource
    private IBookDao bookDao;
    /**
     * 爬取书名列表页
     * @param url
     * @param ctx
     */
    public void crawlingList(String url, String ctx) {
        Template template = new Template();
        try {
            template.geBody(url);
            Element element = template.getElement();
            Elements elements  = element.select("#detail a");
            crawlingAll(elements, ctx, url);
            //获取下一页
            Elements eleList  = element.select("a[target=_self]");
            for (Anchor anchor: ParsingHtmlUtil.getAnchors(eleList)) {
                if (anchor.getContent().equals("下一页")) {
                    crawlingList(ctx + anchor.getHref(), ctx);
                    break;
                }
            }
        } catch (Exception e) {
            CrawlingError error = new CrawlingError(url,1, e.getMessage());
            crawlingErrorDao.addCrawl(error);
            e.printStackTrace();
        }

    }

    public void crawlingAll(Elements elements, String ctx, String url) throws IOException {
        if (elements==null && url!=null) {
            Template template = new Template();
            template.geBody(url);
            Element element = template.getElement();
            elements  = element.select("#detail a");
        }
        crawlingAll(elements, ctx, false);
    }

    public void crawlingAll(Elements elements, String ctx, boolean bool) throws IOException {
        for (Element anchor: elements){//获取到a标签
            String detailUrl = ctx + anchor.attr("href");
            try {
                Elements eles = anchor.select("dd");
                Book book = new Book();
                book.setAuthor(eles.size()>0?eles.get(0).text().trim():"");
                book.setType(eles.size()>1?eles.get(1).text().trim():"");
                book.setLatestChapter(eles.size()>2?eles.get(2).text().trim():"");
                book.setUpdateTime(eles.size()>3?eles.get(3).text().trim():"");
                book.setUrl(detailUrl);
                book.setTitle(anchor.select("h3").text().trim());
                book.setImgUrl(anchor.select("img").attr("src"));
                System.out.println("书名：《" + book.getTitle() + "》");
                Book bookDetail = bookDao.getByTitle(book.getTitle(),book.getUrl());
                if (bookDetail==null) {
                    bookDao.addBook(book);
                } else {
                    book.setId(bookDetail.getId());
                    book.setCentent(bookDetail.getCentent());
                    bookDao.updateBook(book);
                    if (bool) {
                        bookService.crawlingDetail(detailUrl, ctx);
                    }
                }
            } catch (Exception e) {
                CrawlingError error = new CrawlingError(detailUrl,2, e.getMessage());
                crawlingErrorDao.addCrawl(error);
                e.printStackTrace();
            }
        }
    }
}
