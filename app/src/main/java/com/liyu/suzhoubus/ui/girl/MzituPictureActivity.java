package com.liyu.suzhoubus.ui.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.event.GirlsComingEvent;
import com.liyu.suzhoubus.model.Girl;
import com.liyu.suzhoubus.service.GirlService;
import com.liyu.suzhoubus.ui.base.BaseActivity;
import com.liyu.suzhoubus.ui.girl.adapter.GirlsAdapter;
import com.liyu.suzhoubus.utils.ThemeUtil;

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
 * Created by liyu on 2016/11/3.
 */

public class MzituPictureActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";

    private RecyclerView recyclerView;
    private GirlsAdapter adapter;
    private String baseUrl = "";
    protected SwipeRefreshLayout refreshLayout;
    private int totalPages = 1;
    private Subscription getPageSubscription;
    private Subscription getPicSubscription;

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, MzituPictureActivity.class);
        intent.putExtra(MzituPictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(MzituPictureActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mzitu_picture;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        baseUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter.getData() == null || adapter.getData().size() == 0)
                    loadData();
                else {
                    adapter.notifyDataSetChanged();
                    showRefreshing(false);
                }
            }
        });
        refreshLayout.setColorSchemeResources(ThemeUtil.getCurrentColorPrimary(this));
        recyclerView = (RecyclerView) findViewById(R.id.rv_gank);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GirlsAdapter(this, null);
        recyclerView.setAdapter(adapter);
    }

    private void getMeiziFromServer(int page) {
        String url = baseUrl + "/" + page;
        getPicSubscription = Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, List<Girl>>() {
            @Override
            public List<Girl> call(String url) {
                List<Girl> girls = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Element total = doc.select("div.main-image").first();
                    String s = total.select("img").first().attr("src");
                    girls.add(new Girl(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return girls;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Girl>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                showRefreshing(false);
            }

            @Override
            public void onNext(List<Girl> girls) {
                GirlService.start(MzituPictureActivity.this, GirlsComingEvent.GIRLS_FROM_MZITU, girls);
            }
        });
    }

    @Override
    protected void loadData() {
        adapter.setNewData(null);
        getPageSubscription = Observable.just(baseUrl).subscribeOn(Schedulers.io()).map(new Func1<String, List<Girl>>() {
            @Override
            public List<Girl> call(String url) {
                List<Girl> girls = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Element total = doc.select("div.pagenavi").first();
                    Elements spans = total.select("span");
                    for (Element s : spans) {
                        int page;
                        try {
                            page = Integer.parseInt(s.text());
                            if (page >= totalPages)
                                totalPages = page;
                        } catch (Exception e) {

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return girls;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Girl>>() {
            @Override
            public void onCompleted() {
                showRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                showRefreshing(false);
            }

            @Override
            public void onNext(List<Girl> girls) {
                for (int i = 1; i <= totalPages; i++) {
                    getMeiziFromServer(i);
                }
            }
        });
    }

    protected void showRefreshing(final boolean refresh) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void girlIsComing(GirlsComingEvent event) {
        if (event.getFrom() != GirlsComingEvent.GIRLS_FROM_MZITU)
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
        if (getPageSubscription != null && !getPageSubscription.isUnsubscribed())
            getPageSubscription.unsubscribe();
        if (getPicSubscription != null && !getPicSubscription.isUnsubscribed())
            getPicSubscription.unsubscribe();
        super.onDestroy();
    }

}
