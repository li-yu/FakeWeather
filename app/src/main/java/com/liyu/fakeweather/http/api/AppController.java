package com.liyu.fakeweather.http.api;

import com.liyu.fakeweather.http.BaseAppResponse;
import com.liyu.fakeweather.model.UpdateInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liyu on 2016/12/1.
 */

public interface AppController {

    @GET("http://api.caoliyu.cn/appupdate.json")
    Observable<BaseAppResponse<UpdateInfo>> checkUpdate();
}
