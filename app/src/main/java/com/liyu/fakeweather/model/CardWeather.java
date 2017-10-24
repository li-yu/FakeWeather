package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2017/10/18.
 */

public class CardWeather {

    private String city;
    private String code;
    private String text;
    private String temp;

    public CardWeather(String city, String code, String text, String temp) {
        this.city = city;
        this.code = code;
        this.text = text;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
