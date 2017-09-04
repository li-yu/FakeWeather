package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2017/8/28.
 */

public class AqiDetailBean {

    private String name;
    private String desc;
    private String value;

    public AqiDetailBean(String name, String desc, String value) {
        this.name = name;
        this.desc = desc;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
