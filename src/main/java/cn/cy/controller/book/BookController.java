package cn.cy.controller.book;

import cn.cy.dao.book.IBookChapterDao;
import cn.cy.dao.book.IBookImgDao;
import cn.cy.model.book.BookChapter;
import cn.cy.model.book.BookImg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 30721 on 2019/8/5.
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Resource
    private IBookChapterDao bookChapterDao;
    @Resource
    private IBookImgDao bookImgDao;

    @RequestMapping(value = "/home", method= RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("book/home");
        return mv;
    }

    @RequestMapping(value = "/hotBook", method= RequestMethod.GET)
    public ModelAndView hotBook() {
        ModelAndView mv = new ModelAndView("book/hotBook");
        return mv;
    }

    @RequestMapping(value="/chapters/{bookId}", method= RequestMethod.GET)
    public ModelAndView chapters(@PathVariable int bookId) {
        ModelAndView mv = new ModelAndView("book/chapters");
        List<BookChapter> chapters = bookChapterDao.getByBookId(bookId, "desc");
        mv.addObject("bookId", bookId);
        mv.addObject("chapters", chapters);
        return mv;
    }

    @RequestMapping(value="/imgs/{chapterId}", method= RequestMethod.GET)
    public ModelAndView imgs(@PathVariable int chapterId) {
        ModelAndView mv = new ModelAndView("book/bookImgs");
        List<BookImg> imgs = bookImgDao.listByChapterId(chapterId);
        mv.addObject("chapterId", chapterId);
        mv.addObject("imgs", imgs);
        mv.addObject("chapter", bookChapterDao.getById(chapterId));
        return mv;
    }
}
