package com.liyu.fakeweather.location;

import android.support.annotation.NonNull;

import com.baidu.location.BDLocation;

import rx.Subscriber;

/**
 * Created by liyu on 2016/11/2.
 */

public abstract class LocationSubscriber extends Subscriber<BDLocation> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        onLocatedFail(null);
    }

    @Override
    public void onNext(BDLocation bdLocation) {
        if (bdLocation != null) {
            onLocatedSuccess(bdLocation);
        } else {
            onLocatedFail(bdLocation);
        }
    }

    public abstract void onLocatedSuccess(@NonNull BDLocation bdLocation);

    public abstract void onLocatedFail(BDLocation bdLocation);

}
