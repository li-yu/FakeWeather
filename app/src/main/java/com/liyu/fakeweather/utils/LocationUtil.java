package com.liyu.fakeweather.utils;

/**
 * Created by liyu on 2018/10/15.
 */
public class LocationUtil {

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }
}
