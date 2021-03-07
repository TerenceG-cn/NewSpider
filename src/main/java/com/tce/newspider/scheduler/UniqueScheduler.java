package com.tce.newspider.scheduler;

import com.tce.newspider.http.HttpRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import java.util.Comparator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class UniqueScheduler implements Scheduler{

    private static Log log = LogFactory.getLog(UniqueScheduler.class);

    private NavigableSet<PriorityHttpRequest> requests=new ConcurrentSkipListSet<>(
            (o1, o2) -> {
                if(o1.getClass().hashCode()==o2.getRequest().hashCode()&&o1.getRequest().equals(o2.getRequest()))
                    return 0;
                else return o1.getPriorityFlag()-o2.getPriorityFlag()>0L? 1:-1;
            }
    );
    @Override
    public HttpRequest out() {
        PriorityHttpRequest pr=requests.pollFirst();
        HttpRequest request=null;
        if(pr!=null) {
            request = pr.getRequest();
            if (request != null && log.isDebugEnabled()) {
                log.debug("OUT(" + pr.getPriorityFlag() + "):" + request.getUrl());
            }
        }

        return request;
    }

    @Override
    public void in(HttpRequest request) {
        long priority = System.nanoTime();
        boolean success = this.requests.add(new UniqueScheduler.PriorityHttpRequest(priority, request));
        if (success && log.isDebugEnabled()) {
            log.debug("IN(" + priority + "):" + request.getUrl());
        }

        if (!success && log.isDebugEnabled()) {
            log.error("not unique request : " + request.getUrl());
        }
    }

    private class PriorityHttpRequest {
        private long priorityFlag;//优先度标记，System.nanoTime()
        private HttpRequest request;

        private PriorityHttpRequest(long priorityFlag,HttpRequest request){
            this.priorityFlag=priorityFlag;
            this.request=request;
        }

        private long getPriorityFlag() {
            return priorityFlag;
        }

        public HttpRequest getRequest() {
            return request;
        }
    }

    public NavigableSet<PriorityHttpRequest> getRequests() {
        return requests;
    }

    public void setRequests(NavigableSet<PriorityHttpRequest> requests) {
        this.requests = requests;
    }
}
