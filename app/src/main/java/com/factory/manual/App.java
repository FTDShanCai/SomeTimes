package com.factory.manual;

import android.app.Application;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class App extends Application {

    private  static App instance=null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
    }

    public static App getInstance() {
        return instance;
    }
}
