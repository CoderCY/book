package cn.cy.dao.book;

import cn.cy.model.book.BookChapter;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 30721 on 2019/8/1.
 */
@Mapper
public interface IBookChapterDao {

    @Insert("insert into boot_book_chapter(chapter, url, bookId, sort) " +
            "values(#{chapter}, #{url}, #{bookId}, #{sort})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addChapter(BookChapter bookChapter);

    @Select("select * from boot_book_chapter where bookId=#{bookId} and chapter=#{chapter}")
    BookChapter getByChapterAndBookId(BookChapter bookChapter);

    @Select("select * from boot_book_chapter where bookId=#{bookId} and url=#{url}")
    BookChapter getByUrlAndBookId(@Param("bookId") int bookId, @Param("url") String url);

    @Update("update boot_book_chapter set sort=#{sort} where id=#{id}")
    int updateSortById(BookChapter bookChapter);

    @Select("select * from boot_book_chapter where bookId=#{bookId} order by sort ${px}")
    List<BookChapter> getByBookId(@Param("bookId") int bookId, @Param("px") String px);

    @Select("select * from boot_book_chapter where id=#{id}")
    BookChapter getById(@Param("id") int id);

    /**
     * 获取下一章节
     */
    @Select("select * from boot_book_chapter where bookId = #{bookId} and sort > #{sort} " +
            "ORDER BY sort asc LIMIT 0,1")
    BookChapter getNext(BookChapter bookChapter);

    /**
     * 获取上一章节
     */
    @Select("select * from boot_book_chapter where bookId = #{bookId} and sort < #{sort} " +
            "ORDER BY sort desc LIMIT 0,1")
    BookChapter getPrev(BookChapter bookChapter);
}
