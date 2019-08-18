package com.factory.manual.net;

import android.support.annotation.NonNull;

import com.factory.manual.App;
import com.factory.manual.api.Api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static RetrofitUtil util;

    public static final String url = "http://192.168.1.3:8080/";

    public static RetrofitUtil getInstance() {
        if (util == null) {
            synchronized (RetrofitUtil.class) {
                if (util == null) {
                    util = new RetrofitUtil();
                }
            }
        }
        return util;
    }

    private OkHttpClient getClient() {
        Cache cache = new Cache(App.getInstance().getCacheDir(), 1024 * 1024);
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .addInterceptor(new RequestLogInterceoptor())
                .addInterceptor(new ResponseLogInterceptor())
//                                .addInterceptor(new RequestLogInterceoptor())
                .build();
    }

    public Api getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String path) {
        File file = new File(path);
        // 为file建立RequestBody实例
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), file);
        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
