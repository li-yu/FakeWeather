package com.liyu.suzhoubus;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by liyu on 2016/11/2.
 */

public class App extends Application {

    private static Context mContext;

    private static String cacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePalApplication.initialize(this);

        if (getApplicationContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    private boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAppCacheDir() {
        return cacheDir;
    }
}
