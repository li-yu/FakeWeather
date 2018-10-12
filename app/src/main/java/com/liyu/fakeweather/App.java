package com.liyu.fakeweather;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

/**
 * Created by liyu on 2016/11/2.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(this);
        if (!BuildConfig.DEBUG) {
            AppExceptionHandler.getInstance().setCrashHanler(this);
        }
        Bugly.init(getApplicationContext(), "914abea303", false);
    }

    public static Context getContext() {
        return mContext;
    }

}
