package com.tce.newspider.downloader;

import java.util.Map;

public class DownloaderFactory {

    public static final String DEFAULT_DWONLODER = "httpClientDownloader";

    private Map<String, Downloader> downloaders;
    public Downloader defaultDownloader() {
        return downloaders.get(DEFAULT_DWONLODER);
    }
}
