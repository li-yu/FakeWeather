package com.liyu.suzhoubus.ui.gank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.ui.base.BaseActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liyu on 2016/11/3.
 */

public class PictureActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";

    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    String mImageUrl, mImageTitle;

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
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        mImageView = (ImageView) findViewById(R.id.picture);
//        mAttacher = new PhotoViewAttacher(mImageView);
    }

    @Override
    protected void loadData() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        Glide.with(this).load(mImageUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade(0)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mImageView);
    }
}
