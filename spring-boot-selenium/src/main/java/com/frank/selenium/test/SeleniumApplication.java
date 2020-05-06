package com.frank.selenium.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.selenium.test
 * @ClassName: SeleniumApplication
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 17:52
 */
@SpringBootApplication
@EnableScheduling
public class SeleniumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeleniumApplication.class, args);
    }

}
