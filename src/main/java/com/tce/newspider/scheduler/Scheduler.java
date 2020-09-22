package com.tce.newspider.scheduler;


import com.tce.newspider.http.HttpRequest;

public interface Scheduler {
    HttpRequest out();//取出url地址

    void in(HttpRequest request);//添加url地址
}
