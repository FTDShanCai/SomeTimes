package com.factory.manual.net;

import android.net.ParseException;

import com.factory.manual.BuildConfig;
import com.factory.manual.Contants;
import com.factory.manual.bean.BaseResultBean;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class NetObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseResultBean) {
            BaseResultBean bean = (BaseResultBean) t;
            if (Contants.NET_SUCCESS.equals(bean.getResult())) {
                onSuccess(t);
            } else {
                onError(bean.getResultNote(), new Exception(bean.getResultNote()));
            }
        } else {
            onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onError("网络错误", e);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onError("连接失败", e);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onError("请求超时", e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onError("数据解析错误", e);
        } else {
            onError("请求连接失败", e);
        }
    }

    @Override
    public void onComplete() {

    }


    private void onError(String msg, Throwable e) {
        if (BuildConfig.DEBUG) {
            onFail(e.toString());
        } else {
            onFail(msg);
        }
    }

    abstract public void onSuccess(T response);

    abstract public void onFail(String msg);


}
