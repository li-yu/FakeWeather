package com.liyu.fakeweather.ui.weather;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseContentFragment;
import com.liyu.fakeweather.ui.weather.adapter.WeatherAdapter;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.TTSManager;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/10/31.
 */

public class WeatherFragment extends BaseContentFragment {

    private static final String CACHE_WEAHTHER_NAME = "weather_cache";

    private Toolbar mToolbar;
    private TextView tvCityName;
    private TextView tvNowWeatherString;
    private TextView tvNowTemp;
    private TextView tvUpdateTime;
    private TextView tvAqi;

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    private ACache mCache;

    private HeWeather5 currentWeather;

    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCache = ACache.get(getActivity());
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("天气");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_weather);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_share) {
                    new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean result) {
                            if (result) {
                                shareWeather();
                            }
                        }
                    });
                    return true;
                } else if (id == R.id.menu_tts) {
                    TTSManager.getInstance(getActivity()).speak(WeatherUtil.getShareMessage(currentWeather), null);
                    return true;
                }
                return false;
            }
        });
        tvCityName = findView(R.id.tv_city_name);
        tvNowWeatherString = findView(R.id.tv_weather_string);
        tvNowTemp = findView(R.id.tv_temp);
        tvUpdateTime = findView(R.id.tv_update_time);
        tvAqi = findView(R.id.tv_weather_aqi);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findView(R.id.rv_weather);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(null);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void lazyFetchData() {
        showRefreshing(true);
        HeWeather5 cacheWeather = (HeWeather5) mCache.getAsObject(CACHE_WEAHTHER_NAME);
        if (cacheWeather != null) {
            showWeather(cacheWeather);
            showRefreshing(false);
            return;
        }

        subscription = RxLocation.get().locate(getActivity())
                .flatMap(new Func1<BDLocation, Observable<BaseWeatherResponse<HeWeather5>>>() {
                    @Override
                    public Observable<BaseWeatherResponse<HeWeather5>> call(BDLocation bdLocation) {
                        String city = TextUtils.isEmpty(bdLocation.getCity()) ? "苏州" : bdLocation.getCity().replace("市", "");
                        tvCityName.setText(city);
                        return ApiFactory
                                .getWeatherController()
                                .getWeather(city)
                                .subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseWeatherResponse<HeWeather5>>() {
                    @Override
                    public void onCompleted() {
                        showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showRefreshing(false);
                        Snackbar.make(getView(), "获取天气失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lazyFetchData();
                            }
                        }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                    }

                    @Override
                    public void onNext(BaseWeatherResponse<HeWeather5> response) {
                        if (response == null || response.HeWeather5.size() == 0
                                || !response.HeWeather5.get(0).getStatus().equals("ok")) {
                            return;
                        }
                        showWeather(response.HeWeather5.get(0));
                        mCache.put(CACHE_WEAHTHER_NAME, response.HeWeather5.get(0), 10 * 60);
                        WeatherUtil.saveDailyHistory(response.HeWeather5.get(0));
                    }
                });

    }

    private void showWeather(HeWeather5 weather) {
        currentWeather = weather;
        tvCityName.setText(weather.getBasic().getCity());
        tvNowWeatherString.setText(weather.getNow().getCond().getTxt());
        tvAqi.setText(weather.getAqi() == null ? "" : weather.getAqi().getCity().getQlty());
        tvNowTemp.setText(String.format("%s℃", weather.getNow().getTmp()));
        String updateTime = TimeUtils.string2String(weather.getBasic().getUpdate().getLoc(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()), new SimpleDateFormat("HH:mm", Locale.getDefault()));
        tvUpdateTime.setText(String.format("截止 %s", updateTime));

        List<MultiItemEntity> weather5s = new ArrayList<>();

        HeWeather5 nowWeather = (HeWeather5) weather.clone();
        nowWeather.setItemType(HeWeather5.TYPE_NOW);
        weather5s.add(nowWeather);

        weather5s.add(weather.getSuggestion());

        HeWeather5 dailyWeather = (HeWeather5) weather.clone();
        dailyWeather.setItemType(HeWeather5.TYPE_DAILYFORECAST);
        weather5s.add(dailyWeather);

        adapter.setNewData(weather5s);
    }

    private void shareWeather() {
        if (currentWeather == null)
            return;
        String shareType = SettingsUtil.getWeatherShareType();
        if (shareType.equals("纯文本"))
            ShareUtils.shareText(getActivity(), WeatherUtil.getShareMessage(currentWeather));
        else if (shareType.equals("仿锤子便签"))
            ShareActivity.start(getActivity(), WeatherUtil.getShareMessage(currentWeather));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
