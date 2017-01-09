package com.liyu.fakeweather.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import com.liyu.fakeweather.BuildConfig;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.model.UpdateInfo;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/12/29.
 */

public class UpdateUtil {

    /**
     * 检查更新
     *
     * @param activity
     * @param isSilence 是否弹出Snackbar提示
     */
    public static void check(final Activity activity, final boolean isSilence) {
        ApiFactory.getAppController().checkUpdate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseAppResponse<UpdateInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isSilence)
                    Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(final BaseAppResponse<UpdateInfo> response) {
                if (response != null && response.results != null) {
                    if (response.results.getVersionCode() <= BuildConfig.VERSION_CODE) {
                        if (!isSilence)
                            Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setCancelable(false);
                    builder.setTitle("发现新版本")
                            .setMessage(String.format("版本号: %s\n\n更新时间: %s\n\n更新内容: %s",
                                    response.results.getVersionName(),
                                    response.results.getUpdateTime(),
                                    response.results.getInformation()));
                    if (!response.results.isForeUpdate()) {
                        builder.setNegativeButton("取消", null);
                    }
                    builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            WebUtils.openExternal(activity, response.results.getUrl());
                        }
                    });
                    builder.show();

                }
            }
        });
    }
}
