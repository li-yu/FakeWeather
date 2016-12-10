package com.liyu.suzhoubus.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.event.GirlsComingEvent;
import com.liyu.suzhoubus.model.Girl;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/11/9.
 */

public class GirlService extends IntentService {

    private static final String KEY_EXTRA_GIRL_FROM = "from";
    private static final String KEY_EXTRA_GIRL_LIST = "data";

    public GirlService() {
        super("GirlService");
    }

    public static void start(Context context, int from, List<Girl> list) {
        Intent intent = new Intent(context, GirlService.class);
        intent.putExtra(KEY_EXTRA_GIRL_FROM, from);
        intent.putExtra(KEY_EXTRA_GIRL_LIST, (Serializable) list);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int from = intent.getIntExtra(KEY_EXTRA_GIRL_FROM, GirlsComingEvent.GIRLS_FROM_GANK);
        List<Girl> girls = (List<Girl>) intent.getSerializableExtra(KEY_EXTRA_GIRL_LIST);
        try {
            for (final Girl girl : girls) {
                Bitmap bitmap = Glide.with(GirlService.this)
                        .load(girl.getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                girl.setHeight(bitmap.getHeight());
                girl.setWidth(bitmap.getWidth());
            }
        } catch (Exception e) {

        }
        EventBus.getDefault().post(new GirlsComingEvent(from, girls));
    }
}
