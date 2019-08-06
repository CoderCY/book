package cn.cy.controller.book;

import cn.cy.dao.book.IBookChapterDao;
import cn.cy.model.book.Book;
import cn.cy.model.book.BookChapter;
import cn.cy.service.book.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 30721 on 2019/8/4.
 */
@RestController
@RequestMapping("/book")
public class BookRestController {

    @Resource
    private BookService bookService;

    @Resource
    private IBookChapterDao bookChapterDao;

    @RequestMapping(value = "/bookList", method=RequestMethod.POST)
    public List<Book> bookList(@RequestParam(value = "index", required = false, defaultValue = "0") int index,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "author", required = false) String author){
        Map<String, Object> map = new HashMap<>();
        if (title!=null) {
            map.put("title", "%" + title + "%");
        }
        map.put("type", type);
        map.put("author", author);
        if (pageSize==0) {
            pageSize = 25;
        }
        map.put("index", index * pageSize);
        map.put("pageSize", pageSize);
        return bookService.bookList(map);
    }

    @RequestMapping(value = "/bookChapters", method=RequestMethod.POST)
    public List<BookChapter> bookChapters(@RequestParam("bookId") int bookId,
                                          @RequestParam(value = "px", required = false, defaultValue = "desc") String px) {
        return bookChapterDao.getByBookId(bookId, px);
    }

    @RequestMapping(value = "/aroundChapter", method=RequestMethod.POST)
    public Map<String, Object> bookImgs(@RequestParam("chapterId") int chapterId) {
        BookChapter bookChapter = bookChapterDao.getById(chapterId);
        BookChapter next = bookChapterDao.getNext(bookChapter);
        BookChapter prev = bookChapterDao.getPrev(bookChapter);
        Map<String, Object> map = new HashMap<>();
        if (next!=null) {
            map.put("next", next.getId());
        } else {
            map.put("next", 0);
        }
        if (prev!=null) {
            map.put("prev", prev.getId());
        } else {
            map.put("prev", 0);
        }
        return map;
    }


}
