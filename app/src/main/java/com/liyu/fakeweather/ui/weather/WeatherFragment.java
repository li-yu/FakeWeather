package com.liyu.fakeweather.ui.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView2;
import com.liyu.fakeweather.ui.weather.dynamic.SandstormType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/8/24.
 */

public class WeatherFragment extends BaseFragment {

    private DynamicWeatherView2 dynamicWeatherView;
    private Toolbar mToolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather4;
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public DynamicWeatherView2 getDynamicWeatherView() {
        return dynamicWeatherView;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_weather);
        dynamicWeatherView = findView(R.id.dynamicWeather);
        ViewPager viewPager = findView(R.id.weatherViewPager);
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CityWeatherFragment());
        fragmentList.add(new CityWeatherFragment());
        fragmentList.add(new CityWeatherFragment());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setPageTransformer(true, new WeatherPageTransformer());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void lazyFetchData() {
        dynamicWeatherView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dynamicWeatherView.setType(new SandstormType(getContext()));
            }
        }, 3000);
    }

    @Override
    public void onResume() {
        super.onResume();
        dynamicWeatherView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dynamicWeatherView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dynamicWeatherView.onDestroy();
    }
}
