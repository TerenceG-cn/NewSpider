package com.tce.newspider.downloader;

import com.geccocrawler.gecco.request.HttpGetRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import static org.junit.Assert.*;

public class HttpClientDownloaderTest {
    @Test
    public void download() throws Exception {
        HttpGet request=new HttpGet("http://www.baidu.com");//要加"http","https":org.apache.http.client.ClientProtocolException

        new HttpClientDownloader().download(request,100);
    }

    @Test
    public void shutdown() throws Exception {
    }

    @Test
    public void getContent() throws Exception {
        HttpGet request=new HttpGet("http://www.baidu.com");//要加"http","https":org.apache.http.client.ClientProtocolException
        HttpResponse response=new HttpClientDownloader().download(request,100);

        System.out.println(new HttpClientDownloader().getContent(response.getEntity().getContent(),response.getEntity().getContentLength(),
                "utf-8"));
    }

}