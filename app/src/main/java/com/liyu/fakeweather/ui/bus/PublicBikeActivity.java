package com.liyu.fakeweather.ui.bus;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baidu.location.BDLocation;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.location.RxLocation;
import com.liyu.fakeweather.ui.base.BaseActivity;

import java.text.DecimalFormat;

import rx.Observer;

/**
 * Created by liyu on 2018/10/12.
 */
public class PublicBikeActivity extends BaseActivity {

    private WebView webView;

    DecimalFormat df = new DecimalFormat("#.000000");

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_bike;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        webView = findView(R.id.webBike);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    toolbar.setTitle(title);
                }
            }
        });

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

                    }

                    @Override
                    public void onNext(BDLocation bdLocation) {
                        double[] loc =
                                bd09_To_Gcj02(bdLocation.getLatitude(), bdLocation.getLongitude());
                        String url = String.format("http://ws.uibike.com/map.php?lnglat=%s,%s", df.format(loc[1]), df.format(loc[0]));
                        webView.loadUrl(url);
                    }
                });
    }

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    private double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }
}
