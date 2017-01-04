package com.liyu.fakeweather.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;

/**
 * Created by liyu on 2016/11/2.
 */

public class LocationClient {

    private com.baidu.location.LocationClient realClient;

    private static volatile LocationClient proxyClient;

    private LocationClient(Context context) {
        realClient = new com.baidu.location.LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        //设置百度定位参数
        realClient.setLocOption(option);
    }

    public static LocationClient get(Context context) {
        if (proxyClient == null) {
            synchronized (com.baidu.location.LocationClient.class) {
                if (proxyClient == null) {
                    proxyClient = new LocationClient(context.getApplicationContext());
                }
            }
        }
        return proxyClient;
    }

    public void locate(final BDLocationListener bdLocationListener) {
        final BDLocationListener realListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                bdLocationListener.onReceiveLocation(bdLocation);
                //防止内存溢出
                realClient.unRegisterLocationListener(this);
                stop();
            }
        };
        realClient.registerLocationListener(realListener);
        if (!realClient.isStarted()) {
            realClient.start();
        }
    }

    public BDLocation getLastKnownLocation() {
        return realClient.getLastKnownLocation();
    }

    public void stop() {
        realClient.stop();
    }
}
