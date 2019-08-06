package cn.cy.core;

import cn.cy.service.book.scheduled.BookServiceScheduled;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 30721 on 2019/8/6.
 */
@Component
@Order(value = 60)
public class StartServiceRunner implements ApplicationRunner {

    @Resource
    private BookServiceScheduled bookServiceScheduled;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("开启爬虫功能========================================");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("====================少年热血====================");
                    bookServiceScheduled.runType("少年热血");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================少女爱情====================");
                    bookServiceScheduled.runType("少女爱情");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================武侠格斗====================");
                    bookServiceScheduled.runType("武侠格斗");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================科幻魔幻====================");
                    bookServiceScheduled.runType("科幻魔幻");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================竞技体育====================");
                    bookServiceScheduled.runType("竞技体育");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================搞笑喜剧====================");
                    bookServiceScheduled.runType("搞笑喜剧");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================侦探推理====================");
                    bookServiceScheduled.runType("侦探推理");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("====================恐怖灵异====================");
                    bookServiceScheduled.runType("恐怖灵异");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

}
