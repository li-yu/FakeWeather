package com.liyu.fakeweather.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by liyu on 2017/9/4.
 */

public class Province extends DataSupport {

    /**
     * _id : 1
     * name : 北京
     */

    @SerializedName("_id")
    private String province_id;
    private String name;

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
