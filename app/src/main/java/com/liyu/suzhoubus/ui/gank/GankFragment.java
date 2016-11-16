package com.liyu.suzhoubus.ui.gank;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseGankResponse;
import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.service.GirlService;
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

public class GankFragment extends BaseFragment {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private GankAdapter adapter;
    private int currentIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("福利");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_bus);

        adapter = new GankAdapter(R.layout.item_gank, null);
        adapter.openLoadAnimation();
        recyclerView = findView(R.id.rv_gank);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.openLoadMore(10);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getGirlFromServer();
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
                .getGank(String.valueOf(currentIndex))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseGankResponse<List<Gank>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseGankResponse<List<Gank>> response) {
                        currentIndex++;
                        GirlService.start(getActivity(), response.results);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateGirls(final List<Gank> ganks) {
        adapter.addData(adapter.getData().size(), ganks);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}
