package com.liyu.fakeweather.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import org.litepal.crud.DataSupport;

/**
 * Created by liyu on 2018/3/2.
 */

public class Module extends DataSupport implements Cloneable {

    private String name;
    private int index;
    private boolean enable;
    private int menuId;

    private int resIcon;

    public Module(String name, int resIcon, int menuId, int index, boolean enable) {
        this.name = name;
        this.index = index;
        this.enable = enable;
        this.resIcon = resIcon;
        this.menuId = menuId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (index != module.index) return false;
        if (enable != module.enable) return false;
        if (menuId != module.menuId) return false;
        if (resIcon != module.resIcon) return false;
        return name != null ? name.equals(module.name) : module.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + index;
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + menuId;
        result = 31 * result + resIcon;
        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
