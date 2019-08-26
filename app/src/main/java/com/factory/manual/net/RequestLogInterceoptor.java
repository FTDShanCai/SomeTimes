package com.factory.manual.net;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestLogInterceoptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.e("url: " + request.method() + "  " + request.url().toString());
        LogUtil.e("json: " + request.url().queryParameter("json"));
        return chain.proceed(request);
    }
}
