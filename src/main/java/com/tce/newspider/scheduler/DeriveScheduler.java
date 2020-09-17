package com.tce.newspider.scheduler;

import org.apache.http.HttpRequest;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 初始地址生成的派生地址管理
 */
public class DeriveScheduler implements Scheduler{

    private ConcurrentLinkedQueue<HttpRequest> requests=new ConcurrentLinkedQueue<>();

    @Override
    public HttpRequest out() {
        return requests.poll();
    }

    @Override
    public void in(HttpRequest request) {
        //无界队列,永远不会return false;
        requests.offer(request);
    }
}
