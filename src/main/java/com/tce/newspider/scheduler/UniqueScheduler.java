package com.tce.newspider.scheduler;

import com.tce.newspider.downloader.HttpClientDownloader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;


import java.util.Comparator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class UniqueScheduler implements Scheduler{

    private static Log log = LogFactory.getLog(UniqueScheduler.class);

    private NavigableSet<PriorityHttpRequest> requests=new ConcurrentSkipListSet<>(
            new Comparator<PriorityHttpRequest>() {
                @Override
                public int compare(PriorityHttpRequest o1, PriorityHttpRequest o2) {
                    if(o1.getClass().hashCode()==o2.getRequest().hashCode()&&o1.getRequest().equals(o2.getRequest()))
                        return 0;
                    else return o1.getPriorityFlag()-o2.getPriorityFlag()>0L? 1:-1;
                }
            }
    );
    @Override
    public HttpRequest out() {
        PriorityHttpRequest pr=requests.pollFirst();
        HttpRequest request=null;
        if(pr!=null) {
            request = pr.getRequest();
            if (request != null && log.isDebugEnabled()) {
                log.debug("OUT(" + pr.getPriorityFlag() + "):" + request.getRequestLine().getUri() + "(Headers:" + request.getHeaders("Referer").length + ")");
            }
        }

        return request;
    }

    @Override
    public void in(HttpRequest request) {
        long priority = System.nanoTime();
        boolean success = this.requests.add(new UniqueScheduler.PriorityHttpRequest(priority, request));
        if (success && log.isDebugEnabled()) {
            log.debug("INTO(" + priority + "):" + request.getRequestLine().getUri() + "(Headers:" + request.getHeaders("Referer")[0].getValue() + ")");
        }

        if (!success && log.isDebugEnabled()) {
            log.error("not unique request : " + request.getRequestLine().getUri());
        }
    }

    private class PriorityHttpRequest {
        private long priorityFlag;//优先度标记，System.nanoTime()
        private HttpRequest request;

        public PriorityHttpRequest(long priorityFlag,HttpRequest request){
            this.priorityFlag=priorityFlag;
            this.request=request;
        }

        public long getPriorityFlag() {
            return priorityFlag;
        }

        public HttpRequest getRequest() {
            return request;
        }
    }

    public static void main(String[] args){
        HttpGet request=new HttpGet("http://www.baidu.com");
        request.setHeader("Referer","这是Refer的内容");
        UniqueScheduler scheduler=new UniqueScheduler();
        scheduler.in(request);

        new HttpClientDownloader().download((HttpRequestBase) scheduler.out(),1000);
    }

}
