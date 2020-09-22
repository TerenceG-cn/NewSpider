package com.tce.newspider.downloader;


import com.tce.newspider.http.HttpGetRequest;
import com.tce.newspider.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.junit.Test;


public class HttpClientDownloaderTest {
    @Test
    public void download() throws Exception {
        HttpGet request=new HttpGet("http://www.baidu.com");//要加"http","https":org.apache.http.client.ClientProtocolException
        //HttpRequest req=new HttpGetRequest("http://www.baidu.com");
        HttpRequest req=new HttpGetRequest("https://blog.csdn.net/gengkui9897/article/details/87929235");
        com.tce.newspider.http.HttpResponse response=new HttpClientDownloader().download(req,1000);
        System.out.println("Content:"+response.getContent());
        System.out.println(response.getCharset());
        System.out.println(response.getContentType());
    }

    @Test
    public void shutdown() throws Exception {
    }

    @Test
    public void getContent() throws Exception {
        HttpGet request=new HttpGet("http://www.baidu.com");//要加"http","https":org.apache.http.client.ClientProtocolException
        HttpResponse response=new HttpClientDownloader().download(request,100);

        //System.out.println(new HttpClientDownloader().getContent(response.getEntity().getContent(),response.getEntity().getContentLength(),
        //        "utf-8"));
    }

}