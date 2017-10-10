package com.liyu.fakeweather.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by liyu on 2017/9/4.
 */

public class City extends DataSupport {

    /**
     * _id : 5
     * province_id : 0
     * name : 北京.怀柔
     * city_num : 101010500
     */

    @SerializedName("_id")
    private String city_id;
    private String province_id;
    private String name;
    private String city_num;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

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

    public String getCity_num() {
        return city_num;
    }

    public void setCity_num(String city_num) {
        this.city_num = city_num;
    }
}
