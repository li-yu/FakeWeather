package com.liyu.suzhoubus.http.api;

import com.liyu.suzhoubus.http.BaseGankResponse;
import com.liyu.suzhoubus.http.BaseJiandanResponse;
import com.liyu.suzhoubus.model.Gank;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 干货查询的接口
 * Created by liyu on 2016/10/31.
 */

public interface GankController {

    @GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/{page}")
    Observable<BaseGankResponse<List<Gank>>> getGank(@Path("page") String page);

    @GET("http://i.jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments")
    Observable<BaseJiandanResponse> getXXOO(@Query("page") int page);
}
