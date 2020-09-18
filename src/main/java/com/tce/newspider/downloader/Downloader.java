package com.tce.newspider.downloader;

import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;


public interface Downloader {
    public HttpResponse download(HttpRequest request, int timeout) throws DownloadException;

    public void shutdown();
}
