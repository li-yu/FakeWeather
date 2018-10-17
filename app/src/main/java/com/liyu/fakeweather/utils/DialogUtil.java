package com.liyu.fakeweather.utils;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.liyu.fakeweather.widgets.CommProgressDialog;

/**
 * 对话框工具集
 * 用于快速显示和关闭对话框
 * <p>
 * Created by liyu on 2018/6/12.
 */

public class DialogUtil {

    public static Handler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    private static Handler mainHandler;

    /**
     * 显示进度条对话框
     *
     * @param activity 宿主 Activity 必须继承 AppCompatActivity
     * @param message  需要显示的文字信息
     */
    public static void showProgressDialog(final AppCompatActivity activity, final String message) {
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                CommProgressDialog commProgressDialog = (CommProgressDialog) activity.getSupportFragmentManager().findFragmentByTag("ProgressDialog");
                if (commProgressDialog == null) {
                    commProgressDialog = CommProgressDialog.newInstance(message);
                }
                if (commProgressDialog.isVisible())
                    return;
                commProgressDialog.show(activity.getSupportFragmentManager(), "ProgressDialog");
                activity.getSupportFragmentManager().executePendingTransactions();
            }
        });

    }

    /**
     * 关闭进度条对话框
     *
     * @param activity 宿主 Activity 必须继承 AppCompatActivity
     */
    public static void dismissProgressDialog(AppCompatActivity activity) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag("ProgressDialog");
        if (fragment != null) {
            CommProgressDialog dialog = (CommProgressDialog) fragment;
            dialog.dismissAllowingStateLoss();
        }
    }

}
