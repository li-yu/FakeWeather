package com.liyu.suzhoubus;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by liyu on 2016/11/2.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePalApplication.initialize(this);

    }

    public static Context getContext() {
        return mContext;
    }

}
