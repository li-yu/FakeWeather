package com.liyu.fakeweather.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by liyu on 2016/11/14.
 */

public class StandInfoBean extends DataSupport implements Serializable {
    private long id;
    private String SName;
    private String SCode;
    private String InTime;
    private int is_vicinity;
    private int s_num;
    private String s_num_str;
    private BusLineDetail busLineDetail;

    public BusLineDetail getBusLineDetail() {
        return busLineDetail;
    }

    public void setBusLineDetail(BusLineDetail busLineDetail) {
        this.busLineDetail = busLineDetail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getSCode() {
        return SCode;
    }

    public void setSCode(String SCode) {
        this.SCode = SCode;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String InTime) {
        this.InTime = InTime;
    }

    public int getIs_vicinity() {
        return is_vicinity;
    }

    public void setIs_vicinity(int is_vicinity) {
        this.is_vicinity = is_vicinity;
    }

    public int getS_num() {
        return s_num;
    }

    public void setS_num(int s_num) {
        this.s_num = s_num;
    }

    public String getS_num_str() {
        return s_num_str;
    }

    public void setS_num_str(String s_num_str) {
        this.s_num_str = s_num_str;
    }
}
