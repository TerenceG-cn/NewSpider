package com.tce.newspider.http;

import com.tce.newspider.downloader.AbstractDownloader;

import java.util.Map;

/************************************************************************************
 请求头Header内容
     GET(请求的方式) /newcoder/hello.html(请求的目标资源) HTTP/1.1(请求采用的协议和版本号)
     Accept: *|*(客户端能接收的资源类型)Accept-Language: en-us(客户端接收的语言类型)
     Connection: Keep-Alive(维护客户端和服务端的连接关系)
     Host: localhost:8080(连接的目标主机和端口号)
     Referer: http://localhost/links.asp(告诉服务器我来自于哪里)
     User-Agent: Mozilla/4.0(客户端版本号的名字)
     Accept-Encoding: gzip, deflate(客户端能接收的压缩数据的类型)
     If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT(缓存时间)
     Cookie: (客户端暂存服务端的信息)
     Date: Tue, 11 Jul 2000 18:23:51 GMT(客户端请求服务端的时间)
 ***********************************************************************************/
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
