package com.tce.newspider.downloader;


import com.tce.newspider.annotation.Downloader;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;


import java.io.*;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Downloader(name = "httpClientDownloader")
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
     *
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
        CloseableHttpResponse response = null;

        request.setHeader("Accept-Encoding", "identity");//不使用Gzip压缩，返回真实content-length
        try {
            response = this.httpClient.execute(request, this.cookieContext);
            //获取响应实体
            HttpEntity httpEntity = response.getEntity();
            log.info(httpEntity.getContent());
            if (httpEntity != null) {
                log.info("长度：\t" + httpEntity.getContentLength());
                System.out.println("内容：\t" + EntityUtils.toString(httpEntity, "UTF-8"));
            }
        } catch (IOException e) {
            log.error(e.toString());
        }

        return response;
    }

    /**
     * 下载 发起请求获取响应
     * @param request HttpRequest
     * @param timeout  .setSocketTimeout(timeout) .setConnectTimeout(timeout)
     * @return HttpResponse
     * @throws DownloadException
     */
    @Override
    public HttpResponse download(HttpRequest request, int timeout) throws DownloadException{
        if (log.isDebugEnabled()) {
            log.debug("downloading...from " + request.getUrl());
        }

        //把自己封装的HttpRequest->HttpRequestBase
        HttpRequestBase httprequest = new HttpGet(request.getUrl());
        //httprequest.addHeader("Connection","Keep-Alive");
        //log.info(httprequest.getAllHeaders()[1]);
        //设置header
        if (request.getHeaders() != null)
            for (Map.Entry entry : request.getHeaders().entrySet()) {
                httprequest.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        //随机选取用户代理
        httprequest.setHeader("User-Agent", UserAgentUtil.getUserAgent());
        log.info(httprequest.getAllHeaders().length);

        //请求设置
        //https://blog.csdn.net/u010886217/article/details/106869268/
        RequestConfig.Builder rcBuilder = RequestConfig.custom()
                                        // 从链接池获取连接超时时间
                                        .setConnectionRequestTimeout(1000)
                                        //获取数据超时 ms
                                        .setSocketTimeout(timeout)
                                        // 请求连接超时时间 ms
                                        .setConnectTimeout(timeout)
                                        //禁止重定向
                                        .setRedirectsEnabled(false);
        //todo 代理
        //HttpHost proxy = null;
        //rcBuilder.setProxy(proxy);

        //todo 设置Cookies


        httprequest.setConfig(rcBuilder.build());

        HttpResponse resp =new HttpResponse();
        try{
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httprequest, cookieContext);

            //状态码
            int status = httpResponse.getStatusLine().getStatusCode();
            resp.setStatus(status);
            log.info("返回状态："+status);

            if(status==200){
                //响应实体 字节流
                HttpEntity responseEntity = httpResponse.getEntity();
                ByteArrayInputStream byteContent = toByteInputStream(responseEntity.getContent());
                resp.setByteContent(byteContent);
                log.info("字节流："+byteContent);
                //content-type
                String contentType = null;
                Header contentTypeHeader = responseEntity.getContentType();
                if(contentTypeHeader != null) {
                    contentType = contentTypeHeader.getValue();
                }
                resp.setContentType(contentType);
                //编码
                String charset = request.isForceUseCharset() ? request.getCharset():getCharset(request.getCharset(), contentType);
                resp.setCharset(charset);
                //响应内容 String
                //String content = EntityUtils.toString(responseEntity, charset);//entity所得到的流是不可重复读取,会抛出异常Stream Closed
                String content = getContentString(byteContent, responseEntity.getContentLength(), charset);
                resp.setContent(content);
                byteContent.close();
            }
        }catch (IOException e){
            log.error(e);
        }
        return resp;
    }

    public void shutdown() {
        try {
            if (httpClient != null)
                httpClient.close();
        } catch (IOException e) {
            log.error(e.toString());
            this.httpClient = null;
        }

    }

    /**
     * 获取响应内容
     * EntityUtils.toString(responseEntity, charset) 同作用
     * @param inStream Content 流
     * @param contentLength 响应内容长度
     * @param charset       字符编码
     * @return
     * @throws IOException
     */
    public String getContentString(InputStream inStream, long contentLength, String charset) throws IOException {
        if (inStream == null) {
            return null;
        } else {
            try {
                Args.check(contentLength <= 2147483647L, "HTTP entity too large to be buffered in memory");

                int capacity = (int)contentLength;
                if (capacity < 0) {
                    capacity = 4096;
                }
                log.info("capacity"+capacity);

                if (charset == null) {
                    charset = "UTF-8";
                }
                Reader reader = new InputStreamReader(inStream, charset);
                CharArrayBuffer buffer = new CharArrayBuffer(capacity);
                char[] tmp = new char[1024];

                int l;
                while((l = reader.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                log.info(buffer.toString());
                return buffer.toString();
            } finally {
                if(inStream instanceof ByteArrayInputStream)
                    Objects.requireNonNull(inStream).reset();//ByteArrayInputStream可以调用reset实现流的复用
            }
        }
    }

    //todo private boolean isImage(String contentType){}

}
