package com.liyu.suzhoubus.utils;

/**
 * Created by liyu on 2016/11/30.
 */

public class DeviceUtil {

    public static String getAllInfo() {
        return String.format("SDK:  %s\nModel:  %s\nManufacturer:  %s", getSDKVersion(), getModel(), getManufacturer());
    }

    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    public static String getModel() {
        String model = android.os.Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }
}
