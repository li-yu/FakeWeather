package com.liyu.suzhoubus.ui.girl;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.event.GirlsComingEvent;
import com.liyu.suzhoubus.model.Girl;
import com.liyu.suzhoubus.service.GirlService;
import com.liyu.suzhoubus.ui.base.BaseContentFragment;
import com.liyu.suzhoubus.ui.girl.adapter.MzituAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/12/13.
 */

public class MzituFragment extends BaseContentFragment {

    private RecyclerView recyclerView;
    private MzituAdapter adapter;
    private int currentPage = 1;
    private String baseUrl = "";
    private boolean isLoading = false;
    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViews() {
        super.initViews();
        baseUrl = getArguments().getString("url");
        recyclerView = findView(R.id.rv_gank);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MzituAdapter(getActivity(), null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getMeiziFromServer();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getMeiziFromServer() {
        showRefreshing(true);
        String url = baseUrl + "/page/" + currentPage;
        subscription = Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, List<Girl>>() {
            @Override
            public List<Girl> call(String url) {
                List<Girl> girls = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Element total = doc.select("div.postlist").first();
                    Elements items = total.select("li");
                    for (Element element : items) {
                        Girl girl = new Girl(element.select("img").first().attr("data-original"));
                        girl.setLink(element.select("a[href]").attr("href"));
                        girls.add(girl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return girls;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Girl>>() {
            @Override
            public void onCompleted() {
                isLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                isLoading = false;
                showRefreshing(false);
            }

            @Override
            public void onNext(List<Girl> girls) {
                currentPage++;
                showRefreshing(false);
                if (adapter.getData() == null || adapter.getData().size() == 0) {
                    adapter.setNewData(girls);
                } else {
                    adapter.addData(adapter.getData().size(), girls);
                }
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        currentPage = 1;
        adapter.setNewData(null);
        getMeiziFromServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
