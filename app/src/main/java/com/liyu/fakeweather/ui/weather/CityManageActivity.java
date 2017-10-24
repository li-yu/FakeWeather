package com.liyu.fakeweather.ui.weather;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.CardWeather;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.weather.adapter.CardWeatherAdapter;
import com.liyu.fakeweather.utils.ThemeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/10/18.
 */

public class CityManageActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;
    private CardWeatherAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        adapter = new CardWeatherAdapter(R.layout.item_card_weather, null);
        adapter.openLoadAnimation();
        recyclerView = findView(R.id.rv_city_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        refreshLayout = findView(R.id.refeshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        refreshLayout.setColorSchemeResources(ThemeUtil.getCurrentColorPrimary(this));
    }

    @Override
    protected void loadData() {

        List<CardWeather> cardWeathers = new ArrayList<>();

        cardWeathers.add(new CardWeather("苏州", "100", "晴", "9/18℃"));

        cardWeathers.add(new CardWeather("北京", "104", "阴", "3/14℃"));

        cardWeathers.add(new CardWeather("上海", "307", "大雨", "4/15℃"));

        adapter.setNewData(cardWeathers);

    }
}
