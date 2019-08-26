package com.factory.manual.net;


import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (response.body() != null && response.body().contentType() != null) {
            String json = response.body().string();
            if (!TextUtils.isEmpty(json)) {
                LogUtil.e(json);
            } else {
                LogUtil.e("json is null");
            }
            MediaType mediaType = response.body().contentType();
            ResponseBody responseBody = ResponseBody.create(mediaType, json);

            return response.newBuilder().body(responseBody).build();
        } else {
            return response;
        }

    }
}