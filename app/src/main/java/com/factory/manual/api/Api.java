package com.factory.manual.api;

import com.factory.manual.bean.BaseResultBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("factorybook/api/service")
    Observable<BaseResultBean> getData(@Query("json") String json);

}
