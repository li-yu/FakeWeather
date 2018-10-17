package com.liyu.fakeweather.ui.bus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.model.AmapPoi;
import com.liyu.fakeweather.ui.base.BaseActivity;
import com.liyu.fakeweather.ui.bus.adapter.PoiSearchAdapter;
import com.liyu.fakeweather.utils.DialogUtil;
import com.liyu.fakeweather.utils.LocationUtil;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 永安行公共自行车界面
 * Created by liyu on 2018/10/12.
 */
public class PublicBikeActivity extends BaseActivity {

    private WebView webView;
    private MenuItem search;
    private SearchView searchView;
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private PoiSearchAdapter searchAdapter;
    private String currentCity = "苏州";

    private DecimalFormat df = new DecimalFormat("#.000000");

    private static final String AMAP_KEY = "1a220b953abcfd5b148cd54bb68830aa";

    private static final String UA = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_bike;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_bike;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        webView = findView(R.id.webBike);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(UA);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                DialogUtil.showProgressDialog(PublicBikeActivity.this, "加载中...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtil.dismissProgressDialog(PublicBikeActivity.this);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    toolbar.setTitle(title);
                }
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        initSearchView(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void initSearchView(Menu menu) {
        search = menu.findItem(R.id.menu_poi_search);
        searchView = (SearchView) search.getActionView();
        RxSearchView
                .queryTextChanges(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                .switchMap(new Func1<CharSequence, Observable<AmapPoi>>() {
                    @Override
                    public Observable<AmapPoi> call(CharSequence charSequence) {
                        return ApiFactory.getBusController().searchPoi(charSequence.toString(), currentCity, AMAP_KEY).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<AmapPoi>() {
                    @Override
                    public void onNext(AmapPoi amapPoi) {
                        searchAdapter.setNewData(amapPoi.getPois());
                        showAsDropDown(popupWindow, searchView);
                    }
                });

        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                showAsDropDown(popupWindow, toolbar);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                popupWindow.dismiss();
                return true;
            }
        });
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.fragment_poi_search, null);
        recyclerView = contentView.findViewById(R.id.rv_line_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new PoiSearchAdapter(R.layout.item_bus_line_search, null);
        recyclerView.setAdapter(searchAdapter);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuItemCompat.collapseActionView(search);
                String url = String.format("http://ws.uibike.com/map.php?lnglat=%s", searchAdapter.getData().get(position).getLocation());
                webView.loadUrl(url);
            }
        });
    }

    public void showAsDropDown(PopupWindow window, View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            window.setHeight(height);
        }
        popupWindow.showAsDropDown(anchor);
    }

    @Override
    protected void loadData() {
        RxLocation
                .get()
                .locate(this)
                .subscribe(new Observer<BDLocation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(e.getMessage());
                    }

                    @Override
                    public void onNext(BDLocation bdLocation) {
                        currentCity = bdLocation.getAddress().city;
                        double[] loc =
                                LocationUtil.bd09_To_Gcj02(bdLocation.getLatitude(), bdLocation.getLongitude());
                        String url = String.format("http://ws.uibike.com/map.php?lnglat=%s,%s", df.format(loc[1]), df.format(loc[0]));
                        webView.loadUrl(url);
                    }
                });
    }

}
