package com.frank.selenium.test.config;

import com.frank.selenium.test.SeleniumApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.selenium.test.config
 * @ClassName: ServletInitializer
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 17:54
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SeleniumApplication.class);
    }

}
