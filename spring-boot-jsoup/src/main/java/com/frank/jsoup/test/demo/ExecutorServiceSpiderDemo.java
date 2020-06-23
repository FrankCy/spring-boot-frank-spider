package com.frank.jsoup.test.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 通过多线程实现爬虫
 *
 * @author cy
 * @version $Id: ExecutorServiceSpiderDemo.java, v 0.1 2020年05月22日 17:28 cy Exp $
 */
public class ExecutorServiceSpiderDemo {

    private static final int TOTAL_THREADS = 10;//线程数

    public void calculate() {
        //线程安全的list
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        //数据库中的1万条经纬度数据;
        List list = new ArrayList();
        //Executors创建线程池new固定的10个线程
        ExecutorService taskExecutor = Executors.newFixedThreadPool(TOTAL_THREADS);
        final CountDownLatch latch = new CountDownLatch(list.size());//用于判断所有的线程是否结束
        for (int m = 0; m < list.size(); m++) {
            final int n = m;//内部类里m不能直接用,所以赋值给n
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        list.get(n);
                        // 处理业务逻辑
                        System.out.println("处理业务逻辑");
                    } finally {
                        latch.countDown();
                    }
                }
            };
            taskExecutor.execute(run);//开启线程执行池中的任务。还有一个方法submit也可以做到，它的功能是提交指定的任务去执行并且返回Future对象，即执行的结果
        }
        try {
            //等待所有线程执行完毕
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskExecutor.shutdown();//关闭线程池
        // 所有线程执行完毕,执行主线程
        // 循环处理linkedBlockingQueue,然后更新数据库;
        // linkedBlockingQueue是线程安全的,不能用Arraylist,这是我自己的业务逻辑,也许你用不到这个
        // 完毕
    }
}
