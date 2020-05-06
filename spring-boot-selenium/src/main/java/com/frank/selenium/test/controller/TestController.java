package com.frank.selenium.test.controller;

import com.frank.selenium.test.util.TwitchSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.selenium.test.controller
 * @ClassName: TestController
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 17:55
 */
@RestController
public class TestController {

    @Value("${web.driver}")
    private String webDriverPath;

    @Scheduled(cron = "0/50 0 * * * ? ")
    public void getTwurl(){
        TwitchSearch twitchSearch = new TwitchSearch();
        String searchKey = "dota2 psg.lgd";
        String url = twitchSearch.downLoadSeliunm(searchKey,webDriverPath);
        if(url!=null)
            System.out.println(url);
    }

}
