package com.tce.newspider;

import com.tce.newspider.downloader.HttpClientDownloader;
import com.tce.newspider.scheduler.StartScheduler;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class SCPspider {
    public static void main(String[] args){
        HttpResponse response=null;
        HttpGet request=new HttpGet("http://scp-wiki-cn.wikidot.com/scp-series-cn");

        StartScheduler startScheduler=new StartScheduler();
        startScheduler.in(request);

        HttpClientDownloader downloader=new HttpClientDownloader();
        response=downloader.download(request,1000);

        try{
            String content=response.getEntity().getContent().toString();
            System.out.println(content);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
