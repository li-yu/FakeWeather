package com.liyu.fakeweather.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liyu on 2016/11/2.
 */

public class LocationOnSubscribe implements Observable.OnSubscribe<BDLocation> {

    private final Context context;

    public LocationOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void call(final Subscriber<? super BDLocation> subscriber) {
        BDLocationListener bdLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                subscriber.onNext(bdLocation);
                subscriber.onCompleted();
            }
        };
        LocationClient.get(context).locate(bdLocationListener);
    }
}