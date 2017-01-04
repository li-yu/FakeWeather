package com.liyu.fakeweather.ui.girl;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.Girl;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.girl.adapter.PicFragmentAdapter;
import com.liyu.fakeweather.utils.RxImage;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.ToastUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2016/11/3.
 */

public class PictureActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String EXTRA_IMAGE_LIST = "image_list";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String TRANSIT_PIC = "picture";

    private ViewPager viewPager;
    private PicFragmentAdapter adapter;
    private List<Girl> girls;
    private int currentIndex;

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    public static Intent newIntent(Context context, List<Girl> girls, int index) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_LIST, (Serializable) girls);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_INDEX, index);
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
        adapter = new PicFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.picturePager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int flag = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flag = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flag = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flag == 0) {
                            hideOrShowToolbar();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        currentIndex = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        girls = (List<Girl>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);
        if (girls == null || girls.isEmpty())
            return;
        hideOrShowToolbar();

        for (Girl girl : girls) {
            Fragment fragment = new PictureFrament();
            Bundle data = new Bundle();
            data.putString("url", girl.getUrl());
            fragment.setArguments(data);
            adapter.addFragment(fragment);
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
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
            RxImage.saveImageAndGetPathObservable(this, girls.get(viewPager.getCurrentItem()).getUrl(), TimeUtils.getSystemTime())
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
            RxImage.saveImageAndGetPathObservable(this, girls.get(viewPager.getCurrentItem()).getUrl(), TimeUtils.getSystemTime())
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
            RxImage.saveImageAndGetPathObservable(this, girls.get(viewPager.getCurrentItem()).getUrl(), TimeUtils.getSystemTime())
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
