package com.liyu.fakeweather.event;

import com.liyu.fakeweather.model.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyu on 2016/12/8.
 */

public class GirlsComingEvent {

    private List<Girl> girls;
    private int from;

    private String fromName;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public GirlsComingEvent(String from, List<Girl> girls) {
        this.girls = girls;
        this.fromName = from;
    }

    public GirlsComingEvent(String from, Girl girl) {
        this.girls = new ArrayList<>();
        girls.add(girl);
        this.fromName = from;
    }

    public List<Girl> getGirls() {
        return girls;
    }

    public void setGirls(List<Girl> girls) {
        this.girls = girls;
    }
}
