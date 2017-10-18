package com.liyu.fakeweather.ui.weather;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;
import com.liyu.fakeweather.ui.weather.dynamic.BaseWeatherType;
import com.liyu.fakeweather.ui.weather.dynamic.DynamicWeatherView2;
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
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

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
                } else if (id == R.id.menu_tts) {

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
                type = new OvercastType(getActivity(), info);
                break;
            case "阴":
                type = new OvercastType(getActivity(), info);
                break;
            case "雨":
                type = new RainType(getActivity(), RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                break;
            case "雨夹雪":
                type = new RainType(getActivity(), RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                break;
            case "雪":
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_2);
                break;
            case "冰雹":
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_2);
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
}
