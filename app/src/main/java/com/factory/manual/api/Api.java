package com.factory.manual.api;

import com.factory.manual.bean.BaseResultBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Api {

    @GET("factorybook/api/service")
    Observable<BaseResultBean> getData(@Query("json") String json);


    @Multipart
    @POST("factorybook/api/addimg")
    Observable<BaseResultBean> addimg(@PartMap Map<String, String> map, @Part MultipartBody.Part file);
}
