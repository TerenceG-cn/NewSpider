package com.tce.newspider;

import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.scheduler.Scheduler;
import com.tce.newspider.spider.Spider;
import com.tce.newspider.spiderbean.SpiderBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。
 * 引擎包括Scheduler、Downloader、Spider、SpiderBeanFactory4个主要模块
 */
public class SpiderEngine {


    private static Log log = LogFactory.getLog(SpiderEngine.class);

    private Scheduler scheduler;//URL地址管理

    private SpiderBeanFactory spiderBeanFactory;

    private List<Spider> spiders;

    private String classpath;

    private int threadCount;

    private CountDownLatch countDownLatch;

    public SpiderEngine(){

    }

    public Scheduler getScheduler() {
        return scheduler;
    }
    public SpiderBeanFactory getSpiderBeanFactory() {
        return spiderBeanFactory;
    }

    /**
     * spider线程告知engine执行结束
     */
    public void notifyComplete() {
        this.countDownLatch.countDown();//当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
    }

}
