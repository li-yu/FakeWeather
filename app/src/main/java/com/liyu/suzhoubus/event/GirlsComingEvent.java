package com.liyu.suzhoubus.event;

import com.liyu.suzhoubus.model.Girl;
import com.liyu.suzhoubus.model.JiandanXXOO;

import java.util.List;

/**
 * Created by yuyu on 2016/12/8.
 */

public class GirlsComingEvent {

    public static final int GIRLS_FROM_GANK = 0;
    public static final int GIRLS_FROM_JIANDAN = 1;
    public static final int GIRLS_FROM_MZITU = 2;
    public static final int GIRLS_FROM_MZITU_ZIPAI = 3;

    private List<Girl> girls;
    private int from;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public GirlsComingEvent(int from, List<Girl> girls) {
        this.girls = girls;
        this.from = from;
    }

    public List<Girl> getGirls() {
        return girls;
    }

    public void setGirls(List<Girl> girls) {
        this.girls = girls;
    }
}
