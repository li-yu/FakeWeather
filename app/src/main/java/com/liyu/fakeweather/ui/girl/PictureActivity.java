package com.liyu.fakeweather.ui.girl;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.utils.RxImage;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.ToastUtil;
import com.liyu.fakeweather.widgets.DragLayout;

import java.io.IOException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/11/3.
 */

public class PictureActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";

    private DragLayout dragLayout;
    private ImageView mImageView;
    private String mImageUrl;
    private String mImageTitle;

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_pic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showSystemUI();
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        dragLayout = findView(R.id.drag_layout);
        dragLayout.bind(this).setDragListener(new DragLayout.DragListener() {
            @Override
            public void onDragFinished() {
                PictureActivity.this.onBackPressed();
            }

        });
        mImageView = findViewById(R.id.picture);
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOrShowToolbar();
            }
        });
    }

    @Override
    protected void loadData() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        Glide.with(this).load(mImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.IMMEDIATE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mImageView);
        hideOrShowToolbar();
    }

    private void hideOrShowToolbar() {
        if (getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
            hideSystemUI();
        } else {
            showSystemUI();
            getSupportActionBar().show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.menu_share) {
            RxImage.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showLong("图片分享失败: " + e.toString());
                        }

                        @Override
                        public void onNext(Uri uri) {
                            ShareUtils.shareImage(PictureActivity.this, uri, "分享福利...");
                        }
                    });
        } else if (id == R.id.menu_save) {
            RxImage.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showLong("图片成功失败: " + e.toString());
                        }

                        @Override
                        public void onNext(Uri uri) {
                            ToastUtil.showLong("图片成功保存至: " + RxImage.getRealFilePath(PictureActivity.this, uri));
                        }
                    });

        } else if (id == R.id.menu_wallpaper) {
            final WallpaperManager wm = WallpaperManager.getInstance(this);
            RxImage.saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showLong("壁纸设置失败: " + e.toString());
                        }

                        @Override
                        public void onNext(Uri uri) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                startActivity(wm.getCropAndSetWallpaperIntent(uri));
                            } else {
                                try {
                                    wm.setStream(PictureActivity.this.getContentResolver().openInputStream(uri));
                                    ToastUtil.showShort("设置壁纸成功!");
                                } catch (IOException e) {
                                    ToastUtil.showShort("设置壁纸失败!" + e.toString());
                                }
                            }
                        }
                    });
        }
        return true;
    }

}
