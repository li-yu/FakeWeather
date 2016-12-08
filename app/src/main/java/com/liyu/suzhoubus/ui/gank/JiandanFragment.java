package com.liyu.suzhoubus.ui.gank;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.event.JiandanEvent;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseGankResponse;
import com.liyu.suzhoubus.http.BaseJiandanResponse;
import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.model.JiandanXXOO;
import com.liyu.suzhoubus.service.GirlService;
import com.liyu.suzhoubus.service.XXOOService;
import com.liyu.suzhoubus.ui.MainActivity;
import com.liyu.suzhoubus.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/10/31.
 */

public class JiandanFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private JiandanAdapter adapter;
    private int currentIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViews() {
        adapter = new JiandanAdapter(R.layout.item_gank, null);
        adapter.openLoadAnimation();
        recyclerView = findView(R.id.rv_gank);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new ProgressBar(getActivity()));
        adapter.setLoadingView(new ProgressBar(getActivity()));
        adapter.openLoadMore(25);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getGirlFromServer();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case 2:
                        Glide.with(getActivity()).pauseRequests();

                        break;
                    case 0:
                        Glide.with(getActivity()).resumeRequests();

                        break;
                    case 1:
                        Glide.with(getActivity()).resumeRequests();

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        getGirlFromServer();
    }

    private void getGirlFromServer() {
        ApiFactory
                .getGankController()
                .getXXOO(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJiandanResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(), "获取XXOO失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lazyFetchData();
                            }
                        }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                    }

                    @Override
                    public void onNext(BaseJiandanResponse baseJiandanResponse) {
                        currentIndex++;
//                        updateXXOOs(new JiandanEvent(baseJiandanResponse.comments));
                        XXOOService.start(getActivity(), baseJiandanResponse.comments);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateXXOOs(JiandanEvent event) {
        if (adapter.getData() == null || adapter.getData().size() == 0) {
            adapter.setNewData(event.getXxoos());
        } else {
            adapter.addData(adapter.getData().size(), event.getXxoos());
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
