package com.liyu.suzhoubus.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.event.JiandanEvent;
import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.model.JiandanXXOO;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/11/9.
 */

public class XXOOService extends IntentService {

    private static final String KEY_EXTRA_GIRL_LIST = "data";

    public XXOOService() {
        super("GirlService");
    }

    public static void start(Context context, List<JiandanXXOO> list) {
        Intent intent = new Intent(context, XXOOService.class);
        intent.putExtra(KEY_EXTRA_GIRL_LIST, (Serializable) list);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<JiandanXXOO> xxoos = (List<JiandanXXOO>) intent.getSerializableExtra(KEY_EXTRA_GIRL_LIST);
        try {
            for (final JiandanXXOO xxoo : xxoos) {
                Bitmap bitmap = Glide.with(XXOOService.this)
                        .load(xxoo.getPics().get(0))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                xxoo.setGirlHeight(bitmap.getHeight());
                xxoo.setGirlWidth(bitmap.getWidth());
            }
        } catch (Exception e) {
            Log.d("liyuyu", e.toString());
        }
        EventBus.getDefault().post(new JiandanEvent(xxoos));
    }
}
