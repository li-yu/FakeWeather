package com.liyu.fakeweather.ui.setting;

import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.Module;
import com.liyu.fakeweather.utils.ThemeUtil;

import java.util.List;

/**
 * Created by liyu on 2017/10/18.
 */

public class ModuleAdapter extends BaseItemDraggableAdapter<Module, BaseViewHolder> {

    public ModuleAdapter(int layoutResId, List<Module> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Module item) {
        TextView tvName = helper.getView(R.id.tv_module_name);
        tvName.setText(item.getName());
        tvName.setCompoundDrawablesWithIntrinsicBounds(ThemeUtil.setTintDrawable(mContext.getDrawable(item.getResIcon()), mContext), null, null, null);
        SwitchCompat switchCompat = helper.getView(R.id.switch_module_enable);
        switchCompat.setChecked(item.isEnable());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setEnable(isChecked);
            }
        });

    }

}
