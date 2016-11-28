package com.liyu.suzhoubus.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.liyu.suzhoubus.BuildConfig;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.ui.base.BaseActivity;

/**
 * Created by liyu on 2016/11/28.
 */

public class AboutActivity extends BaseActivity {

    private TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        tvVersion = (TextView) findViewById(R.id.tv_app_version);
        tvVersion.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void loadData() {

    }
}
