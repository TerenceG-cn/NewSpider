package com.tce.newspider.downloader;

public class DownloadException extends Exception{
    //todo
    public DownloadException(Throwable cause) {
        super(cause);
    }

    public DownloadException(String message) {
        super(message);
    }
}
