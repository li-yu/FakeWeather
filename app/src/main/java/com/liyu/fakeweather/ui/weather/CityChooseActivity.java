package com.liyu.fakeweather.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.HeWeatherCity;
import com.liyu.fakeweather.model.SimpleItem;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.weather.adapter.SimpleListAdapter;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by liyu on 2017/10/18.
 */

public class CityChooseActivity extends BaseActivity {

    private RecyclerView rvProvince;

    private RecyclerView rvLeader;

    private RecyclerView rvCity;

    private SimpleListAdapter provinceAdapter;

    private SimpleListAdapter leaderAdapter;

    private SimpleListAdapter cityAdapter;

    public static final String EXTRA_CITY_NAME = "extra_city_name";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_choose;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        rvProvince = findView(R.id.rv_province);
        rvProvince.setLayoutManager(new LinearLayoutManager(this));
        rvProvince.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        provinceAdapter = new SimpleListAdapter(R.layout.item_simple_list, null);
        provinceAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvProvince.setAdapter(provinceAdapter);
        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cityAdapter.setNewData(null);
                cityAdapter.setSelectedItem(-1);
                leaderAdapter.setNewData(null);
                leaderAdapter.setSelectedItem(-1);
                provinceAdapter.setSelectedItem(position);
                queryLeader(provinceAdapter.getItem(position).getItem());
            }
        });

        rvLeader = findView(R.id.rv_leader);
        rvLeader.setLayoutManager(new LinearLayoutManager(this));
        rvLeader.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        leaderAdapter = new SimpleListAdapter(R.layout.item_simple_list, null);
        leaderAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvLeader.setAdapter(leaderAdapter);

        leaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leaderAdapter.setSelectedItem(position);
                queryCity(leaderAdapter.getItem(position).getItem());
            }
        });

        rvCity = findView(R.id.rv_city);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        cityAdapter = new SimpleListAdapter(R.layout.item_simple_list, null);
        cityAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvCity.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent();
                i.putExtra(EXTRA_CITY_NAME, cityAdapter.getItem(position).getItem());
                setResult(RESULT_OK, i);
                onBackPressed();
            }
        });
    }

    @Override
    protected void loadData() {

        DataSupport.findAllAsync(HeWeatherCity.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<HeWeatherCity> list = (List<HeWeatherCity>) t;
                HashSet<String> provinces = new HashSet<>();
                for (HeWeatherCity city : list) {
                    provinces.add(city.getProvinceZh());
                }
                List<String> provinceArray = new ArrayList<>(provinces);
                List<SimpleItem> datas = new ArrayList<>();
                for (String s : provinceArray) {
                    datas.add(new SimpleItem(s));
                }
                provinceAdapter.setNewData(datas);
            }
        });

    }

    private void queryLeader(String provice) {
        DataSupport.where("provinceZh = ?", provice).findAsync(HeWeatherCity.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<HeWeatherCity> list = (List<HeWeatherCity>) t;
                HashSet<String> leaders = new HashSet<>();
                for (HeWeatherCity city : list) {
                    leaders.add(city.getLeaderZh());
                }
                List<String> leaderArray = new ArrayList<>(leaders);
                List<SimpleItem> datas = new ArrayList<>();
                for (String s : leaderArray) {
                    datas.add(new SimpleItem(s));
                }
                leaderAdapter.setNewData(datas);
            }
        });
    }

    private void queryCity(String provice) {
        DataSupport.where("leaderZh = ?", provice).findAsync(HeWeatherCity.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<HeWeatherCity> list = (List<HeWeatherCity>) t;
                HashSet<String> citys = new HashSet<>();
                for (HeWeatherCity city : list) {
                    citys.add(city.getCityZh());
                }
                List<String> cityArray = new ArrayList<>(citys);
                List<SimpleItem> datas = new ArrayList<>();
                for (String s : cityArray) {
                    datas.add(new SimpleItem(s));
                }
                cityAdapter.setNewData(datas);
            }
        });
    }
}
