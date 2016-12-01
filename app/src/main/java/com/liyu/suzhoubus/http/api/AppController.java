package com.liyu.suzhoubus.http.api;

import com.liyu.suzhoubus.http.BaseAppResponse;
import com.liyu.suzhoubus.model.UpdateInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liyu on 2016/12/1.
 */

public interface AppController {

    @GET("http://api.caoliyu.cn/appupdate.json")
    Observable<BaseAppResponse<UpdateInfo>> checkUpdate();
}
