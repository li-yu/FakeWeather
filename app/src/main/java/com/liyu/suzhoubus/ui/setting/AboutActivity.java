package com.liyu.suzhoubus.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liyu.suzhoubus.BuildConfig;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseAppResponse;
import com.liyu.suzhoubus.model.UpdateInfo;
import com.liyu.suzhoubus.ui.base.BaseActivity;
import com.liyu.suzhoubus.utils.DeviceUtil;
import com.liyu.suzhoubus.utils.FileUtil;
import com.liyu.suzhoubus.utils.ShareUtils;
import com.liyu.suzhoubus.utils.SimpleSubscriber;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.liyu.suzhoubus.utils.FileUtil.getFileDir;

/**
 * Created by liyu on 2016/11/28.
 */

public class AboutActivity extends BaseActivity {

    private TextView tvVersion;
    private ImageSwitcher imageSwitcher;
    private String[] imageUrls = {
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu01.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu02.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu03.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu04.png",
            "http://7xp1a1.com1.z0.glb.clouddn.com/liyu05.png"};

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
        tvVersion = (TextView) findViewById(R.id.tv_app_version);
        tvVersion.setText("v" + BuildConfig.VERSION_NAME);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
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
//                openWeb("http://liyuyu.cn");
                break;
            case R.id.btn_feedback:
                feedBack();
                break;
            case R.id.btn_check_update:
                checkUpdate();
                break;
            case R.id.btn_share_app:
                ShareUtils.shareText(this, "来不及了，赶紧上车！");
                break;
        }
    }

    private void openWeb(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
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

    private void checkUpdate() {
        ApiFactory.getAppController().checkUpdate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseAppResponse<UpdateInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(AboutActivity.this.getWindow().getDecorView().getRootView(), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(final BaseAppResponse<UpdateInfo> response) {
                if (response != null && response.results != null) {
                    if (response.results.getVersionCode() <= BuildConfig.VERSION_CODE) {
                        Snackbar.make(findViewById(R.id.coordinator_about), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("发现新版本")
                            .setMessage(String.format("版本号: %s\n\n更新时间: %s\n\n更新内容: %s",
                                    response.results.getVersionName(),
                                    response.results.getUpdateTime(),
                                    response.results.getInformation()));
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openWeb(response.results.getUrl());
                        }
                    });
                    builder.show();

                }
            }
        });
    }
}
