package com.liyu.suzhoubus.model;

import java.io.Serializable;

/**
 * Created by liyu on 2016/12/9.
 */

public class Girl implements Serializable {
    private String url;
    private int width;
    private int height;

    public Girl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
