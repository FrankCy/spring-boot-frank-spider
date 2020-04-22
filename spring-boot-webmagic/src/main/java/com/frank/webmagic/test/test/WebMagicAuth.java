package com.frank.webmagic.test.test;

import com.frank.webmagic.test.processor.CloudBaiduProcessor;
import com.frank.webmagic.test.processor.MyProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @ProjectName: spring-boot-frank-spider
 * @Package: com.frank.webmagic.test.test
 * @ClassName: WebMagicAuth
 * @Author: cy
 * @Description: ${description}
 * @Date: 2020-04-21 20:31
 */
@Component
public class WebMagicAuth {

    MyProcessor myProcessor = new MyProcessor();

    CloudBaiduProcessor cloudBaidu = new CloudBaiduProcessor();

    public void spiderStart() {
        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        Spider.create(myProcessor).addUrl("https://www.cnblogs.com/")
                .addPipeline(new ConsolePipeline())
                .thread(1)
                .run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+MyProcessor.count+"条记录");
    }

    public void spiderCloudBaiduStart() {
        long startTime, endTime;
        System.out.println("爬百度云");
        startTime = System.currentTimeMillis();
        Spider.create(cloudBaidu).addUrl("https://cloud.baidu.com/campaign/PromotionActivity/index.html")
                .addPipeline(new ConsolePipeline())
                .thread(1)
                .run();
        endTime = System.currentTimeMillis();
        System.out.println("百度云爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+MyProcessor.count+"条记录");
    }

    public static void main(String[] args) {
        WebMagicAuth webMagicAuth = new WebMagicAuth();
//        webMagicAuth.spiderStart();
        webMagicAuth.spiderCloudBaiduStart();
    }

}
