package com.tce.newspider.http;

import com.google.common.io.CharStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HttpResponse {
    private ByteArrayInputStream byteContent;//响应体 字节流

    private String content;

    private String contentType;

    private String charset;

    private int status;

    public static HttpResponse createSimple(String content) {
        HttpResponse response = new HttpResponse();
        response.setContent(content);
        return response;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        if(this.charset==null)
            return content;
        try{//设置了编码，就把字节流转为字符流，转为字符串
            return CharStreams.toString(new InputStreamReader(byteContent,charset));
        }catch (IOException e2){
            e2.printStackTrace();
        }
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public String getCharset() {
        return charset;
    }

    public int getStatus() {
        return status;
    }

    public ByteArrayInputStream getByteContent() {
        return byteContent;
    }

    public void setByteContent(ByteArrayInputStream byteContent) {
        this.byteContent = byteContent;
    }

    /**
     * 关闭字节流
     */
    public boolean close(){
        if(byteContent!=null) {
            try{
                byteContent.close();
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
