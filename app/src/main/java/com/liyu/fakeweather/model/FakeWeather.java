package com.liyu.fakeweather.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringDef;

import com.liyu.fakeweather.R;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by liyu on 2017/9/1.
 */

public class FakeWeather {

    public static class FakeBasic implements Serializable {

        private String cityName;
        private String cityId;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

    }

    public static class FakeNow implements Serializable {

        private String nowTemp;
        private String nowHum;
        private String nowPres;
        private String nowCode;
        private String nowText;
        private String nowWindDir;
        private String nowWindSpeed;
        private String nowWindSc;
        private String updateTime;

        public String getNowHum() {
            return nowHum;
        }

        public void setNowHum(String nowHum) {
            this.nowHum = nowHum;
        }

        public String getNowPres() {
            return nowPres;
        }

        public void setNowPres(String nowPres) {
            this.nowPres = nowPres;
        }

        public String getNowWindSc() {
            return nowWindSc;
        }

        public void setNowWindSc(String nowWindSc) {
            this.nowWindSc = nowWindSc;
        }

        public String getNowTemp() {
            return nowTemp;
        }

        public void setNowTemp(String nowTemp) {
            this.nowTemp = nowTemp;
        }

        public String getNowCode() {
            return nowCode;
        }

        public void setNowCode(String nowCode) {
            this.nowCode = nowCode;
        }

        public String getNowText() {
            return nowText;
        }

        public void setNowText(String nowText) {
            this.nowText = nowText;
        }

        public String getNowWindDir() {
            return nowWindDir;
        }

        public void setNowWindDir(String nowWindDir) {
            this.nowWindDir = nowWindDir;
        }

        public String getNowWindSpeed() {
            return nowWindSpeed;
        }

        public void setNowWindSpeed(String nowWindSpeed) {
            this.nowWindSpeed = nowWindSpeed;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class FakeAqi implements Serializable {
        private String api;
        private String co;
        private String no2;
        private String o3;
        private String pm10;
        private String pm25;
        private String qlty;
        private String so2;

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }
    }

    public static class FakeForecastHourly implements Serializable {

        private String temp;
        private String time;
        private String code;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class FakeForecastDaily implements Serializable {
        private String date;
        private String txt;
        private String code;
        private String maxTemp;
        private String minTemp;
        private String sunRise;
        private String sunSet;
        private String moonRise;
        private String moonSet;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(String maxTemp) {
            this.maxTemp = maxTemp;
        }

        public String getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(String minTemp) {
            this.minTemp = minTemp;
        }

        public String getSunRise() {
            return sunRise;
        }

        public void setSunRise(String sunRise) {
            this.sunRise = sunRise;
        }

        public String getSunSet() {
            return sunSet;
        }

        public void setSunSet(String sunSet) {
            this.sunSet = sunSet;
        }

        public String getMoonRise() {
            return moonRise;
        }

        public void setMoonRise(String moonRise) {
            this.moonRise = moonRise;
        }

        public String getMoonSet() {
            return moonSet;
        }

        public void setMoonSet(String moonSet) {
            this.moonSet = moonSet;
        }
    }

    public static class FakeSuggestion implements Serializable {
        private String title;
        private String msg;
        private
        @DrawableRes
        int icon;
        private int iconBackgroudColor;

        public String getTitle() {
            return title;
        }

        public void setTitle(@SuggestionType String title) {
            this.title = title;
            switch (title) {
                case 空气:
                    setIcon(R.drawable.ic_air);
                    setIconBackgroudColor(0xFF7F9EE9);
                    break;
                case 舒适度:
                    setIcon(R.drawable.ic_comf);
                    setIconBackgroudColor(0xFFE99E3C);
                    break;
                case 洗车:
                    setIcon(R.drawable.ic_cw);
                    setIconBackgroudColor(0xFF62B1FF);
                    break;
                case 穿衣:
                    setIcon(R.drawable.ic_drsg);
                    setIconBackgroudColor(0xFF8FC55F);
                    break;
                case 感冒:
                    setIcon(R.drawable.ic_flu);
                    setIconBackgroudColor(0xFFF98178);
                    break;
                case 运动:
                    setIcon(R.drawable.ic_sport);
                    setIconBackgroudColor(0xFFB3CA60);
                    break;
                case 旅游:
                    setIcon(R.drawable.ic_trav);
                    setIconBackgroudColor(0xFFFD6C35);
                    break;
                case 紫外线:
                    setIcon(R.drawable.ic_uv);
                    setIconBackgroudColor(0xFFF0AB2A);
                    break;
                default:
                    setIcon(R.drawable.ic_air);
                    setIconBackgroudColor(0xFF7F9EE9);


            }
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getIconBackgroudColor() {
            return iconBackgroudColor;
        }

        public void setIconBackgroudColor(int iconBackgroudColor) {
            this.iconBackgroudColor = iconBackgroudColor;
        }
    }

    public static final String 空气 = "空气";
    public static final String 舒适度 = "舒适度";
    public static final String 洗车 = "洗车";
    public static final String 穿衣 = "穿衣";
    public static final String 感冒 = "感冒";
    public static final String 运动 = "运动";
    public static final String 旅游 = "旅游";
    public static final String 紫外线 = "紫外线";

    @StringDef({空气, 舒适度, 洗车, 穿衣, 感冒, 运动, 旅游, 紫外线})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggestionType {
    }


    public static final int[] SUGGESTION_COLORS = new int[]{
            0xFF7F9EE9,
            0xFFE99E3C,
            0xFF62B1FF,
            0xFF8FC55F,
            0xFFF98178,
            0xFFB3CA60,
            0xFFFD6C35,
            0xFFF0AB2A};

}
