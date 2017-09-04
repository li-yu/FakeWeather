package com.liyu.fakeweather.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by liyu on 2017/8/31.
 */

public class WeatherCityBean extends DataSupport {

    /**
     * ID : 2
     * cityName : 北京
     * cityEN : Beijing
     * townID : CHBJ000100
     * townName : 海淀
     * townEN : Haidian
     */

    @SerializedName("ID")
    private String cityID;
    private String cityName;
    private String cityEN;
    private String townID;
    private String townName;
    private String townEN;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityEN() {
        return cityEN;
    }

    public void setCityEN(String cityEN) {
        this.cityEN = cityEN;
    }

    public String getTownID() {
        return townID;
    }

    public void setTownID(String townID) {
        this.townID = townID;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getTownEN() {
        return townEN;
    }

    public void setTownEN(String townEN) {
        this.townEN = townEN;
    }
}
