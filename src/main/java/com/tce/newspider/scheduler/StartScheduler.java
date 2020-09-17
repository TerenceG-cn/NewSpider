package com.tce.newspider.scheduler;

import org.apache.http.HttpRequest;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 初始url管理
 */
public class StartScheduler implements Scheduler {
    /**
     * 阻塞队列FIFO，存放HttpRequst
     */
    private LinkedBlockingQueue<HttpRequest> requestQueue = new LinkedBlockingQueue<>();

    @Override
    public HttpRequest out() {
        try{
            //若队列为空，发生阻塞，等待有元素
            return requestQueue.take();
        //当阻塞方法收到中断请求的时候就会抛出InterruptedException异常
        }catch (InterruptedException e){
            e.printStackTrace();
            //通过调用Thread.currentThread().interrupt()，你可以设置线程的中断标志，因此更高级别的中断处理程序会注意到它并且可以正确处理它。
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public void in(HttpRequest request) {
        try {
            //若队列满，发生阻塞，等待有元素被取出
            requestQueue.put(request);
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
