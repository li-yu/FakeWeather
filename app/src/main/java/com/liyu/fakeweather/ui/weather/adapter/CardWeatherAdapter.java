package com.liyu.fakeweather.ui.weather.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.WeatherBean;
import com.liyu.fakeweather.model.WeatherCity;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.ThemeUtil;
import com.liyu.fakeweather.utils.WeatherUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyu on 2017/10/18.
 */

public class CardWeatherAdapter extends BaseItemDraggableAdapter<WeatherCity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public CardWeatherAdapter(int layoutResId, List<WeatherCity> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, WeatherCity item) {
        TextView tvName = helper.getView(R.id.tv_card_city_name);
        if (helper.getAdapterPosition() == 0) {
            tvName.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ThemeUtil.setTintDrawable(R.drawable.ic_location, mContext,
                            ThemeUtil.getCurrentColorPrimary(mContext)), null);
        }
        tvName.setText(item.getCityName());
        helper.setText(R.id.tv_card_weather, item.getWeatherText());
        helper.setText(R.id.tv_card_temp, item.getWeatherTemp() + "℃");
        final ImageView imageView = helper.getView(R.id.iv_card_weather);
        WeatherUtil.getInstance().getWeatherDict(item.getWeatherCode()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleSubscriber<WeatherBean>() {
            @Override
            public void onNext(WeatherBean weatherBean) {
                Glide.with(mContext).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        });
    }
}
