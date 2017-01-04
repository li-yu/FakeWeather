package com.liyu.fakeweather.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/11/10.
 */

public class HeWeather5 implements Serializable, Cloneable, MultiItemEntity {

    public static final int TYPE_NOW = 1;
    public static final int TYPE_DAILYFORECAST = 2;
    public static final int TYPE_SUGGESTION = 3;

    private int itemType = 0;

    /**
     * city : {"aqi":"48","co":"1","no2":"51","o3":"26","pm10":"47","pm25":"25","qlty":"优","so2":"24"}
     */

    private AqiBean aqi;
    /**
     * city : 苏州
     * cnty : 中国
     * id : CN101190401
     * lat : 31.309000
     * lon : 120.612000
     * update : {"loc":"2016-11-10 08:53","utc":"2016-11-10 00:53"}
     */

    private BasicBean basic;
    /**
     * cond : {"code":"101","txt":"多云"}
     * fl : 9
     * hum : 78
     * pcpn : 0
     * pres : 1025
     * tmp : 10
     * vis : 10
     * wind : {"deg":"346","dir":"西北风","sc":"4-5","spd":"18"}
     */

    private NowBean now;
    /**
     * aqi : {"city":{"aqi":"48","co":"1","no2":"51","o3":"26","pm10":"47","pm25":"25","qlty":"优","so2":"24"}}
     * basic : {"city":"苏州","cnty":"中国","id":"CN101190401","lat":"31.309000","lon":"120.612000","update":{"loc":"2016-11-10 08:53","utc":"2016-11-10 00:53"}}
     * daily_forecast : [{"astro":{"mr":"14:10","ms":"01:11","sr":"06:20","ss":"17:02"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-11-10","hum":"73","pcpn":"0.2","pop":"72","pres":"1024","tmp":{"max":"14","min":"7"},"uv":"5","vis":"10","wind":{"deg":"280","dir":"西风","sc":"3-4","spd":"11"}},{"astro":{"mr":"14:50","ms":"02:14","sr":"06:21","ss":"17:01"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2016-11-11","hum":"66","pcpn":"0.0","pop":"0","pres":"1022","tmp":{"max":"18","min":"9"},"uv":"4","vis":"10","wind":{"deg":"173","dir":"南风","sc":"3-4","spd":"16"}},{"astro":{"mr":"15:32","ms":"03:19","sr":"06:22","ss":"17:00"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-11-12","hum":"65","pcpn":"0.0","pop":"0","pres":"1020","tmp":{"max":"21","min":"13"},"uv":"4","vis":"10","wind":{"deg":"213","dir":"南风","sc":"微风","spd":"8"}},{"astro":{"mr":"16:16","ms":"04:26","sr":"06:23","ss":"17:00"},"cond":{"code_d":"305","code_n":"104","txt_d":"小雨","txt_n":"阴"},"date":"2016-11-13","hum":"67","pcpn":"3.5","pop":"65","pres":"1019","tmp":{"max":"21","min":"14"},"uv":"5","vis":"10","wind":{"deg":"175","dir":"南风","sc":"3-4","spd":"13"}},{"astro":{"mr":"17:04","ms":"05:36","sr":"06:24","ss":"16:59"},"cond":{"code_d":"101","code_n":"300","txt_d":"多云","txt_n":"阵雨"},"date":"2016-11-14","hum":"77","pcpn":"0.4","pop":"65","pres":"1020","tmp":{"max":"20","min":"15"},"uv":"-999","vis":"10","wind":{"deg":"354","dir":"东北风","sc":"3-4","spd":"13"}},{"astro":{"mr":"17:55","ms":"06:45","sr":"06:25","ss":"16:59"},"cond":{"code_d":"300","code_n":"104","txt_d":"阵雨","txt_n":"阴"},"date":"2016-11-15","hum":"78","pcpn":"2.9","pop":"67","pres":"1024","tmp":{"max":"19","min":"17"},"uv":"-999","vis":"10","wind":{"deg":"74","dir":"东风","sc":"4-5","spd":"24"}},{"astro":{"mr":"18:51","ms":"07:53","sr":"06:26","ss":"16:58"},"cond":{"code_d":"300","code_n":"305","txt_d":"阵雨","txt_n":"小雨"},"date":"2016-11-16","hum":"91","pcpn":"26.3","pop":"100","pres":"1023","tmp":{"max":"23","min":"10"},"uv":"-999","vis":"2","wind":{"deg":"74","dir":"东南风","sc":"3-4","spd":"10"}}]
     * hourly_forecast : [{"cond":{"code":"305","txt":"小雨"},"date":"2016-11-10 10:00","hum":"69","pop":"0","pres":"1025","tmp":"17","wind":{"deg":"297","dir":"无持续风向","sc":"微风","spd":"15"}},{"cond":{"code":"305","txt":"小雨"},"date":"2016-11-10 13:00","hum":"65","pop":"48","pres":"1024","tmp":"18","wind":{"deg":"282","dir":"无持续风向","sc":"微风","spd":"20"}},{"cond":{"code":"305","txt":"小雨"},"date":"2016-11-10 16:00","hum":"69","pop":"24","pres":"1022","tmp":"18","wind":{"deg":"268","dir":"无持续风向","sc":"微风","spd":"18"}},{"cond":{"code":"305","txt":"小雨"},"date":"2016-11-10 19:00","hum":"75","pop":"0","pres":"1022","tmp":"17","wind":{"deg":"267","dir":"无持续风向","sc":"微风","spd":"17"}},{"cond":{"code":"101","txt":"多云"},"date":"2016-11-10 22:00","hum":"77","pop":"0","pres":"1023","tmp":"13","wind":{"deg":"275","dir":"无持续风向","sc":"微风","spd":"15"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"9","hum":"78","pcpn":"0","pres":"1025","tmp":"10","vis":"10","wind":{"deg":"346","dir":"西北风","sc":"4-5","spd":"18"}}
     * status : ok
     * suggestion : {"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"},"sport":{"brf":"较适宜","txt":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。"},"trav":{"brf":"适宜","txt":"天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
     */

