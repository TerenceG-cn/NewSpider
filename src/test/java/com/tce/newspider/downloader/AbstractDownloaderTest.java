package com.tce.newspider.downloader;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractDownloaderTest {
    @Test
    public void getCharset() throws Exception {
        AbstractDownloader abstractDownloader=new HttpClientDownloader();
        System.out.println(abstractDownloader.getCharset("gbk","content-type: application/json; charset=   g b r 4    89;danidan"));
    }

}