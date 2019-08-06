package cn.cy.dao.book;

import cn.cy.model.book.CrawlingError;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 30721 on 2019/8/2.
 */
@Mapper
public interface ICrawlingErrorDao {

    @Insert("insert into boot_crawling_error(url, level, errorMsg, relationId, sort) " +
            "values(#{url}, #{level}, #{errorMsg}, #{relationId}, #{sort})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCrawl(CrawlingError crawlingError);

    @Select("select * from boot_crawling_error where level>0")
    List<CrawlingError> getAll();

    @Delete("delete from boot_crawling_error where id=#{id}")
    int delCrawl(int id);

    @Update("update boot_crawling_error set url=#{url}, level=#{level}, errorMsg=#{errorMsg} " +
            "where id=#{id}")
    int update(CrawlingError crawlingError);
}
