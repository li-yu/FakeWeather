package com.liyu.fakeweather.ui.weather;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseContentFragment;
import com.liyu.fakeweather.ui.weather.adapter.DividerGridItemDecoration;
import com.liyu.fakeweather.ui.weather.adapter.SuggestionAdapter;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ShareUtils;
import com.liyu.fakeweather.utils.SizeUtils;
import com.liyu.fakeweather.utils.TTSManager;
import com.liyu.fakeweather.utils.TimeUtils;
import com.liyu.fakeweather.utils.WeatherUtil;
import com.liyu.fakeweather.widgets.WeatherChartView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
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
    private ACache mCache;

    private HeWeather5 currentWeather;

    private Subscription subscription;

    LinearLayout dailyForecast;
    RecyclerView rvSuggestion;
    SuggestionAdapter suggestionAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather2;
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
                    TTSManager.getInstance(getActivity()).speak(WeatherUtil.getInstance().getShareMessage(currentWeather), null);
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
        rvSuggestion = findView(R.id.rvSuggestion);
        rvSuggestion.setLayoutManager(new GridLayoutManager(getContext(), 4));
        suggestionAdapter = new SuggestionAdapter(R.layout.item_suggestion, null);
        suggestionAdapter.setDuration(1000);
        suggestionAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvSuggestion.addItemDecoration(new DividerGridItemDecoration(getContext()));
        rvSuggestion.setAdapter(suggestionAdapter);

    }

    private Observable<HeWeather5> getLocalCache() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<HeWeather5>() {

            @Override
            public void call(Subscriber<? super HeWeather5> subscriber) {
                HeWeather5 cacheWeather = (HeWeather5) mCache.getAsObject(CACHE_WEAHTHER_NAME);
                if (cacheWeather == null) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(cacheWeather);
                }
            }
        });
    }

    private Observable<HeWeather5> getFromNetwork() {
        return RxLocation.get().locate(getActivity())
                .flatMap(new Func1<BDLocation, Observable<BaseWeatherResponse<HeWeather5>>>() {
                    @Override
                    public Observable<BaseWeatherResponse<HeWeather5>> call(BDLocation bdLocation) {
                        final String city = TextUtils.isEmpty(bdLocation.getCity()) ? "苏州" : bdLocation.getCity().replace("市", "");
                        tvCityName.setText(city);
                        return WeatherUtil.getInstance().getWeatherKey().flatMap(new Func1<String, Observable<BaseWeatherResponse<HeWeather5>>>() {
                            @Override
                            public Observable<BaseWeatherResponse<HeWeather5>> call(String key) {
                                return ApiFactory
                                        .getWeatherController()
                                        .getWeather(key, city)
                                        .subscribeOn(Schedulers.io());
                            }
                        });
                    }
                })
                .map(new Func1<BaseWeatherResponse<HeWeather5>, HeWeather5>() {
                    @Override
                    public HeWeather5 call(BaseWeatherResponse<HeWeather5> response) {
                        HeWeather5 heWeather5 = response.HeWeather5.get(0);
                        mCache.put(CACHE_WEAHTHER_NAME, heWeather5, 30 * 60);
                        WeatherUtil.getInstance().saveDailyHistory(heWeather5);
                        return heWeather5;
                    }
                });
    }

    @Override
    protected void lazyFetchData() {
        showRefreshing(true);
        subscription = Observable
                .concat(getLocalCache(), getFromNetwork())
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeWeather5>() {
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
                    public void onNext(HeWeather5 heWeather5) {
                        showWeather(heWeather5);
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

        dailyForecast = findView(R.id.contentLayout);
        dailyForecast.setPadding(0, SizeUtils.dp2px(getContext(), 16), 0, SizeUtils.dp2px(getContext(), 16));
        dailyForecast.removeAllViews();
        dailyForecast.addView(getChartView(weather));

        List suggestion = new ArrayList();
//        suggestion.add(weather.getAqi());
        suggestion.add(weather.getSuggestion().getAir());
        suggestion.add(weather.getSuggestion().getComf());
        suggestion.add(weather.getSuggestion().getCw());
        suggestion.add(weather.getSuggestion().getDrsg());
        suggestion.add(weather.getSuggestion().getFlu());
        suggestion.add(weather.getSuggestion().getSport());
        suggestion.add(weather.getSuggestion().getTrav());
        suggestion.add(weather.getSuggestion().getUv());
        for (int i = 0; i < suggestion.size(); i++) {
            if (suggestion.get(i) == null)
                suggestion.remove(i);
        }
        suggestionAdapter.setNewData(suggestion);

    }

    private WeatherChartView getChartView(HeWeather5 weather5) {
        WeatherChartView chartView = new WeatherChartView(getContext());
        chartView.setWeather5(weather5);
        return chartView;
    }

    private void shareWeather() {
        if (currentWeather == null)
            return;
        String shareType = SettingsUtil.getWeatherShareType();
        if (shareType.equals("纯文本"))
            ShareUtils.shareText(getActivity(), WeatherUtil.getInstance().getShareMessage(currentWeather));
        else if (shareType.equals("仿锤子便签"))
            ShareActivity.start(getActivity(), WeatherUtil.getInstance().getShareMessage(currentWeather));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
