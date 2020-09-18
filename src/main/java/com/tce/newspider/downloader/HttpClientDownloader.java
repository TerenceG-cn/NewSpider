package com.tce.newspider.downloader;


import com.tce.newspider.annotation.Downloader;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.apache.http.util.EntityUtils;


import java.io.*;

@Downloader(name="httpClientDownloader")
public class HttpClientDownloader extends AbstractDownloader {
    private static Log log = LogFactory.getLog(HttpClientDownloader.class);
    private CloseableHttpClient httpClient;
    private HttpClientContext cookieContext = HttpClientContext.create();//TODO cookies


    /*构造器*/
    public HttpClientDownloader() {
        //todo cookies
        //todo ssl证书
        //todo 超时重试


        //设置setRedirectsEnabled false 不可重定向：普通请求正常，但是遇到重定向则停止请求
        RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();

        //提供多线程请求，先采用默认Registry TODO
        //gecco 实现
        //Registry socketFactoryRegistry = null;
        //socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
        PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();

        this.httpClient = HttpClientBuilder.create()
                        .setDefaultRequestConfig(clientConfig)//分配默认RequestConfig实例，如果未在客户端执行上下文中显式设置，该实例将用于请求执行
                        .setConnectionManager(syncConnectionManager)//分配 HttpClientConnectionManager实例
                        .build();
    }



    /**
     * download 1.0
     * @param request HttpRequestBase
     * @param timeout 过期时间 TODO
     * @return HttpResponse
     */
    public org.apache.http.HttpResponse download(HttpRequestBase request, int timeout) {
        //HttpRequestBase request=(HttpRequestBase)req;//TODO
        if (log.isDebugEnabled()) {
            log.debug("downloading..." + request.getURI());
        }
        log.info("运行download");
        CloseableHttpResponse response=null;
        request.setHeader("Accept-Encoding","identity");//不使用Gzip压缩，返回真实content-length
        try{
            response = this.httpClient.execute(request, this.cookieContext);
            //获取响应实体
            HttpEntity httpEntity = response.getEntity();
            log.info(httpEntity.getContent());
            if (httpEntity != null) {
                log.info("长度：\t" + httpEntity.getContentLength());
                System.out.println("内容：\t" + EntityUtils.toString(httpEntity,"UTF-8"));
            }
        }catch (IOException e){
            log.error(e.toString());
        }

        return response;
    }

    @Override
    public HttpResponse download(HttpRequest request, int timeout) throws DownloadException {
        if (log.isDebugEnabled()) {
            log.debug("downloading...from" + request.getUrl());
        }
        return null;//todo
    }

    public void shutdown() {
        try{
            if(httpClient!=null)
                httpClient.close();
        }catch (IOException e){
            log.error(e.toString());
            this.httpClient=null;
        }

    }

    /**
     * 获取响应内容
     * @param instream
     * @param contentLength 响应内容长度
     * @param charset 字符编码
     * @return
     * @throws IOException
     */
    public String getContent(InputStream instream, long contentLength, String charset) throws IOException{
        return null;
    }

    //todo private boolean isImage(String contentType){}

}
