package com.liyu.suzhoubus.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by liyu on 2016/11/17.
 */

public class ShareUtils {

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
}
