package com.liyu.fakeweather.model;

import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HeWeather implements Serializable, Cloneable, IFakeWeather {

    /**
     * basic : {"cid":"CN101190401","location":"苏州","parent_city":"苏州","admin_area":"江苏","cnty":"中国","lat":"31.29937935","lon":"120.61958313","tz":"+8.00"}
     * update : {"loc":"2018-05-09 08:47","utc":"2018-05-09 00:47"}
     * status : ok
     * now : {"cloud":"5","cond_code":"100","cond_txt":"晴","fl":"17","hum":"70","pcpn":"0.0","pres":"1018","tmp":"17","vis":"20","wind_deg":"16","wind_dir":"东北风","wind_sc":"2","wind_spd":"6"}
     * daily_forecast : [{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2018-05-09","hum":"79","mr":"01:20","ms":"12:30","pcpn":"0.0","pop":"0","pres":"1020","sr":"05:06","ss":"18:42","tmp_max":"25","tmp_min":"15","uv_index":"10","vis":"20","wind_deg":"25","wind_dir":"东北风","wind_sc":"1-2","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-05-10","hum":"72","mr":"01:56","ms":"13:26","pcpn":"0.0","pop":"0","pres":"1022","sr":"05:06","ss":"18:43","tmp_max":"25","tmp_min":"16","uv_index":"10","vis":"20","wind_deg":"139","wind_dir":"东南风","wind_sc":"3-4","wind_spd":"14"},{"cond_code_d":"101","cond_code_n":"104","cond_txt_d":"多云","cond_txt_n":"阴","date":"2018-05-11","hum":"79","mr":"02:30","ms":"14:22","pcpn":"0.0","pop":"0","pres":"1017","sr":"05:05","ss":"18:43","tmp_max":"27","tmp_min":"21","uv_index":"6","vis":"20","wind_deg":"107","wind_dir":"东南风","wind_sc":"3-4","wind_spd":"18"},{"cond_code_d":"300","cond_code_n":"300","cond_txt_d":"阵雨","cond_txt_n":"阵雨","date":"2018-05-12","hum":"78","mr":"03:05","ms":"15:21","pcpn":"0.0","pop":"0","pres":"1011","sr":"05:04","ss":"18:44","tmp_max":"26","tmp_min":"22","uv_index":"5","vis":"19","wind_deg":"155","wind_dir":"东南风","wind_sc":"4-5","wind_spd":"30"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-05-13","hum":"82","mr":"03:41","ms":"16:21","pcpn":"4.1","pop":"70","pres":"1005","sr":"05:03","ss":"18:45","tmp_max":"30","tmp_min":"22","uv_index":"5","vis":"7","wind_deg":"200","wind_dir":"西南风","wind_sc":"3-4","wind_spd":"22"},{"cond_code_d":"104","cond_code_n":"104","cond_txt_d":"阴","cond_txt_n":"阴","date":"2018-05-14","hum":"77","mr":"04:17","ms":"17:25","pcpn":"0.0","pop":"0","pres":"1007","sr":"05:03","ss":"18:45","tmp_max":"32","tmp_min":"25","uv_index":"7","vis":"10","wind_deg":"123","wind_dir":"东南风","wind_sc":"4-5","wind_spd":"25"},{"cond_code_d":"300","cond_code_n":"300","cond_txt_d":"阵雨","cond_txt_n":"阵雨","date":"2018-05-15","hum":"74","mr":"04:58","ms":"18:30","pcpn":"0.0","pop":"0","pres":"1005","sr":"05:02","ss":"18:46","tmp_max":"32","tmp_min":"25","uv_index":"7","vis":"10","wind_deg":"172","wind_dir":"南风","wind_sc":"4-5","wind_spd":"34"}]
     * hourly : [{"cloud":"55","cond_code":"100","cond_txt":"晴","dew":"12","hum":"66","pop":"0","pres":"1021","time":"2018-05-09 10:00","tmp":"21","wind_deg":"71","wind_dir":"东北风","wind_sc":"1-2","wind_spd":"2"},{"cloud":"31","cond_code":"100","cond_txt":"晴","dew":"9","hum":"51","pop":"0","pres":"1021","time":"2018-05-09 13:00","tmp":"23","wind_deg":"81","wind_dir":"东风","wind_sc":"1-2","wind_spd":"3"},{"cloud":"23","cond_code":"100","cond_txt":"晴","dew":"10","hum":"59","pop":"0","pres":"1020","time":"2018-05-09 16:00","tmp":"23","wind_deg":"93","wind_dir":"东风","wind_sc":"1-2","wind_spd":"9"},{"cloud":"7","cond_code":"100","cond_txt":"晴","dew":"10","hum":"75","pop":"0","pres":"1021","time":"2018-05-09 19:00","tmp":"19","wind_deg":"81","wind_dir":"东风","wind_sc":"1-2","wind_spd":"8"},{"cloud":"1","cond_code":"100","cond_txt":"晴","dew":"10","hum":"87","pop":"0","pres":"1023","time":"2018-05-09 22:00","tmp":"17","wind_deg":"92","wind_dir":"东风","wind_sc":"1-2","wind_spd":"8"},{"cloud":"47","cond_code":"100","cond_txt":"晴","dew":"9","hum":"93","pop":"0","pres":"1023","time":"2018-05-10 01:00","tmp":"16","wind_deg":"93","wind_dir":"东风","wind_sc":"1-2","wind_spd":"11"},{"cloud":"88","cond_code":"100","cond_txt":"晴","dew":"9","hum":"95","pop":"0","pres":"1022","time":"2018-05-10 04:00","tmp":"15","wind_deg":"79","wind_dir":"东风","wind_sc":"1-2","wind_spd":"7"},{"cloud":"94","cond_code":"100","cond_txt":"晴","dew":"10","hum":"82","pop":"0","pres":"1022","time":"2018-05-10 07:00","tmp":"18","wind_deg":"99","wind_dir":"东风","wind_sc":"1-2","wind_spd":"2"}]
     * lifestyle : [{"type":"comf","brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},{"type":"drsg","brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"type":"flu","brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},{"type":"sport","brf":"较适宜","txt":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意防风。"},{"type":"trav","brf":"适宜","txt":"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。"},{"type":"uv","brf":"强","txt":"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。"},{"type":"cw","brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"type":"air","brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}]
     */

    private BasicBean basic;
    private UpdateBean update;
    private String status;
    private NowBean now;
    private List<DailyForecastBean> daily_forecast;
    private List<HourlyBean> hourly;
    private List<LifestyleBean> lifestyle;
    private FakeWeather.FakeAqi fakeAqi;

    public void setFakeAqi(FakeWeather.FakeAqi aqi) {
        this.fakeAqi = aqi;
    }

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

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyBean> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyBean> hourly) {
        this.hourly = hourly;
    }

    public List<LifestyleBean> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<LifestyleBean> lifestyle) {
        this.lifestyle = lifestyle;
    }

    @Override
    public FakeWeather.FakeBasic getFakeBasic() {
        FakeWeather.FakeBasic basic = new FakeWeather.FakeBasic();
        basic.setCityName(this.basic.getLocation());
        basic.setCityId(this.basic.getCid());
        return basic;
    }

    @Override
    public FakeWeather.FakeNow getFakeNow() {
        FakeWeather.FakeNow fakeNow = new FakeWeather.FakeNow();
        fakeNow.setNowCode(this.now.getCond_code());
        fakeNow.setNowText(this.now.getCond_txt());
        fakeNow.setNowHum(this.now.getHum());
        fakeNow.setNowPres(this.now.getPres());
        fakeNow.setNowTemp(this.now.getTmp());
        fakeNow.setNowWindDir(this.now.getWind_dir());
        fakeNow.setNowWindSc(this.now.getWind_sc());
        fakeNow.setNowWindSpeed(this.now.wind_spd);
        fakeNow.setUpdateTime(this.update.getLoc());
        return fakeNow;
    }

    @Override
    public FakeWeather.FakeAqi getFakeAqi() {
        return this.fakeAqi;
    }

    @Override
    public List<FakeWeather.FakeForecastDaily> getFakeForecastDaily() {
        List<FakeWeather.FakeForecastDaily> dailyList = new ArrayList<>();
        for (HeWeather.DailyForecastBean item : daily_forecast) {
            FakeWeather.FakeForecastDaily daily = new FakeWeather.FakeForecastDaily();
            daily.setCode(item.getCond_code_d());
            daily.setMaxTemp(item.getTmp_max());
            daily.setMinTemp(item.getTmp_min());
            daily.setTxt(item.getCond_txt_d());
            daily.setSunRise(item.getSr());
            daily.setSunSet(item.getSs());
            daily.setMoonRise(item.getMr());
            daily.setMoonSet(item.getMs());
            if (SettingsUtil.getWeatherDateType() == SettingsUtil.WEATHER_DATE_TYPE_WEEK) {
                daily.setDate(TimeUtils.getWeek(item.getDate(), TimeUtils.DATE_SDF));
            } else {
                daily.setDate(TimeUtils.string2String(item.getDate(), TimeUtils.DATE_SDF, TimeUtils.DATE_NO_YEAR_SDF));
            }
            dailyList.add(daily);
        }
        return dailyList;
    }

    @Override
    public List<FakeWeather.FakeForecastHourly> getFakeForecastHourly() {
        List<FakeWeather.FakeForecastHourly> hourlyList = new ArrayList<>();
        if (hourly == null)
            return hourlyList;
        for (HeWeather.HourlyBean item : hourly) {
            FakeWeather.FakeForecastHourly hourly = new FakeWeather.FakeForecastHourly();
            hourly.setCode(item.getCond_code());
            hourly.setTemp(item.getTmp());
            hourly.setTime(TimeUtils.string2String(item.time, TimeUtils.HOURLY_FORECAST_SDF, TimeUtils.HOUR_SDF));
            hourlyList.add(hourly);
        }
        return hourlyList;
    }

    @Override
    public List<FakeWeather.FakeSuggestion> getFakeSuggestion() {
        List<FakeWeather.FakeSuggestion> suggestionList = new ArrayList<>();
        FakeWeather.FakeSuggestion air = new FakeWeather.FakeSuggestion();
        air.setTitle(FakeWeather.空气);
        air.setMsg(getLifeStyleBrf("air"));
        suggestionList.add(air);

        FakeWeather.FakeSuggestion comf = new FakeWeather.FakeSuggestion();
        comf.setTitle(FakeWeather.舒适度);
        comf.setMsg(getLifeStyleBrf("comf"));
        suggestionList.add(comf);

        FakeWeather.FakeSuggestion carWash = new FakeWeather.FakeSuggestion();
        carWash.setTitle(FakeWeather.洗车);
        carWash.setMsg(getLifeStyleBrf("cw"));
        suggestionList.add(carWash);

        FakeWeather.FakeSuggestion drsg = new FakeWeather.FakeSuggestion();
        drsg.setTitle(FakeWeather.穿衣);
        drsg.setMsg(getLifeStyleBrf("drsg"));
        suggestionList.add(drsg);

        FakeWeather.FakeSuggestion flu = new FakeWeather.FakeSuggestion();
        flu.setTitle(FakeWeather.感冒);
        flu.setMsg(getLifeStyleBrf("flu"));
        suggestionList.add(flu);

        FakeWeather.FakeSuggestion sport = new FakeWeather.FakeSuggestion();
        sport.setTitle(FakeWeather.运动);
        sport.setMsg(getLifeStyleBrf("sport"));
        suggestionList.add(sport);

        FakeWeather.FakeSuggestion trav = new FakeWeather.FakeSuggestion();
        trav.setTitle(FakeWeather.旅游);
        trav.setMsg(getLifeStyleBrf("trav"));
        suggestionList.add(trav);

        FakeWeather.FakeSuggestion uv = new FakeWeather.FakeSuggestion();
        uv.setTitle(FakeWeather.紫外线);
        uv.setMsg(getLifeStyleBrf("uv"));
        suggestionList.add(uv);

        return suggestionList;
    }

    private String getLifeStyleBrf(String type) {
        if (lifestyle == null)
            return "未知";
        for (LifestyleBean bean : lifestyle) {
            if (type.equals(bean.getType())) {
                return bean.getBrf();
            }
        }
        return "未知";
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
         * loc : 2018-05-09 08:47
         * utc : 2018-05-09 00:47
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

    public static class NowBean {
        /**
         * cloud : 5
         * cond_code : 100
         * cond_txt : 晴
         * fl : 17
         * hum : 70
         * pcpn : 0.0
         * pres : 1018
         * tmp : 17
         * vis : 20
         * wind_deg : 16
         * wind_dir : 东北风
         * wind_sc : 2
         * wind_spd : 6
         */

        private String cloud;
        private String cond_code;
        private String cond_txt;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class DailyForecastBean {
        /**
         * cond_code_d : 100
         * cond_code_n : 100
         * cond_txt_d : 晴
         * cond_txt_n : 晴
         * date : 2018-05-09
         * hum : 79
         * mr : 01:20
         * ms : 12:30
         * pcpn : 0.0
         * pop : 0
         * pres : 1020
         * sr : 05:06
         * ss : 18:42
         * tmp_max : 25
         * tmp_min : 15
         * uv_index : 10
         * vis : 20
         * wind_deg : 25
         * wind_dir : 东北风
         * wind_sc : 1-2
         * wind_spd : 5
         */

        private String cond_code_d;
        private String cond_code_n;
        private String cond_txt_d;
        private String cond_txt_n;
        private String date;
        private String hum;
        private String mr;
        private String ms;
        private String pcpn;
        private String pop;
        private String pres;
        private String sr;
        private String ss;
        private String tmp_max;
        private String tmp_min;
        private String uv_index;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCond_code_d() {
            return cond_code_d;
        }

        public void setCond_code_d(String cond_code_d) {
            this.cond_code_d = cond_code_d;
        }

        public String getCond_code_n() {
            return cond_code_n;
        }

        public void setCond_code_n(String cond_code_n) {
            this.cond_code_n = cond_code_n;
        }

        public String getCond_txt_d() {
            return cond_txt_d;
        }

        public void setCond_txt_d(String cond_txt_d) {
            this.cond_txt_d = cond_txt_d;
        }

        public String getCond_txt_n() {
            return cond_txt_n;
        }

        public void setCond_txt_n(String cond_txt_n) {
            this.cond_txt_n = cond_txt_n;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        public String getTmp_max() {
            return tmp_max;
        }

        public void setTmp_max(String tmp_max) {
            this.tmp_max = tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public void setTmp_min(String tmp_min) {
            this.tmp_min = tmp_min;
        }

        public String getUv_index() {
            return uv_index;
        }

        public void setUv_index(String uv_index) {
            this.uv_index = uv_index;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class HourlyBean {
        /**
         * cloud : 55
         * cond_code : 100
         * cond_txt : 晴
         * dew : 12
         * hum : 66
         * pop : 0
         * pres : 1021
         * time : 2018-05-09 10:00
         * tmp : 21
         * wind_deg : 71
         * wind_dir : 东北风
         * wind_sc : 1-2
         * wind_spd : 2
         */

        private String cloud;
        private String cond_code;
        private String cond_txt;
        private String dew;
        private String hum;
        private String pop;
        private String pres;
        private String time;
        private String tmp;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getDew() {
            return dew;
        }

        public void setDew(String dew) {
            this.dew = dew;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class LifestyleBean {
        /**
         * type : comf
         * brf : 舒适
         * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
         */

        private String type;
        private String brf;
        private String txt;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    @Override
    public Object clone() {
        HeWeather o = null;
        try {
            o = (HeWeather) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
