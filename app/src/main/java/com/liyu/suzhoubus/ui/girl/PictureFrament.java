package com.liyu.suzhoubus.ui.girl;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.ui.base.BaseFragment;


/**
 * Created by liyu on 2016/12/22.
 */

public class PictureFrament extends BaseFragment {

    private ImageView mImageView;

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initViews() {
        url = getArguments().getString("url");
        mImageView = findView(R.id.picture);
    }

    @Override
    protected void lazyFetchData() {
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).priority(Priority.IMMEDIATE).crossFade(0)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mImageView);
    }
}
