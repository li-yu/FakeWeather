package com.liyu.suzhoubus.event;

import com.liyu.suzhoubus.model.Gank;
import com.liyu.suzhoubus.model.JiandanXXOO;

import java.util.List;

/**
 * Created by yuyu on 2016/12/8.
 */

public class JiandanEvent {

    private List<JiandanXXOO> xxoos;

    public JiandanEvent(List<JiandanXXOO> xxoos){
        this.xxoos =xxoos;
    }

    public List<JiandanXXOO> getXxoos() {
        return xxoos;
    }

    public void setXxoos(List<JiandanXXOO> xxoos) {
        this.xxoos = xxoos;
    }
}
