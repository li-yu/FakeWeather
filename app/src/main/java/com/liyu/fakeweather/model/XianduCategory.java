package com.liyu.fakeweather.model;

import java.io.Serializable;

/**
 * Created by liyu on 2016/12/13.
 */

public class XianduCategory  implements Serializable{
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
