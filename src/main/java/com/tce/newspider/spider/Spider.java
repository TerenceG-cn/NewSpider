package com.tce.newspider.spider;

import com.tce.newspider.SpiderEngine;
import com.tce.newspider.downloader.DownloadException;
import com.tce.newspider.downloader.Downloader;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import com.tce.newspider.scheduler.Scheduler;
import com.tce.newspider.spiderbean.SpiderBean;
import com.tce.newspider.spiderbean.render.Render;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.util.concurrent.CountDownLatch;

public class Spider implements Runnable {
    private static Log log = LogFactory.getLog(Spider.class);

    //private CountDownLatch pauseCountDown;//闭锁

    private SpiderEngine engine;

    private Scheduler spiderScheduler;

    public Scheduler getSpiderScheduler() {
        return spiderScheduler;
    }

    @Override
    public void run() {
        //将spider放入线程本地变量，之后需要使用
        SpiderThreadLocal.set(this);

        while(true){
            //获取待抓取的url
            //boolean start = false;
            HttpRequest request = spiderScheduler.out();
            if(request == null) {
                //startScheduler
                request = engine.getScheduler().out();
                if(request == null) {
                    //告知engine线程执行结束
                    engine.notifyComplete();
                    break;
                }
                //start = true;
            }
            if(log.isDebugEnabled()) {
                log.debug("match url : " + request.getUrl());
            }

            //download
            HttpResponse response = null;
            try{
                response = download(request);
                if(response.getStatus() == 200) {
                    //render
                    Render render = null;//todo

                    SpiderBean spiderBean = null;
                    //spiderBean = render.inject(null, request, response);

                    log.info(spiderBean.toString());

                    //todo pipelines

                } else if(response.getStatus() == 302 || response.getStatus() == 301){
                    spiderScheduler.in(request.subRequest(response.getContent()));
                }
            }catch (DownloadException e){
                e.printStackTrace();
            }





            //开始地址放入队尾重新抓取
//            if(start && engine.isLoop()) {
//                //如果是一个开始抓取请求，再返回开始队列中
//                engine.getScheduler().in(request);
//            }
        }

    }


    private HttpResponse download(HttpRequest request) throws DownloadException {
        Downloader currDownloader = null;
        int timeout = 1000;
        currDownloader = engine.getSpiderBeanFactory().getDownloaderFactory().defaultDownloader();
        HttpResponse response = currDownloader.download(request, timeout);
        return response;
    }


}
