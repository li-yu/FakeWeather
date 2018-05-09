package com.liyu.fakeweather.model;

import java.util.List;

public class HeWeatherAir {

    /**
     * basic : {"cid":"CN101190401","location":"苏州","parent_city":"苏州","admin_area":"江苏","cnty":"中国","lat":"31.29937935","lon":"120.61958313","tz":"+8.00"}
     * update : {"loc":"2018-05-09 09:47","utc":"2018-05-09 01:47"}
     * status : ok
     * air_now_city : {"aqi":"71","qlty":"良","main":"PM2.5","pm25":"51","pm10":"77","no2":"51","so2":"9","co":"0.73","o3":"75","pub_time":"2018-05-09 08:00"}
     * air_now_station : [{"air_sta":"上方山","aqi":"79","asid":"CNA1160","co":"0.4","lat":"31.2472","lon":"120.561","main":"PM2.5","no2":"48","o3":"65","pm10":"103","pm25":"58","pub_time":"2018-05-09 08:00","qlty":"良","so2":"10"},{"air_sta":"南门","aqi":"75","asid":"CNA1161","co":"0.5","lat":"31.2864","lon":"120.628","main":"PM2.5","no2":"44","o3":"77","pm10":"59","pm25":"55","pub_time":"2018-05-09 08:00","qlty":"良","so2":"14"},{"air_sta":"彩香","aqi":"68","asid":"CNA1162","co":"0.9","lat":"31.3019","lon":"120.591","main":"PM2.5","no2":"57","o3":"79","pm10":"68","pm25":"49","pub_time":"2018-05-09 08:00","qlty":"良","so2":"12"},{"air_sta":"轧钢厂","aqi":"69","asid":"CNA1163","co":"0.5","lat":"31.3264","lon":"120.596","main":"PM2.5","no2":"52","o3":"71","pm10":"62","pm25":"50","pub_time":"2018-05-09 08:00","qlty":"良","so2":"13"},{"air_sta":"吴中区","aqi":"67","asid":"CNA1164","co":"0.5","lat":"31.2703","lon":"120.613","main":"PM2.5","no2":"58","o3":"71","pm10":"68","pm25":"48","pub_time":"2018-05-09 08:00","qlty":"良","so2":"6"},{"air_sta":"苏州新区","aqi":"80","asid":"CNA1165","co":"0.6","lat":"31.2994","lon":"120.543","main":"PM2.5","no2":"68","o3":"63","pm10":"104","pm25":"59","pub_time":"2018-05-09 08:00","qlty":"良","so2":"7"},{"air_sta":"苏州工业园区","aqi":"64","asid":"CNA1166","co":"0.5","lat":"31.3097","lon":"120.669","main":"PM10","no2":"42","o3":"87","pm10":"78","pm25":"45","pub_time":"2018-05-09 08:00","qlty":"良","so2":"8"},{"air_sta":"相城区","aqi":"70","asid":"CNA1167","co":"1.9","lat":"31.3708","lon":"120.641","main":"PM2.5","no2":"44","o3":"91","pm10":"0","pm25":"51","pub_time":"2018-05-09 08:00","qlty":"良","so2":"7"}]
     */

    private BasicBean basic;
    private UpdateBean update;
    private String status;
    private AirNowCityBean air_now_city;
    private List<AirNowStationBean> air_now_station;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AirNowCityBean getAir_now_city() {
        return air_now_city;
    }

    public void setAir_now_city(AirNowCityBean air_now_city) {
        this.air_now_city = air_now_city;
    }

    public List<AirNowStationBean> getAir_now_station() {
        return air_now_station;
    }

    public void setAir_now_station(List<AirNowStationBean> air_now_station) {
        this.air_now_station = air_now_station;
    }

    public static class BasicBean {
        /**
         * cid : CN101190401
         * location : 苏州
         * parent_city : 苏州
         * admin_area : 江苏
         * cnty : 中国
         * lat : 31.29937935
         * lon : 120.61958313
         * tz : +8.00
         */

        private String cid;
        private String location;
        private String parent_city;
        private String admin_area;
        private String cnty;
        private String lat;
        private String lon;
        private String tz;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }
    }

    public static class UpdateBean {
        /**
         * loc : 2018-05-09 09:47
         * utc : 2018-05-09 01:47
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }

    public static class AirNowCityBean {
        /**
         * aqi : 71
         * qlty : 良
         * main : PM2.5
         * pm25 : 51
         * pm10 : 77
         * no2 : 51
         * so2 : 9
         * co : 0.73
         * o3 : 75
         * pub_time : 2018-05-09 08:00
         */

        private String aqi;
        private String qlty;
        private String main;
        private String pm25;
        private String pm10;
        private String no2;
        private String so2;
        private String co;
        private String o3;
        private String pub_time;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }
    }

    public static class AirNowStationBean {
        /**
         * air_sta : 上方山
         * aqi : 79
         * asid : CNA1160
         * co : 0.4
         * lat : 31.2472
         * lon : 120.561
         * main : PM2.5
         * no2 : 48
         * o3 : 65
         * pm10 : 103
         * pm25 : 58
         * pub_time : 2018-05-09 08:00
         * qlty : 良
         * so2 : 10
         */

        private String air_sta;
        private String aqi;
        private String asid;
        private String co;
        private String lat;
        private String lon;
        private String main;
        private String no2;
        private String o3;
        private String pm10;
        private String pm25;
        private String pub_time;
        private String qlty;
        private String so2;

        public String getAir_sta() {
            return air_sta;
        }

        public void setAir_sta(String air_sta) {
            this.air_sta = air_sta;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getAsid() {
            return asid;
        }

        public void setAsid(String asid) {
            this.asid = asid;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }
    }
}
