package com.liyu.fakeweather.ui.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.event.GirlsComingEvent;
import com.liyu.fakeweather.ui.girl.adapter.GirlsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Subscription;

/**
 * Created by liyu on 2016/10/31.
 */

public abstract class BaseGirlsListFragment extends BaseContentFragment {

    protected RecyclerView recyclerView;
    protected GirlsAdapter adapter;
    protected int currentPage = 1;
    protected boolean isLoading = false;
    protected Subscription subscription;
    protected int sendCount = 0;
    protected int receivedCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViews() {
        super.initViews();
        adapter = new GirlsAdapter(getActivity(), null);
        recyclerView = findView(R.id.rv_gank);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getGirlFromServer();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void lazyFetchData() {
        currentPage = 1;
        adapter.setNewData(null);
        getGirlFromServer();
    }

    protected abstract void getGirlFromServer();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void girlIsComing(GirlsComingEvent event) {
        if (!event.getFrom().equals(this.getClass().getName()))
            return;
        showRefreshing(false);
        if (adapter.getData() == null || adapter.getData().size() == 0) {
            adapter.setNewData(event.getGirls());
        } else {
            adapter.addData(adapter.getData().size(), event.getGirls());
        }
        receivedCount++;
        if (receivedCount == sendCount) {
            isLoading = false;
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
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        super.onDestroy();
    }
}
