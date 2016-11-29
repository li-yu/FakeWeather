package com.liyu.suzhoubus.ui.bus;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.event.BusFavoritesEvent;
import com.liyu.suzhoubus.event.ThemeChangedEvent;
import com.liyu.suzhoubus.model.BusLineDetail;
import com.liyu.suzhoubus.model.FavoritesBusBean;
import com.liyu.suzhoubus.ui.base.BaseContentFragment;
import com.liyu.suzhoubus.ui.bus.adapter.FavoritesAdapter;
import com.liyu.suzhoubus.utils.RxDataBase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by liyu on 2016/11/16.
 */

public class FavoritesFragment extends BaseContentFragment {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bus_favorites;
    }

    @Override
    protected void initViews() {
        super.initViews();
        recyclerView = findView(R.id.rv_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoritesAdapter(R.layout.item_bus_favorites, null);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void lazyFetchData() {
        showRefreshing(true);
        RxDataBase.getAll(BusLineDetail.class, "isFavorite = ?", "1").map(new Func1<List<BusLineDetail>, List<FavoritesBusBean>>() {
            @Override
            public List<FavoritesBusBean> call(List<BusLineDetail> lines) {
                if (lines == null || lines.size() == 0) {
                    return null;
                }
                List<FavoritesBusBean> favList = new ArrayList<>();
                for (BusLineDetail line : lines) {
                    favList.add(new FavoritesBusBean(line));
                }
                return favList;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<FavoritesBusBean>>() {
            @Override
            public void onCompleted() {
                showRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                showRefreshing(false);
            }

            @Override
            public void onNext(List<FavoritesBusBean> favList) {
                adapter.setNewData(favList);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFavoritesChanged(BusFavoritesEvent event) {
        int positon = adapter.getData().indexOf(event.getFavorite());
        if (positon >= 0) {
            if (!event.isFavorite()) {
                adapter.remove(positon);
            }
        } else {
            if (event.isFavorite()) {
                adapter.addData(event.getFavorite());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
