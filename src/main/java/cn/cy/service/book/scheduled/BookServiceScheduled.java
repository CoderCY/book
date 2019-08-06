package cn.cy.service.book.scheduled;

import cn.cy.dao.book.IBookDao;
import cn.cy.dao.book.ICrawlingErrorDao;
import cn.cy.model.book.Book;
import cn.cy.model.book.ConstantBean;
import cn.cy.model.book.CrawlingError;
import cn.cy.service.book.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by 30721 on 2019/8/3.
 */
@Component
public class BookServiceScheduled {

    @Resource
    private IBookDao bookDao;
    @Resource
    private BookService bookService;
    @Resource
    private ICrawlingErrorDao crawlingErrorDao;

    public void runType(String type) {
        List<Book> list = bookDao.getByType(type);
        for (Book book: list) {
            try {
                bookService.crawlingDetail(book.getUrl(), ConstantBean.CTX);
            } catch (Exception e) {
                CrawlingError error = new CrawlingError(book.getUrl(),2, e.getMessage());
                crawlingErrorDao.addCrawl(error);
                e.printStackTrace();
            }
        }
    }
}
