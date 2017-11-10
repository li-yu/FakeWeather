package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2017/11/9.
 */

public class SimpleItem {

    private String item;
    private boolean selected;

    public SimpleItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
