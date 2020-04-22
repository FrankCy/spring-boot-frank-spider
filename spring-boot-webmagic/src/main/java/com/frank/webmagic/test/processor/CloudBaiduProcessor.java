package com.frank.webmagic.test.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.webmagic.test.processor
 * @ClassName: CloudBaiduProcessor
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-21 21:09
 */
public class CloudBaiduProcessor implements PageProcessor {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public static int count = 0;

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());
        //获取页面需要的内容
        page.putField("test",page.getHtml().xpath("//div[@class=\"para\"]/b/text()"));
        page.putField("authname", page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()"));
//        auth.setName(page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()").get());
        page.putField("", page.getHtml().xpath(""));
        System.out.println("抓取的内容：" +
                page.getHtml().css("#Header1_HeaderTitle", "text").get() +
                page.getHtml().xpath("//*[@id=\"mainContent\"]/div/a/@name").get() +
                page.getHtml().xpath("//*[@id=\"home\"]/div[@id=\"footer\"]/text()").get() +
                page.getHtml().xpath("//*[@id=\"cnblogs_post_body\"]/p/strong/text()").get() +
                page.getHtml().xpath("//div[@class=\"para\"]/b/text()").get()
        );
        count++;
    }

}