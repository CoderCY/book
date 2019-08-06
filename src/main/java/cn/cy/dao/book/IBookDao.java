package cn.cy.dao.book;

import cn.cy.model.book.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 30721 on 2019/8/1.
 */
@Mapper
public interface IBookDao {

    @Insert("insert into boot_book(title,type,centent,author,url,latestChapter,updateTime,clientType, imgUrl) " +
            "values(#{title},#{type},#{centent},#{author},#{url},#{latestChapter},#{updateTime},#{clientType}, #{imgUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addBook(Book book);

    @Select("select * from boot_book where title=#{title} and url=#{url}")
    Book getByTitle(@Param("title") String title, @Param("url") String url);

    @Update("update boot_book set latestChapter=#{latestChapter}, " +
            "title=#{title}," +
            "type=#{type}, " +
            "centent=#{centent}, " +
            "author = #{author}, " +
            "url=#{url}, " +
            "latestChapter=#{latestChapter}, " +
            "updateTime=#{updateTime}, " +
            "clientType=#{clientType}, " +
            "imgUrl=#{imgUrl} " +
            "where id=#{id}")
    int updateBook(Book book);

    @Select("select b.* from boot_book b " +
            " LEFT JOIN boot_crawling_first on b.id= boot_crawling_first.bootId" +
            " where type=#{type} and status=0 " +
            " ORDER BY boot_crawling_first.`order` desc")
    List<Book> getByType(@Param("type") String type);

    @Update("update boot_book set status=#{status} where id=#{id}")
    int updateStatusById(@Param("id") int id, @Param("status") int status);

    @Select("select status from boot_book where id=#{id}")
    int getStatusById(@Param("id") int id);

    @Select({"<script>" +
            "select * from boot_book where 1=1" +
            "<when test='title!=null'>" +
            "and title like #{title}" +
            "</when>" +
            "and status = 1" +
            "<when test='type!=null'>" +
            "and type=#{type}" +
            "</when>" +
            "<when test='author!=null'>" +
            "and author=#{author}" +
            "</when>" +
            "LIMIT #{index},#{pageSize}" +
            "</script>"})
    List<Book> bookList(Map<String, Object> map);

    @Select("select * from boot_book where id=#{id}")
    Book getById(@Param("id") int id);
}
