package com.frank.selenium.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.selenium.test.util
 * @ClassName: TwitchSearch
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-05-06 17:55
 */
public class TwitchSearch {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String downLoadSeliunm(String keyword,String webDriverPath){
        String url = "https://www.twitch.tv/";
        System.setProperty("webdriver.chrome.driver",webDriverPath);
        //ChromeDriver服务地址
        ArrayList<String> command = new ArrayList<String>();
        //command.add("--headless");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(command);
        WebDriver driver = new ChromeDriver(options);
        try{
            driver.manage().window().setSize(new Dimension(1920,1080));
            driver.get(url);
            driver.findElement(By.id("nav-search-input")).sendKeys(keyword);
            String docString = driver.getPageSource();
            Document doc  =  Jsoup.parse(docString);
            String liveUrl= parserTwitch(doc);
            return liveUrl;
        }catch (Exception ex){
            logger.info(ex.toString());
        }finally {
            driver.quit();
        }
        return "";
    }


    public String parserTwitch(Document doc){
        Elements searchResultSectionBlock = doc.getElementsByClass("search-result-section__block");
        if(searchResultSectionBlock.size()>0){
            Elements urlA = searchResultSectionBlock.get(0).getElementsByClass("tw-interactive tw-block tw-full-width tw-interactable tw-interactable--inverted");
            String href = urlA.get(0).attr("href");
            return  "https://www.twitch.tv"+href;
        }
        return "";
    }

}
