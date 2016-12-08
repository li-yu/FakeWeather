package com.liyu.suzhoubus.event;

import com.liyu.suzhoubus.model.Gank;

import java.util.List;

/**
 * Created by yuyu on 2016/12/8.
 */

public class GankEvent {

    private List<Gank> ganks;

    public GankEvent(List<Gank> ganks){
        this.ganks =ganks;
    }

    public List<Gank> getGanks() {
        return ganks;
    }

    public void setGanks(List<Gank> ganks) {
        this.ganks = ganks;
    }
}
