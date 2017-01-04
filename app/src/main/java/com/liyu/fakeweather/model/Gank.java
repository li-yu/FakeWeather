package com.liyu.fakeweather.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by liyu on 2016/11/2.
 */

public class Gank extends DataSupport implements Serializable {


    /**
     * _id : 58193781421aa90e6f21b49f
     * createdAt : 2016-11-02T08:46:57.726Z
     * desc : 11-2
     * publishedAt : 2016-11-02T11:49:08.835Z
     * source : chrome
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/610dc034jw1f9dh2ohx2vj20u011hn0r.jpg
     * used : true
     * who : daimajia
     */

    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private int girlHeight;
    private int girlWidth;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getGirlHeight() {
        return girlHeight;
    }

    public void setGirlHeight(int girlHeight) {
        this.girlHeight = girlHeight;
    }

    public int getGirlWidth() {
        return girlWidth;
    }

    public void setGirlWidth(int girlWidth) {
        this.girlWidth = girlWidth;
    }

    @Override
    public boolean equals(Object obj) {
        return this.url.equals(((Gank) obj).getUrl());
    }
}
