package com.tce.newspider.downloader.spider;

import com.geccocrawler.gecco.spider.Spider;

public class SpiderThreadLocal {
    private static ThreadLocal<Spider> spiderThreadLocal = new ThreadLocal();

    public SpiderThreadLocal() {
    }

    public static void set(Spider spider) {
        spiderThreadLocal.set(spider);
    }

    public static Spider get() {
        return (Spider)spiderThreadLocal.get();
    }
}
