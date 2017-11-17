package com.liyu.fakeweather.ui.weather;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.baidu.location.BDLocation;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.model.HeWeatherCity;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.model.WeatherCity;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;
import com.liyu.fakeweather.ui.base.BaseViewPagerAdapter;
import com.liyu.fakeweather.ui.weather.dynamic.BaseWeatherType;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView;
import com.liyu.fakeweather.ui.weather.dynamic.FogType;
import com.liyu.fakeweather.ui.weather.dynamic.OvercastType;
import com.liyu.fakeweather.ui.weather.dynamic.RainType;
import com.liyu.fakeweather.ui.weather.dynamic.SandstormType;
import com.liyu.fakeweather.ui.weather.dynamic.ShortWeatherInfo;
import com.liyu.fakeweather.ui.weather.dynamic.SnowType;
import com.liyu.fakeweather.ui.weather.dynamic.SunnyType;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.liyu.fakeweather.widgets.SimplePagerIndicator;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liyu on 2017/8/24.
 */

public class WeatherFragment extends BaseFragment {

    private DynamicWeatherView dynamicWeatherView;
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private BaseViewPagerAdapter adapter;
    private SimplePagerIndicator pagerTitleView;

    private static final int REQUEST_CITY_MANAGE = 280;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public DynamicWeatherView getDynamicWeatherView() {
        return dynamicWeatherView;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_weather);
        dynamicWeatherView = findView(R.id.dynamicWeather);
        pagerTitleView = findView(R.id.pager_title);
        viewPager = findView(R.id.weatherViewPager);
        viewPager.setPageTransformer(true, new WeatherPageTransformer());
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_share) {
                    new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean result) {
                            if (result) {
                                shareWeather(dynamicWeatherView.getOriginWeather());
                            }
                        }
                    });
                    return true;
                } else if (id == R.id.menu_city_manage) {
                    startActivityForResult(new Intent(getActivity(), CityManageActivity.class), REQUEST_CITY_MANAGE);
                    return true;
                } else if (id == R.id.menu_preview) {
                    previewDynamicWeather();
                }
                return false;
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        RxLocation
                .get()
                .locate(getActivity())
                .map(new Func1<BDLocation, List<WeatherCity>>() {
                    @Override
                    public List<WeatherCity> call(BDLocation bdLocation) {
                        String nowCity = TextUtils.isEmpty(bdLocation.getCity()) ? "苏州" : bdLocation.getCity().replace("市", "");
                        String nowProvince = TextUtils.isEmpty(bdLocation.getProvince()) ? "江苏" : bdLocation.getCity().replace("省", "");
                        HeWeatherCity heWeatherCity = DataSupport.where("provinceZh = ? and cityZh = ?", nowProvince, nowCity).findFirst(HeWeatherCity.class);
                        List<WeatherCity> savedCities = DataSupport.order("cityIndex").find(WeatherCity.class);
                        WeatherCity city = new WeatherCity();
                        city.setCityIndex(0);
                        if (heWeatherCity == null) {
                            city.setCityId("CN101190401");
                            city.setCityName("苏州");
                        } else {
                            city.setCityId(heWeatherCity.getId());
                            city.setCityName(heWeatherCity.getCityZh());
                        }

                        int index = savedCities.indexOf(city);
                        if (index >= 0) {
                            savedCities.set(0, savedCities.get(index));
                            ContentValues values = new ContentValues();
                            values.put("cityIndex", 0);
                            DataSupport.updateAll(WeatherCity.class, values, "cityName = ?", nowCity);
                        } else {
                            city.save();
                            savedCities.add(0, city);
                        }
                        return savedCities;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<WeatherCity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(), "获取天气失败!", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lazyFetchData();
                            }
                        }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                    }

                    @Override
                    public void onNext(List<WeatherCity> weatherCities) {
                        adapter = new BaseViewPagerAdapter(getChildFragmentManager());
                        for (WeatherCity city : weatherCities) {
                            Fragment fragment = new CityWeatherFragment();
                            Bundle data = new Bundle();
                            data.putString("city", city.getCityId());
                            fragment.setArguments(data);
                            adapter.addFrag(fragment, city.getCityName());
                        }
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(adapter.getCount());
                        pagerTitleView.setViewPager(viewPager);
                    }
                });
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

    private void shareWeather(IFakeWeather weather) {
        if (weather == null)
            return;
        String shareType = SettingsUtil.getWeatherShareType();
        if (shareType.equals("纯文本")) {
            ShareUtils.shareText(getActivity(), WeatherUtil.getInstance().getShareMessage(weather), "分享到");
        } else if (shareType.equals("仿锤子便签")) {
            ShareActivity.start(getActivity(), WeatherUtil.getInstance().getShareMessage(weather));
        }
    }

    private void previewDynamicWeather() {
        final String[] items = new String[]{"晴（白天）", "晴（夜晚）", "多云", "阴", "雨", "雨夹雪",
                "雪", "冰雹", "雾", "雾霾", "沙尘暴"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("动态天气预览");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switchDynamicWeather(items[which]);
            }
        });
        builder.create().show();

    }

    private void switchDynamicWeather(String which) {
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode("100");
        info.setWindSpeed("11");
        BaseWeatherType type;
        switch (which) {
            case "晴（白天）":
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                type = new SunnyType(getActivity(), info);
                break;
            case "晴（夜晚）":
                info.setSunrise("00:00");
                info.setSunset("00:01");
                info.setMoonrise("00:01");
                info.setMoonset("23:59");
                type = new SunnyType(getActivity(), info);
                break;
            case "多云":
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                SunnyType sunnyType = new SunnyType(getActivity(), info);
                sunnyType.setCloud(true);
                type = sunnyType;
                break;
            case "阴":
                type = new OvercastType(getActivity(), info);
                break;
            case "雨":
                RainType rainType = new RainType(getActivity(), RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                rainType.setFlashing(true);
                type = rainType;
                break;
            case "雨夹雪":
                type = new RainType(getActivity(), RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                break;
            case "雪":
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_2);
                break;
            case "冰雹":
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_3);
                break;
            case "雾":
                type = new FogType(getActivity());
                break;
            case "雾霾":
                type = new FogType(getActivity());
                break;
            case "沙尘暴":
                type = new SandstormType(getActivity());
                break;
            default:
                type = new SunnyType(getActivity(), info);
        }
        dynamicWeatherView.setType(type);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CITY_MANAGE && resultCode == Activity.RESULT_OK && data != null) {
            final boolean changed = data.getBooleanExtra(CityManageActivity.EXTRA_DATA_CHANGED, false);
            final int selectedItem = data.getIntExtra(CityManageActivity.EXTRA_SELECTED_ITEM, 0);
            if (changed) {
                DataSupport.order("cityIndex").findAsync(WeatherCity.class).listen(new FindMultiCallback() {
                    @Override
                    public <T> void onFinish(List<T> t) {
                        List<WeatherCity> weatherCities = (List<WeatherCity>) t;
                        if (adapter != null) {
                            adapter.clear();
                            adapter = null;
                        }
                        adapter = new BaseViewPagerAdapter(getChildFragmentManager());
                        for (WeatherCity city : weatherCities) {
                            Fragment fragment = new CityWeatherFragment();
                            Bundle data = new Bundle();
                            data.putString("city", city.getCityId());
                            fragment.setArguments(data);
                            adapter.addFrag(fragment, city.getCityName());
                        }
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(adapter.getCount());
                        pagerTitleView.notifyDataSetChanged();
                    }
                });
            }
            if (selectedItem > -1) {
                viewPager.setCurrentItem(selectedItem);
            }
        }
    }
}
