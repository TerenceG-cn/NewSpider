package com.tce.newspider.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpGetRequest extends AbstractHttpRequest {

    public HttpGetRequest() {
        super();
    }

    public HttpGetRequest(String url) {
        super(url);
    }

    public static HttpGetRequest fromJson(JSONObject request) {
        return (HttpGetRequest) JSON.toJavaObject(request, HttpGetRequest.class);
    }
}
