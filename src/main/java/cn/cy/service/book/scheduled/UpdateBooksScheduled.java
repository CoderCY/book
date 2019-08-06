package cn.cy.service.book.scheduled;

import cn.cy.dao.book.ICrawlingErrorDao;
import cn.cy.model.Template;
import cn.cy.model.book.ConstantBean;
import cn.cy.model.book.CrawlingError;
import cn.cy.service.book.CrawlingService;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 30721 on 2019/8/4.
 */
@Component
@EnableScheduling
public class UpdateBooksScheduled {

    @Resource
    private CrawlingService crawlingService;
    @Resource
    private ICrawlingErrorDao crawlingErrorDao;

    @Scheduled(cron = "0 0 18 1/1 * ? ")
    public void updateBooks() {
        try {
            Template template = new Template();
            template.geBody(ConstantBean.UP_BOOK_URL);
            Element element = template.getElement();
            Elements elements  = element.select("#detail a");
            crawlingService.crawlingAll(elements, ConstantBean.CTX, true);
        } catch (Exception e) {
            CrawlingError error = new CrawlingError(ConstantBean.UP_BOOK_URL,1, e.getMessage());
            crawlingErrorDao.addCrawl(error);
            e.printStackTrace();
        }
    }
}
