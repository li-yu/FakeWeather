package com.liyu.fakeweather.ui.bus;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;

import com.liyu.fakeweather.App;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.LPLine;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.bus.adapter.AllLineAdapter;
import com.liyu.fakeweather.utils.ThemeUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2017/2/3.
 */

public class AllLineActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private AllLineAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_bus;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshLayout.setColorSchemeResources(ThemeUtil.getCurrentColorPrimary(this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_bus_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllLineAdapter(R.layout.item_bus_all, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        showRefreshing(true);
        subscription = Observable
                .just("哈哈，开始工作！")
                .flatMap(new Func1<String, Observable<List<LPLine>>>() {
                    @Override
                    public Observable<List<LPLine>> call(String s) {
                        try {
                            return Observable.just(getLPLinesFromXml());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LPLine>>() {
                    @Override
                    public void onCompleted() {
                        showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showRefreshing(false);
                    }

                    @Override
                    public void onNext(List<LPLine> lpLines) {
                        adapter.setNewData(lpLines);
                    }
                });
    }

    private List<LPLine> getLPLinesFromXml() throws IOException, XmlPullParserException {
        List<LPLine> list = null;
        LPLine line = null;
        InputStream is = App.getContext().getAssets().open("suzhoubus.xml");
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(is, "UTF-8");
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    list = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("LPLine")) {
                        line = new LPLine();
                    } else if (xpp.getName().equals("LPLineName")) {
                        xpp.next();
                        line.setName(xpp.getText());
                    } else if (xpp.getName().equals("LPGUID")) {
                        xpp.next();
                        line.setId(xpp.getText());
                    } else if (xpp.getName().equals("LPFStdName")) {
                        xpp.next();
                        line.setFromWhere(xpp.getText());
                    } else if (xpp.getName().equals("LPEStdName")) {
                        xpp.next();
                        line.setToWhere(xpp.getText());
                    } else if (xpp.getName().equals("LPFStdFTime")) {
                        xpp.next();
                        line.setStartTime(xpp.getText());
                    } else if (xpp.getName().equals("LPFStdETime")) {
                        xpp.next();
                        line.setEndTime(xpp.getText());
                    } else if (xpp.getName().equals("LPIntervalH")) {
                        xpp.next();
                        line.setMinTime(xpp.getText());
                    } else if (xpp.getName().equals("LPIntervalN")) {
                        xpp.next();
                        line.setMaxTime(xpp.getText());
                    } else if (xpp.getName().equals("LPDirection")) {
                        xpp.next();
                        line.setDirection(xpp.getText());
                    } else if (xpp.getName().equals("LPLineDirect")) {
                        xpp.next();
                        line.setLineDirect(xpp.getText());
                    } else if (xpp.getName().equals("LPStandName")) {
                        xpp.next();
                        line.setStations(xpp.getText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("LPLine")) {
                        list.add(line);
                        line = null;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        final Collator chineseCollator = Collator.getInstance(Locale.CHINA);
        Collections.sort(list, new Comparator<LPLine>() {
            @Override
            public int compare(LPLine o1, LPLine o2) {
                return chineseCollator.compare(o1.getName(), o2.getName());
            }
        });
        return list;
    }

    private void showRefreshing(final boolean refresh) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
