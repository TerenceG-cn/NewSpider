package com.tce.newspider.http;

import java.util.Map;

public interface HttpRequest extends Cloneable {

    public String getUrl();

    public void setUrl(String url);

    public void addParameter(String name, String value);

    public void setParameters(Map<String, String> parameters);

    public String getParameter(String name);

    public Map<String, String> getParameters();

    public void addHeader(String name, String value);

    public void addRefer(String refer);

    public Map<String, String> getHeaders();

    public void clearHeader();



    public String getCharset();

    public void setCharset(String charset);

    public void setForceUseCharset(boolean forceUseCharset);

    public boolean isForceUseCharset();

    public com.geccocrawler.gecco.request.HttpRequest subRequest(String url);

    public Map<String, String> getCookies();

    public void addCookie(String name, String value);

    public String getCookie(String name);

    public void clearCookie();

    public long getPriority();

    public void setPriority(long prio);
}
