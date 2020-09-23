package com.tce.newspider;

import com.tce.newspider.http.HttpGetRequest;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.scheduler.Scheduler;
import com.tce.newspider.scheduler.StartScheduler;
import com.tce.newspider.spider.Spider;
import com.tce.newspider.spiderbean.SpiderBeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。
 * 引擎包括Scheduler、Downloader、Spider、SpiderBeanFactory4个主要模块
 */
public class SpiderEngine<V> extends Thread implements Callable<V> {

    private List<HttpRequest> startRequests = new ArrayList<HttpRequest>();

    private EventListener eventListener;

    private static Log log = LogFactory.getLog(SpiderEngine.class);

    private Scheduler scheduler;//URL地址管理

    private SpiderBeanFactory spiderBeanFactory;

    private List<Spider> spiders;

    private String classpath;

    private int threadCount;

    private CountDownLatch countDownLatch;

    public SpiderEngine(){

    }
    /**
     * 动态配置规则不能使用该方法构造GeccoEngine
     *
     * @return GeccoEngine
     */
    public static SpiderEngine create() {
        SpiderEngine spiderEngine = new SpiderEngine();
        spiderEngine.setName("SpiderEngine");
        return spiderEngine;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
    public SpiderBeanFactory getSpiderBeanFactory() {
        return spiderBeanFactory;
    }

    public SpiderEngine seed(String url) {
        return seed(new HttpGetRequest(url));
    }

    public SpiderEngine seed(HttpRequest request) {
        this.startRequests.add(request);
        return this;
    }

    public SpiderEngine thread(int count) {
        this.threadCount = count;
        return this;
    }

    @Override
    public synchronized void start() {
        if (eventListener != null) {
            eventListener.onStart(this);
        }
        super.start();
    }

    /**
     * spider线程告知engine执行结束
     */
    public void notifyComplete() {
        this.countDownLatch.countDown();//当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
    }

    @Override
    public V call() throws Exception {
        return null;
    }

    @Override
    public void run() {

        if (scheduler == null) {
            scheduler = new StartScheduler();

        }
        if (spiderBeanFactory == null) {
            spiderBeanFactory = new SpiderBeanFactory();//todo
        }
        if (threadCount <= 0) {
            threadCount = 1;
        }
        this.countDownLatch = new CountDownLatch(threadCount);
        if (startRequests.isEmpty()) {
            // startRequests不为空
            // throw new IllegalArgumentException("startRequests cannot be empty");
        }
        for (HttpRequest startRequest : startRequests) {
            scheduler.in(startRequest);
        }
        spiders = new ArrayList<Spider>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Spider spider = new Spider(this);
            spiders.add(spider);
            Thread thread = new Thread(spider, "T" + classpath + i);
            thread.start();
        }
    }
}