    private String status;
    /**
     * air : {"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}
     * comf : {"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"}
     * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
     * flu : {"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"}
     * sport : {"brf":"较适宜","txt":"天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。"}
     * trav : {"brf":"适宜","txt":"天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。"}
     * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
     */

    private SuggestionBean suggestion;
    /**
     * astro : {"mr":"14:10","ms":"01:11","sr":"06:20","ss":"17:02"}
     * cond : {"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"}
     * date : 2016-11-10
     * hum : 73
     * pcpn : 0.2
     * pop : 72
     * pres : 1024
     * tmp : {"max":"14","min":"7"}
     * uv : 5
     * vis : 10
     * wind : {"deg":"280","dir":"西风","sc":"3-4","spd":"11"}
     */

    private List<DailyForecastBean> daily_forecast;
    /**
     * cond : {"code":"305","txt":"小雨"}
     * date : 2016-11-10 10:00
     * hum : 69
     * pop : 0
     * pres : 1025
     * tmp : 17
     * wind : {"deg":"297","dir":"无持续风向","sc":"微风","spd":"15"}
     */

    private List<HourlyForecastBean> hourly_forecast;

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuggestionBean getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBean suggestion) {
        this.suggestion = suggestion;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int type) {
        this.itemType = type;
    }

    public static class AqiBean implements Serializable {
        /**
         * aqi : 48
         * co : 1
         * no2 : 51
         * o3 : 26
         * pm10 : 47
         * pm25 : 25
         * qlty : 优
         * so2 : 24
         */

        private CityBean city;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public static class CityBean implements Serializable {
            private String aqi;
            private String co;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String qlty;
            private String so2;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
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

    public static class BasicBean implements Serializable {
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        /**
         * loc : 2016-11-10 08:53
         * utc : 2016-11-10 00:53
         */

        private UpdateBean update;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public static class UpdateBean implements Serializable {
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
    }

    public static class NowBean implements Serializable, MultiItemEntity {
        /**
         * code : 101
         * txt : 多云
         */

        private CondBean cond;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        /**
         * deg : 346
         * dir : 西北风
         * sc : 4-5
         * spd : 18
         */

        private WindBean wind;

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
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

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        @Override
        public int getItemType() {
            return TYPE_NOW;
        }

        public static class CondBean implements Serializable {
            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBean implements Serializable {
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class SuggestionBean implements Serializable, MultiItemEntity {
        /**
         * brf : 良
         * txt : 气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。
         */

        private AirBean air;
        /**
         * brf : 较舒适
         * txt : 白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。
         */

        private ComfBean comf;
        /**
         * brf : 较适宜
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        private CwBean cw;
        /**
         * brf : 较冷
         * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
         */

        private DrsgBean drsg;
        /**
         * brf : 较易发
         * txt : 天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。
         */

        private FluBean flu;
        /**
         * brf : 较适宜
         * txt : 天气较好，但风力较大，推荐您进行室内运动，若在户外运动请注意避风保暖。
         */

        private SportBean sport;
        /**
         * brf : 适宜
         * txt : 天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。
         */

        private TravBean trav;
        /**
         * brf : 最弱
         * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        private UvBean uv;

        public AirBean getAir() {
            return air;
        }

        public void setAir(AirBean air) {
            this.air = air;
        }

        public ComfBean getComf() {
            return comf;
        }

        public void setComf(ComfBean comf) {
            this.comf = comf;
        }

        public CwBean getCw() {
            return cw;
        }

        public void setCw(CwBean cw) {
            this.cw = cw;
        }

        public DrsgBean getDrsg() {
            return drsg;
        }

        public void setDrsg(DrsgBean drsg) {
            this.drsg = drsg;
        }

        public FluBean getFlu() {
            return flu;
        }

        public void setFlu(FluBean flu) {
            this.flu = flu;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(SportBean sport) {
            this.sport = sport;
        }

        public TravBean getTrav() {
            return trav;
        }

        public void setTrav(TravBean trav) {
            this.trav = trav;
        }

        public UvBean getUv() {
            return uv;
        }

        public void setUv(UvBean uv) {
            this.uv = uv;
        }

        @Override
        public int getItemType() {
            return TYPE_SUGGESTION;
        }

        public static class AirBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class ComfBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class CwBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class DrsgBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class FluBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class SportBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class TravBean implements Serializable {
            private String brf;
            private String txt;

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

        public static class UvBean implements Serializable {
            private String brf;
            private String txt;

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
    }

    public static class DailyForecastBean implements Serializable, MultiItemEntity {
        /**
         * mr : 14:10
         * ms : 01:11
         * sr : 06:20
         * ss : 17:02
         */

        private AstroBean astro;
        /**
         * code_d : 101
         * code_n : 101
         * txt_d : 多云
         * txt_n : 多云
         */

        private CondBean cond;
        private String date;
        private String hum;
        private String pcpn;
        private String pop;
        private String pres;
        /**
         * max : 14
         * min : 7
         */

        private TmpBean tmp;
        private String uv;
        private String vis;
        /**
         * deg : 280
         * dir : 西风
         * sc : 3-4
         * spd : 11
         */

        private WindBean wind;

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
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

        public TmpBean getTmp() {
            return tmp;
        }

        public void setTmp(TmpBean tmp) {
            this.tmp = tmp;
        }

        public String getUv() {
            return uv;
        }

        public void setUv(String uv) {
            this.uv = uv;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        @Override
        public int getItemType() {
            return TYPE_DAILYFORECAST;
        }

        public static class AstroBean implements Serializable {
            private String mr;
            private String ms;
            private String sr;
            private String ss;

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
        }

        public static class CondBean implements Serializable {
            private String code_d;
            private String code_n;
            private String txt_d;
            private String txt_n;

            public String getCode_d() {
                return code_d;
            }

            public void setCode_d(String code_d) {
                this.code_d = code_d;
            }

            public String getCode_n() {
                return code_n;
            }

            public void setCode_n(String code_n) {
                this.code_n = code_n;
            }

            public String getTxt_d() {
                return txt_d;
            }

            public void setTxt_d(String txt_d) {
                this.txt_d = txt_d;
            }

            public String getTxt_n() {
                return txt_n;
            }

            public void setTxt_n(String txt_n) {
                this.txt_n = txt_n;
            }
        }

        public static class TmpBean implements Serializable {
            private String max;
            private String min;

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }
        }

        public static class WindBean implements Serializable {
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class HourlyForecastBean implements Serializable {
        /**
         * code : 305
         * txt : 小雨
         */

        private CondBean cond;
        private String date;
        private String hum;
        private String pop;
        private String pres;
        private String tmp;
        /**
         * deg : 297
         * dir : 无持续风向
         * sc : 微风
         * spd : 15
         */

        private WindBean wind;

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
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

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class CondBean implements Serializable {
            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBean implements Serializable {
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    @Override
    public Object clone() {
        HeWeather5 o = null;
        try {
            o = (HeWeather5) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
