package com.liyu.suzhoubus.model;

/**
 * Created by liyu on 2016/12/1.
 */

public class UpdateInfo {


    /**
     * app : 就看苏州
     * versionCode : 4
     * versionName : 1.1
     * updateTime : 2016-11-11 18:00:00
     * information : 新增多语言设置
     * foreUpdate : false
     * url : http://7xp1a1.com1.z0.glb.clouddn.com/app-debug.apk
     */

    private String app;
    private int versionCode;
    private String versionName;
    private String updateTime;
    private String information;
    private boolean foreUpdate;
    private String url;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean isForeUpdate() {
        return foreUpdate;
    }

    public void setForeUpdate(boolean foreUpdate) {
        this.foreUpdate = foreUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
