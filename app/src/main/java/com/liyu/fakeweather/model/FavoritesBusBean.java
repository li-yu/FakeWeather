package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2016/11/29.
 */

public class FavoritesBusBean {
    private String LGUID;
    private String LName;
    private String LDirection;
    private String SCode;
    private String SName;
    private long id;

    public FavoritesBusBean(BusLineDetail line) {
        LGUID = line.getLGUID();
        LName = line.getLName();
        LDirection = line.getLDirection();
        id = line.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FavoritesBusBean() {
    }

    public String getLDirection() {
        return LDirection;
    }

    public void setLDirection(String LDirection) {
        this.LDirection = LDirection;
    }

    public String getLGUID() {
        return LGUID;
    }

    public void setLGUID(String LGUID) {
        this.LGUID = LGUID;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

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

    @Override
    public boolean equals(Object obj) {
        return LGUID.equals(((FavoritesBusBean) obj).getLGUID());
    }
}
