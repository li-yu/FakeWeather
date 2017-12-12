package com.liyu.fakeweather.ui.weather;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseWeatherResponse;
import com.liyu.fakeweather.model.HeWeather5;
import com.liyu.fakeweather.model.HeWeatherCity;
import com.liyu.fakeweather.model.IFakeWeather;
import com.liyu.fakeweather.model.WeatherCity;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.weather.adapter.CardWeatherAdapter;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.ToastUtil;
import com.liyu.fakeweather.utils.WeatherUtil;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2017/10/18.
 */

public class CityManageActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CardWeatherAdapter adapter;

    private boolean dataChanged = false;

    private int dragStartPosition = 0;

    private int selectedItem = -1;

    private static final int REQUEST_CHOOSE_CITY = 114;

    public static final String EXTRA_DATA_CHANGED = "extra_data_changed";

    public static final String EXTRA_SELECTED_ITEM = "extra_selected_item";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_city;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        adapter = new CardWeatherAdapter(R.layout.item_card_weather, null);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView = findView(R.id.rv_city_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                if (source.getAdapterPosition() == 0 || target.getAdapterPosition() == 0)
                    return false;
                else
                    return super.onMove(recyclerView, source, target);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0)
                    return makeMovementFlags(0, 0);
                else
                    return super.getMovementFlags(recyclerView, viewHolder);
            }


        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        // 开启拖拽
        adapter.enableDragItem(itemTouchHelper, R.id.card_root, true);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                dragStartPosition = pos;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                if (dragStartPosition != pos) {
                    dataChanged = true;
                }
            }
        });

        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                DataSupport.deleteAll(WeatherCity.class, "cityName = ?", adapter.getItem(pos).getCityName());
                Snackbar.make(getWindow().getDecorView().getRootView().findViewById(android.R.id.content), adapter.getItem(pos).getCityName() + " 删除成功!",
                        Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.actionColor)).show();
                dataChanged = true;
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectedItem = position;
                onBackPressed();
            }
        });

    }

    @Override
    protected void loadData() {

        DataSupport.order("cityIndex").findAsync(WeatherCity.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                adapter.setNewData((List<WeatherCity>) t);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_city) {
            startActivityForResult(new Intent(this, CityChooseActivity.class), REQUEST_CHOOSE_CITY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Observable<IFakeWeather> getFromNetwork(final String city) {
        int weatherSrc = SettingsUtil.getWeatherSrc();
        if (weatherSrc == SettingsUtil.WEATHER_SRC_HEFENG) {
            return WeatherUtil.getInstance().getWeatherKey().flatMap(new Func1<String, Observable<BaseWeatherResponse<HeWeather5>>>() {
                @Override
                public Observable<BaseWeatherResponse<HeWeather5>> call(String key) {
                    return ApiFactory
                            .getWeatherController()
                            .getWeather(key, city)
                            .subscribeOn(Schedulers.io());
                }
            }).map(new Func1<BaseWeatherResponse<HeWeather5>, IFakeWeather>() {
                @Override
                public IFakeWeather call(BaseWeatherResponse<HeWeather5> response) {
                    HeWeather5 heWeather5 = response.HeWeather5.get(0);
                    ACache.get(CityManageActivity.this).put(city, heWeather5, 30 * 60);
                    return heWeather5;
                }
            });
        } else if (weatherSrc == SettingsUtil.WEATHER_SRC_XIAOMI) {

            // TODO: 2017/10/12 完成小米天气源的适配
            return null;
        } else
            return null;

    }

    private void queryWeather(String city) {
        getFromNetwork(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IFakeWeather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLong(e.getMessage());
                    }

                    @Override
                    public void onNext(IFakeWeather weather) {
                        updateCity(weather);
                    }
                });
    }

    private void updateCity(IFakeWeather weather) {
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (weather.getFakeBasic().getCityName().equals(adapter.getItem(i).getCityName())) {
                adapter.getItem(i).setWeatherCode(weather.getFakeNow().getNowCode());
                adapter.getItem(i).setWeatherTemp(weather.getFakeNow().getNowTemp());
                adapter.getItem(i).setWeatherText(weather.getFakeNow().getNowText());
                adapter.notifyItemChanged(i);

                ContentValues values = new ContentValues();
                values.put("weatherCode", weather.getFakeNow().getNowCode());
                values.put("weatherText", weather.getFakeNow().getNowText());
                values.put("weatherTemp", weather.getFakeNow().getNowTemp());
                DataSupport.updateAll(WeatherCity.class, values, "cityName = ?", weather.getFakeBasic().getCityName());

                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (adapter.getData() != null) {
            for (int i = 0; i < adapter.getData().size(); i++) {
                ContentValues values = new ContentValues();
                values.put("cityIndex", i);
                DataSupport.updateAll(WeatherCity.class, values, "cityName = ?", adapter.getItem(i).getCityName());
            }
        }
        Intent i = new Intent();
        i.putExtra(EXTRA_SELECTED_ITEM, selectedItem);
        i.putExtra(EXTRA_DATA_CHANGED, dataChanged);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_CITY && resultCode == Activity.RESULT_OK && data != null) {
            HeWeatherCity city = (HeWeatherCity) data.getSerializableExtra(CityChooseActivity.EXTRA_CITY_NAME);
            if (city != null) {
                WeatherCity weatherCity = new WeatherCity();
                weatherCity.setCityIndex(adapter.getData().size());
                weatherCity.setCityName(city.getCityZh());
                weatherCity.setCityId(city.getId());
                if (!adapter.getData().contains(weatherCity)) {
                    adapter.addData(weatherCity);
                    weatherCity.save();
                    queryWeather(city.getId());
                    dataChanged = true;
                } else {
                    ToastUtil.showShort("重复的城市！");
                }
            }
        }
    }
}
