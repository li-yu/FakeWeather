package com.liyu.suzhoubus.ui.reading;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.model.XianduItem;
import com.liyu.suzhoubus.ui.base.BaseContentFragment;
import com.liyu.suzhoubus.ui.reading.adapter.XianduAdapter;

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

public class CategoryFragment extends BaseContentFragment {

    private RecyclerView recyclerView;
    private XianduAdapter adapter;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new XianduAdapter(getActivity(), null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getXianduFromServer();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getXianduFromServer() {
        showRefreshing(true);
        String url = baseUrl + "/page/" + currentPage;
        subscription = Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, List<XianduItem>>() {
            @Override
            public List<XianduItem> call(String url) {
                List<XianduItem> xianduItems = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Element total = doc.select("div.xiandu_items").first();
                    Elements items = total.select("div.xiandu_item");
                    for (Element element : items) {
                        XianduItem item = new XianduItem();
                        Element left = element.select("div.xiandu_left").first();
                        Element right = element.select("div.xiandu_right").first();
                        item.setName(left.select("a[href]").text());
                        item.setFrom(right.select("a").attr("title"));
                        item.setUpdateTime(left.select("span").select("small").text());
                        item.setUrl(left.select("a[href]").attr("href"));
                        item.setIcon(right.select("img").first().attr("src"));
                        xianduItems.add(item);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return xianduItems;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<XianduItem>>() {
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
            public void onNext(List<XianduItem> xianduItems) {
                currentPage++;
                showRefreshing(false);
                if (adapter.getData() == null || adapter.getData().size() == 0) {
                    adapter.setNewData(xianduItems);
                } else {
                    adapter.addData(adapter.getData().size(), xianduItems);
                }
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        currentPage = 1;
        adapter.setNewData(null);
        getXianduFromServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
