package com.frank.jsoup.test;

import com.frank.jsoup.test.util.JsoupUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 *
 * @author cy
 * @version $Id: Demo16.java, v 0.1 2020年05月11日 13:42 cy Exp $
 */
public class Demo16 {

    /**
     * 点击百度搜索按钮
     */
    public static void baiduClick() throws Exception {

        String url = "https://www.baidu.com";
        WebDriver webDriver = JsoupUtil.getDriver(false);
        webDriver.get(url);
        // 获取百度输入参数的位置
        WebElement kwWebElement = webDriver.findElement(By.xpath("//*[@id=\"kw\"]"));

        // 输入参数
        kwWebElement.sendKeys("弗兰克");
        System.out.println("输入参数结束");
        Thread.sleep(1000);

        // 获取搜索并点击
        WebElement clickWebElement=  webDriver.findElement(By.xpath("//*[@id=\"su\"]"));
        clickWebElement.submit();
        System.out.println("搜索点击等待");
        Thread.sleep(4000);

        System.out.println("当前地址(第一页)："+ webDriver.getCurrentUrl());

    }

    public static void main(String[] args) throws Exception {
        baiduClick();
    }

}
