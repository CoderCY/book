package cn.cy.dao.book;

import cn.cy.model.book.BookImg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 30721 on 2019/8/1.
 */
@Mapper
public interface IBookImgDao {

    @Insert("insert into boot_book_img(title, url, chapterId, sort) " +
            "values(#{title}, #{url}, #{chapterId}, #{sort})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addBookImg(BookImg bookImg);

    @Select("select * from boot_book_img where chapterId=#{chapterId} order by sort asc")
    List<BookImg> listByChapterId(int chapterId);
}
