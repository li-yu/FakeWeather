package com.liyu.suzhoubus.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.utils.RxDataBase;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/11/9.
 */

public class GirlService extends IntentService {

    private static final String KEY_EXTRA_GIRL_LIST = "data";

    public GirlService() {
        super("GirlService");
    }

    public static void start(Context context, List<Gank> list) {
        Intent intent = new Intent(context, GirlService.class);
        intent.putExtra(KEY_EXTRA_GIRL_LIST, (Serializable) list);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<Gank> ganks = (List<Gank>) intent.getSerializableExtra(KEY_EXTRA_GIRL_LIST);
        try {
            for (final Gank gank : ganks) {
                Bitmap bitmap = Glide.with(GirlService.this)
                        .load(gank.getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                gank.setGirlHeight(bitmap.getHeight());
                gank.setGirlWidth(bitmap.getWidth());
            }
        } catch (Exception e) {

        }
        EventBus.getDefault().post(ganks);
    }
}
