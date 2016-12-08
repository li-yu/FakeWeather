package com.liyu.suzhoubus.ui.gank;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.model.JiandanXXOO;
import com.liyu.suzhoubus.widgets.RatioImageView;

import java.util.List;

/**
 * Created by liyu on 2016/11/2.
 */

public class JiandanAdapter extends BaseQuickAdapter<JiandanXXOO, BaseViewHolder> {

    public JiandanAdapter(int layoutResId, List<JiandanXXOO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final JiandanXXOO xxoo) {
        final RatioImageView iv = baseViewHolder.getView(R.id.iv_gank);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPictureActivity(xxoo, view);
            }
        });
        iv.setOriginalSize(xxoo.getGirlWidth(), xxoo.getGirlHeight());
        Glide.with(mContext).load(xxoo.getPics().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_glide_holder).crossFade(500).into(iv);
    }

    private void startPictureActivity(JiandanXXOO xxoo, View transitView) {
        Intent intent = PictureActivity.newIntent(mContext, xxoo.getPics().get(0), "");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) mContext, transitView, PictureActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(mContext, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            mContext.startActivity(intent);
        }
    }

    @Override
    public void addData(int position, List<JiandanXXOO> data) {
        if (0 <= position && position <= this.mData.size()) {
            this.mData.addAll(position, data);
            this.hideLoadingMore();
            this.notifyItemInserted(position);
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }
}
