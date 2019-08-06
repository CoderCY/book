package cn.cy.config;

import cn.cy.SpringbootProjectApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 30721 on 2019/8/4.
 */
@Configuration
public class SpringBootServletInitializer extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringbootProjectApplication.class);
    }
}