package com.liyu.fakeweather.ui.weather.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.SimpleItem;

import java.util.List;

/**
 * Created by liyu on 2017/11/9.
 */

public class SimpleListAdapter extends BaseQuickAdapter<SimpleItem, BaseViewHolder> {

    private int lastSelectedIndex = -1;

    public SimpleListAdapter(int layoutResId, List<SimpleItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleItem item) {
        helper.setText(R.id.tv_simple, item.getItem());
        if (helper.getAdapterPosition() == lastSelectedIndex) {
            helper.itemView.setSelected(true);
        } else {
            helper.itemView.setSelected(false);
        }
    }

    public void setSelectedItem(int position) {
        if (lastSelectedIndex != -1 && position != -1) {
            getData().get(lastSelectedIndex).setSelected(false);
            notifyItemChanged(lastSelectedIndex);
            getData().get(position).setSelected(true);
        }
        lastSelectedIndex = position;
        notifyItemChanged(position);
    }

}
