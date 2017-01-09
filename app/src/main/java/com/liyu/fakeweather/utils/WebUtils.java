package com.liyu.fakeweather.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.liyu.fakeweather.R;
import com.thefinestartist.finestwebview.FinestWebView;

/**
 * Created by liyu on 2016/7/18.
 */
public class WebUtils {


    /**
     * App 内部打开一个网页
     *
     * @param context
     * @param url
     */
    public static void openInternal(Context context, String url) {
        new FinestWebView.Builder(context)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResRefresh(R.string.menu_action_refresh)
                .stringResShareVia(R.string.menu_action_share)
                .stringResCopyLink(R.string.menu_action_copy)
                .stringResOpenWith(R.string.menu_action_openwith)
                .titleColor(context.getResources().getColor(R.color.white))
                .toolbarColor(context.getResources().getColor(ThemeUtil.getCurrentColorPrimary(context)))
                .statusBarColor(context.getResources().getColor(ThemeUtil.getCurrentColorPrimary(context)))
                .swipeRefreshColor(context.getResources().getColor(ThemeUtil.getCurrentColorPrimary(context)))
                .showUrl(false)
                .iconDefaultColor(context.getResources().getColor(R.color.Color_White))
                .show(url);
    }

    /**
     * 跳转到外部浏览器打开 url
     *
     * @param context
     * @param url
     */
    public static void openExternal(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
