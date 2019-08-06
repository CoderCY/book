package cn.cy.service.book.scheduled;

import cn.cy.dao.book.IBookChapterDao;
import cn.cy.dao.book.ICrawlingErrorDao;
import cn.cy.model.book.BookChapter;
import cn.cy.model.book.ConstantBean;
import cn.cy.model.book.CrawlingError;
import cn.cy.service.book.BookService;
import cn.cy.service.book.CrawlingService;
import org.jsoup.HttpStatusException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 30721 on 2019/8/3.
 */
@Component
@EnableScheduling
public class CrawlingErrorService {

    @Resource
    private ICrawlingErrorDao crawlingErrorDao;
    @Resource
    private BookService bookService;
    @Resource
    private IBookChapterDao bookChapterDao;
    @Resource
    private CrawlingService crawlingService;


    @Scheduled(cron="0 0 0/1 * * ? ")
    public void runErrorReload() {
        List<CrawlingError> list = crawlingErrorDao.getAll();
        for (CrawlingError error: list) {
            try {
                if (error.getLevel() == 3) {
                    BookChapter bookChapter = bookChapterDao.getByUrlAndBookId(error.getRelationId(), error.getUrl());
                    System.out.println("错误章节重新抓取信息--" + bookChapter.getChapter());
                    bookService.crawlingChapterDetail(error.getUrl(), ConstantBean.CTX, bookChapter);
                } else if (error.getLevel() == 1) {
                    crawlingService.crawlingAll(null, ConstantBean.CTX, error.getUrl());
                } else if (error.getLevel() == 2) {
                    bookService.crawlingDetail(error.getUrl(), ConstantBean.CTX);
                }
                crawlingErrorDao.delCrawl(error.getId());
            } catch (HttpStatusException e2){
                if (e2.getStatusCode()== 404) {
                    error.setLevel(0);
                    crawlingErrorDao.update(error);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
