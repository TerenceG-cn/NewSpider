package com.tce.newspider.scheduler;

import com.tce.newspider.http.HttpGetRequest;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;


public class UniqueSchedulerTest {
    @Test
    public void out() throws Exception {
        HttpGetRequest request=new HttpGetRequest("http://www.baidu.com");
        new UniqueScheduler().in(request);

        new UniqueScheduler().out();
    }

    @Test
    public void in() throws Exception {

    }

}