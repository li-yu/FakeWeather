package com.liyu.fakeweather.ui.bus.adapter;

import android.content.ContentValues;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.BusLineDetail;
import com.liyu.fakeweather.model.FavoritesBusBean;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.bus.LineDetailActivity;
import com.liyu.fakeweather.utils.RxDataBase;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by liyu on 2016/11/29.
 */

public class FavoritesAdapter extends BaseQuickAdapter<FavoritesBusBean, BaseViewHolder> {

    private int deletedPosition = 0;
    private FavoritesBusBean deletedItem;

    public FavoritesAdapter(int layoutResId, List<FavoritesBusBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final FavoritesBusBean item) {
        holder.setText(R.id.tv_fav_name, item.getLName());
        holder.setText(R.id.tv_fav_info, item.getLDirection());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineDetailActivity.start(mContext, item.getLGUID(), item.getLName(), item.getLDirection());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deletedPosition = holder.getAdapterPosition();
                deletedItem = item;
                remove(deletedPosition);
                setFavorite(deletedItem, false);
                Snackbar.make(((MainActivity) mContext).getWindow().getDecorView().getRootView().findViewById(R.id.contentLayout), "删除成功!",
                        Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add(deletedPosition, deletedItem);
                        setFavorite(deletedItem, true);
                    }
                }).setActionTextColor(mContext.getResources().getColor(R.color.actionColor)).show();
                return true;
            }
        });
    }

    private void setFavorite(FavoritesBusBean item, final boolean isFav) {
        RxDataBase.getFirst(BusLineDetail.class, "LGUID = ?", item.getLGUID()).map(new Func1<BusLineDetail, Integer>() {
            @Override
            public Integer call(BusLineDetail busLineDetail) {
                ContentValues values = new ContentValues();
                values.put("isFavorite", isFav);
                return DataSupport.updateAll(BusLineDetail.class, values, "LGUID = ?", busLineDetail.getLGUID());
            }
        }).subscribe();
    }

}
