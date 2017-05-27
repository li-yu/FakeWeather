package com.liyu.fakeweather.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by liyu on 2016/11/2.
 */

public class RxLocation {
    private static RxLocation instance = new RxLocation();

    private RxLocation() {
    }

    public static RxLocation get() {
        return instance;
    }

    public Observable<BDLocation> locate(final Activity context) {
        return new RxPermissions(context).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE).flatMap(new Func1<Boolean, Observable<BDLocation>>() {
            @Override
            public Observable<BDLocation> call(Boolean aBoolean) {
                return Observable.unsafeCreate(new LocationOnSubscribe(context));
            }
        });
    }

    public Observable<BDLocation> locateLastKnown(Context context) {
        return Observable.unsafeCreate(new LocationLastKnownOnSubscribe(context));
    }

}
