package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2016/11/11.
 */

public class WeatherBean {


    /**
     * code : 100
     * cname : 晴
     * ename : Sunny/Clear
     * icon : http://files.heweather.com/cond_icon/100.png
     */

    private String code;
    private String cname;
    private String ename;
    private String icon;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
