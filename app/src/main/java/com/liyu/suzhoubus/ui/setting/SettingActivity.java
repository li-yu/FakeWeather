package com.liyu.suzhoubus.ui.setting;

import android.os.Bundle;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.ui.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void loadData() {
        getFragmentManager().beginTransaction().replace(R.id.contentLayout, new SettingFragment()).commit();
    }
}
