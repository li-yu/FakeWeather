package com.liyu.fakeweather.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.liyu.fakeweather.BuildConfig;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.utils.FileUtil;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.UpdateUtil;
import com.liyu.fakeweather.utils.WebUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.liyu.fakeweather.utils.FileUtil.getFileDir;

/**
 * Created by liyu on 2016/11/28.
 */

public class AboutActivity extends BaseActivity {

    private TextView tvVersion;
    private ImageSwitcher imageSwitcher;
    private static final String[] imageUrls = {
            "http://cdn.liyuyu.cn/fakeweathers1.png",
            "http://cdn.liyuyu.cn/fakeweathers2.png",
            "http://cdn.liyuyu.cn/fakeweathers3.png",
            "http://cdn.liyuyu.cn/fakeweathers4.png",
            "http://cdn.liyuyu.cn/fakeweathers5.png"};

    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        tvVersion = findViewById(R.id.tv_app_version);
        tvVersion.setText("v" + BuildConfig.VERSION_NAME);
        imageSwitcher = findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(AboutActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.zoom_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.zoom_out));
    }

    private void loadImage() {
        Glide.with(this).load(imageUrls[new Random().nextInt(5)]).into(new SimpleTarget<GlideDrawable>(imageSwitcher.getWidth(), imageSwitcher.getHeight()) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageSwitcher.setImageDrawable(resource);
            }
        });
    }

    @Override
    protected void loadData() {
        imageSwitcher.post(new Runnable() {
            @Override
            public void run() {
                loadImage();
            }
        });
        subscription = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        loadImage();
                    }
                });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_web_home:
                WebUtils.openInternal(this, "https://github.com/li-yu/FakeWeather");
                break;
            case R.id.btn_feedback:
                feedBack();
                break;
            case R.id.btn_check_update:
                UpdateUtil.check(AboutActivity.this, false);
                break;
            case R.id.btn_share_app:
                ShareUtils.shareText(this, "来不及了，赶紧上车！https://github.com/li-yu/FakeWeather", "分享到");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    private void feedBack() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "me@liyuyu.cn", null));
        intent.putExtra(Intent.EXTRA_EMAIL, "me@liyuyu.cn");
        intent.putExtra(Intent.EXTRA_SUBJECT, "反馈");
        intent.putExtra(Intent.EXTRA_TEXT, FileUtil.readFile(getFileDir("Log/crash.log")));
        startActivity(Intent.createChooser(intent, "反馈"));
    }

}
