package com.liyu.fakeweather.ui.weather.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.HeWeather5.SuggestionBean;
import com.liyu.fakeweather.widgets.CircleImageView;

import java.util.List;

/**
 * Created by liyu on 2017/4/1.
 */

public class SuggestionAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public SuggestionAdapter(int layoutResId, List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {
        CircleImageView circleImageView = holder.getView(R.id.civ_suggesstion);
        if (item instanceof SuggestionBean.AirBean) {
            holder.setText(R.id.tvName, "空气");
            holder.setText(R.id.tvMsg, ((SuggestionBean.AirBean) item).getBrf());
            circleImageView.setFillColor(0xFF7F9EE9);
            circleImageView.setImageResource(R.drawable.ic_air);
        } else if (item instanceof SuggestionBean.ComfBean) {
            holder.setText(R.id.tvName, "舒适度");
            holder.setText(R.id.tvMsg, ((SuggestionBean.ComfBean) item).getBrf());
            circleImageView.setFillColor(0xFFE99E3C);
            circleImageView.setImageResource(R.drawable.ic_comf);
        } else if (item instanceof SuggestionBean.CwBean) {
            holder.setText(R.id.tvName, "洗车");
            holder.setText(R.id.tvMsg, ((SuggestionBean.CwBean) item).getBrf());
            circleImageView.setFillColor(0xFF62B1FF);
            circleImageView.setImageResource(R.drawable.ic_cw);
        } else if (item instanceof SuggestionBean.DrsgBean) {
            holder.setText(R.id.tvName, "穿衣");
            holder.setText(R.id.tvMsg, ((SuggestionBean.DrsgBean) item).getBrf());
            circleImageView.setFillColor(0xFF8FC55F);
            circleImageView.setImageResource(R.drawable.ic_drsg);
        } else if (item instanceof SuggestionBean.FluBean) {
            holder.setText(R.id.tvName, "感冒");
            holder.setText(R.id.tvMsg, ((SuggestionBean.FluBean) item).getBrf());
            circleImageView.setFillColor(0xFFF98178);
            circleImageView.setImageResource(R.drawable.ic_flu);
        } else if (item instanceof SuggestionBean.SportBean) {
            holder.setText(R.id.tvName, "运动");
            holder.setText(R.id.tvMsg, ((SuggestionBean.SportBean) item).getBrf());
            circleImageView.setFillColor(0xFFB3CA60);
            circleImageView.setImageResource(R.drawable.ic_sport);
        } else if (item instanceof SuggestionBean.TravBean) {
            holder.setText(R.id.tvName, "旅游");
            holder.setText(R.id.tvMsg, ((SuggestionBean.TravBean) item).getBrf());
            circleImageView.setFillColor(0xFFFD6C35);
            circleImageView.setImageResource(R.drawable.ic_trav);
        } else if (item instanceof SuggestionBean.UvBean) {
            holder.setText(R.id.tvName, "紫外线");
            holder.setText(R.id.tvMsg, ((SuggestionBean.UvBean) item).getBrf());
            circleImageView.setFillColor(0xFFF0AB2A);
            circleImageView.setImageResource(R.drawable.ic_uv);
        }
    }
}
