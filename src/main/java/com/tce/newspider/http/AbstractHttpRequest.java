package com.tce.newspider.http;

import com.tce.newspider.downloader.AbstractDownloader;

import java.util.Map;

public class AbstractHttpRequest implements HttpRequest{

    private String url;

    private boolean forceUseCharset = false;

    private String charset;

    private Map<String, String> parameters;//参数

    private Map<String, String> cookies;

    private Map<String, String> headers;//请求头

    private long priority;

    public AbstractHttpRequest(){}

    public AbstractHttpRequest(String url){
        this.url=url;
    }


    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(String url) {
        this.url=url;
    }

    @Override
    public void addParameter(String name, String value) {
        this.parameters.put(name,value);
    }

    @Override
    public void setParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public void addHeader(String name, String value) {
        this.headers.put(name,value);
    }

    @Override
    public void addRefer(String refer) {
        this.addHeader("refer",refer);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public void clearHeader() {
        headers.clear();
    }

    @Override
    public String getCharset() {
        return this.charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset=charset;
    }

    @Override
    public void setForceUseCharset(boolean forceUseCharset) {
        this.forceUseCharset=forceUseCharset;
    }

    @Override
    public boolean isForceUseCharset() {
        return this.forceUseCharset;
    }

    @Override
    public com.geccocrawler.gecco.request.HttpRequest subRequest(String url) {
        return null;
    }

    @Override
    public Map<String, String> getCookies() {
        return this.cookies;
    }

    @Override
    public void addCookie(String name, String value) {
        this.cookies.put(name,value);
    }

    @Override
    public String getCookie(String name) {
        return this.cookies.get(name);
    }

    @Override
    public void clearCookie() {
        this.cookies.clear();
    }

    @Override
    public long getPriority() {
        return this.priority;
    }

    @Override
    public void setPriority(long prio) {
        this.priority=prio;
    }
}
