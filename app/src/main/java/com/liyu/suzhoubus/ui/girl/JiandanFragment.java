package com.liyu.suzhoubus.ui.girl;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.event.GirlsComingEvent;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseJiandanResponse;
import com.liyu.suzhoubus.model.Girl;
import com.liyu.suzhoubus.model.JiandanXXOO;
import com.liyu.suzhoubus.service.GirlService;
import com.liyu.suzhoubus.ui.base.BaseContentFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/10/31.
 */

public class JiandanFragment extends BaseContentFragment {

    private RecyclerView recyclerView;
    private GirlsAdapter adapter;
    private int currentPage = 1;
    private boolean isLoading = false;

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

    private void getGirlFromServer() {
        showRefreshing(true);
        ApiFactory
                .getGirlsController()
                .getXXOO(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJiandanResponse>() {
                    @Override
                    public void onCompleted() {
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        showRefreshing(false);
                        Snackbar.make(getView(), "获取煎蛋妹纸失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getGirlFromServer();
                            }
                        }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                    }

                    @Override
                    public void onNext(BaseJiandanResponse baseJiandanResponse) {
                        currentPage++;
                        List<Girl> items = new ArrayList<>();
                        for (JiandanXXOO item : baseJiandanResponse.comments) {
                            for (String url : item.getPics()) {
                                if (!url.toLowerCase().endsWith("gif")) {
                                    //gif占用内存&流量太大，pass掉
                                    items.add(new Girl(url));
                                }
                            }
                        }
                        GirlService.start(getActivity(), GirlsComingEvent.GIRLS_FROM_JIANDAN, items);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void girlIsComing(GirlsComingEvent event) {
        if (event.getFrom() != GirlsComingEvent.GIRLS_FROM_JIANDAN)
            return;
        showRefreshing(false);
        if (adapter.getData() == null || adapter.getData().size() == 0) {
            adapter.setNewData(event.getGirls());
        } else {
            adapter.addData(adapter.getData().size(), event.getGirls());
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
