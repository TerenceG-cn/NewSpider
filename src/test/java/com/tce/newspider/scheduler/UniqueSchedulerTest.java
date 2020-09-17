package com.tce.newspider.scheduler;

import org.apache.http.client.methods.HttpGet;
import org.junit.Test;


public class UniqueSchedulerTest {
    @Test
    public void out() throws Exception {
        HttpGet request=new HttpGet("http://www.baidu.com");
        new UniqueScheduler().in(request);

        new UniqueScheduler().out();
    }

    @Test
    public void in() throws Exception {

    }

}