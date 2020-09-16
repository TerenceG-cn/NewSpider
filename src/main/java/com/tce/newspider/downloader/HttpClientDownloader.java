package com.tce.newspider.downloader;


//以上需要自己重新实现

import com.sun.org.apache.bcel.internal.generic.IXOR;
import com.tce.newspider.downloader.spider.SpiderThreadLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;


import java.io.*;

public class HttpClientDownloader {
    private static Log log = LogFactory.getLog(HttpClientDownloader.class);
    private CloseableHttpClient httpClient;



    /*构造器*/
    public HttpClientDownloader() {

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
                        .setRetryHandler(new HttpRequestRetryHandler() {
                            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                                int retryCount = SpiderThreadLocal.get().getEngine().getRetry();
                                boolean retry = executionCount <= retryCount;
                                if (HttpClientDownloader.log.isDebugEnabled() && retry) {
                                    HttpClientDownloader.log.debug("retry : " + executionCount);
                                }
                                return retry;
                            }
                        })
                        .build();
    }


    private HttpClientContext cookieContext = HttpClientContext.create();//TODO

    /**
     * download 1.0
     * @param request HttpRequestBase
     * @param timeout 过期时间 TODO
     * @return HttpResponse
     */
    public HttpResponse download(HttpRequestBase request, int timeout) {
        if (log.isDebugEnabled()) {
            log.debug("downloading..." + request.getURI());
        }
        log.info("运行");
        org.apache.http.HttpResponse response=null;
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

    public void shutdown() {
        try{
            if(httpClient!=null)
                httpClient.close();
        }catch (IOException e){
            log.error(e.toString());
            this.httpClient=null;
        }

    }


    public String getContent(InputStream instream, long contentLength, String charset) throws IOException{
        //BufferedInputStream instream=new BufferedInputStream(instreamSRC);
        try {
            if (instream == null) {
                return null;
            } else {
//                int i = (int)contentLength;
//                if (i < 0) {
//                    i = 4096;
//                } 设置必须获得真实conten-type

                int i = (int)contentLength;
                Reader reader = new InputStreamReader(instream, charset);
                CharArrayBuffer buffer = new CharArrayBuffer(i);
                char[] tmp = new char[1024];

                int l;
                while((l = reader.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }

                String res = buffer.toString();
                return res;
            }
        } finally {
            instream.close();//instream.reset();
        }
    }

    //private boolean isImage(String contentType){}

}
