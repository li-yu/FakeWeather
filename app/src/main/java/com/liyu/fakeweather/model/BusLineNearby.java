package com.liyu.fakeweather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/10/31.
 */

public class BusLineNearby implements Serializable {

    /**
     * SCode : RBK
     * SName : 园区工业技术学校
     * baidu_lat : 31.2721
     * baidu_lng : 120.746387
     * address : 松涛街东
     * sname_info : (165.9米，松涛街东)
     * long : 165.9
     * lines_info : 线路：118,120
     */

    private List<StationBean> station;
    /**
     * Guid : 52f67e87-dcb7-43ff-b632-e2a582c9f544
     * LName : 120路
     * LDirection : 车坊首末站-车坊首末站
     * vicinity_station_info : 园区工业技术学校，距离165.9米，位于松涛街东
     * long : 165.9
     * Distince : 8
     * Distince_str : 7站
     */

    private List<LineBean> line;

    public List<StationBean> getStation() {
        return station;
    }

    public void setStation(List<StationBean> station) {
        this.station = station;
    }

    public List<LineBean> getLine() {
        return line;
    }

    public void setLine(List<LineBean> line) {
        this.line = line;
    }

    public static class StationBean {
        private String SCode;
        private String SName;
        private String baidu_lat;
        private String baidu_lng;
        private String address;
        private String sname_info;
        @SerializedName("long")
        private double longX;
        private String lines_info;

        public String getSCode() {
            return SCode;
        }

        public void setSCode(String SCode) {
            this.SCode = SCode;
        }

        public String getSName() {
            return SName;
        }

        public void setSName(String SName) {
            this.SName = SName;
        }

        public String getBaidu_lat() {
            return baidu_lat;
        }

        public void setBaidu_lat(String baidu_lat) {
            this.baidu_lat = baidu_lat;
        }

        public String getBaidu_lng() {
            return baidu_lng;
        }

        public void setBaidu_lng(String baidu_lng) {
            this.baidu_lng = baidu_lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSname_info() {
            return sname_info;
        }

        public void setSname_info(String sname_info) {
            this.sname_info = sname_info;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getLines_info() {
            return lines_info;
        }

        public void setLines_info(String lines_info) {
            this.lines_info = lines_info;
        }
    }

    public static class LineBean {
        private String Guid;
        private String LName;
        private String LDirection;
        private String vicinity_station_info;
        @SerializedName("long")
        private double longX;
        private String Distince;
        private String Distince_str;

        public String getGuid() {
            return Guid;
        }

        public void setGuid(String Guid) {
            this.Guid = Guid;
        }

        public String getLName() {
            return LName;
        }

        public void setLName(String LName) {
            this.LName = LName;
        }

        public String getLDirection() {
            return LDirection;
        }

        public void setLDirection(String LDirection) {
            this.LDirection = LDirection;
        }

        public String getVicinity_station_info() {
            return vicinity_station_info;
        }

        public void setVicinity_station_info(String vicinity_station_info) {
            this.vicinity_station_info = vicinity_station_info;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getDistince() {
            return Distince;
        }

        public void setDistince(String Distince) {
            this.Distince = Distince;
        }

        public String getDistince_str() {
            return Distince_str;
        }

        public void setDistince_str(String Distince_str) {
            this.Distince_str = Distince_str;
        }
    }
}
