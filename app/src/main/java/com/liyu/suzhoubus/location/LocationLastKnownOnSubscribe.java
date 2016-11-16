package com.liyu.suzhoubus.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liyu on 2016/11/2.
 */

public class LocationLastKnownOnSubscribe implements Observable.OnSubscribe<BDLocation> {

    private final Context context;

    public LocationLastKnownOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void call(final Subscriber<? super BDLocation> subscriber) {
        BDLocation lateKnownLocation = LocationClient.get(context).getLastKnownLocation();
        if (lateKnownLocation != null) {
            subscriber.onNext(lateKnownLocation);
            subscriber.onCompleted();
        } else {
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
}
