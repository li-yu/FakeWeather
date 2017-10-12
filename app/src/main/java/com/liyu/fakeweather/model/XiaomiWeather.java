package com.liyu.fakeweather.model;

import java.util.List;

/**
 * Created by liyu on 2017/8/30.
 */

public class XiaomiWeather implements IFakeWeather{


    /**
     * current : {"feelsLike":{"unit":"℃","value":"24"},"humidity":{"unit":"%","value":"54"},"pressure":{"unit":"mb","value":"1014.1"},"pubTime":"2017-08-30T13:40:00+08:00","temperature":{"unit":"℃","value":"27"},"uvIndex":"2","visibility":{"unit":"km","value":""},"weather":"0","wind":{"direction":{"unit":"°","value":"135"},"speed":{"unit":"km/h","value":"8.5"}}}
     * forecastDaily : {"aqi":{"brandInfo":{"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]},"pubTime":"2017-08-30T00:00:00+08:00","status":0,"value":[105,130,145,142,149,142,66,37,59,99,136,141,117,108,119]},"precipitationProbability":{"status":0,"value":["5","25","21","70","25"]},"pubTime":"2017-08-30T13:40:00+08:00","status":0,"sunRiseSet":{"status":0,"value":[{"from":"2017-08-30T05:40:00+08:00","to":"2017-08-30T18:49:00+08:00"},{"from":"2017-08-31T05:41:00+08:00","to":"2017-08-31T18:48:00+08:00"},{"from":"2017-09-01T05:42:00+08:00","to":"2017-09-01T18:47:00+08:00"},{"from":"2017-09-02T05:43:00+08:00","to":"2017-09-02T18:45:00+08:00"},{"from":"2017-09-03T05:44:00+08:00","to":"2017-09-03T18:43:00+08:00"},{"from":"2017-09-04T05:45:00+08:00","to":"2017-09-04T18:42:00+08:00"},{"from":"2017-09-05T05:46:00+08:00","to":"2017-09-05T18:40:00+08:00"},{"from":"2017-09-06T05:47:00+08:00","to":"2017-09-06T18:39:00+08:00"},{"from":"2017-09-07T05:48:00+08:00","to":"2017-09-07T18:37:00+08:00"},{"from":"2017-09-08T05:49:00+08:00","to":"2017-09-08T18:35:00+08:00"},{"from":"2017-09-09T05:50:00+08:00","to":"2017-09-09T18:34:00+08:00"},{"from":"2017-09-10T05:51:00+08:00","to":"2017-09-10T18:32:00+08:00"},{"from":"2017-09-11T05:52:00+08:00","to":"2017-09-11T18:31:00+08:00"},{"from":"2017-09-12T05:53:00+08:00","to":"2017-09-12T18:29:00+08:00"},{"from":"2017-09-13T05:53:00+08:00","to":"2017-09-13T18:27:00+08:00"}]},"temperature":{"status":0,"unit":"℃","value":[{"from":"28","to":"15"},{"from":"28","to":"18"},{"from":"28","to":"19"},{"from":"24","to":"18"},{"from":"27","to":"19"},{"from":"27","to":"20"},{"from":"28","to":"20"},{"from":"30","to":"18"},{"from":"30","to":"18"},{"from":"30","to":"19"},{"from":"30","to":"18"},{"from":"29","to":"18"},{"from":"29","to":"17"},{"from":"28","to":"18"},{"from":"27","to":"18"}]},"weather":{"status":0,"value":[{"from":"0","to":"1"},{"from":"2","to":"2"},{"from":"2","to":"7"},{"from":"7","to":"7"},{"from":"2","to":"1"},{"from":"1","to":"2"},{"from":"7","to":"2"},{"from":"7","to":"1"},{"from":"1","to":"7"},{"from":"7","to":"7"},{"from":"7","to":"1"},{"from":"0","to":"1"},{"from":"1","to":"1"},{"from":"1","to":"2"},{"from":"1","to":"1"}]},"wind":{"direction":{"status":0,"unit":"°","value":[{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"90","to":"90"},{"from":"135","to":"135"},{"from":"135","to":"135"}]},"speed":{"status":0,"unit":"km/h","value":[{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"}]}}}
     * forecastHourly : {"aqi":{"brandInfo":{"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]},"pubTime":"2017-08-30T15:00:00+08:00","status":0,"value":[102,103,105,106,108,109,111,112,113,114,116,118,121,122,123,125,127,128,129,130,132,133,134]},"desc":"明天凌晨2点钟后转小雨，其后多云","status":0,"temperature":{"pubTime":"2017-08-30T15:00:00+08:00","status":0,"unit":"℃","value":[25,26,25,23,22,20,20,20,20,19,19,19,19,19,19,19,20,21,22,23,23,24,25]},"weather":{"pubTime":"2017-08-30T15:00:00+08:00","status":0,"value":[0,0,0,0,0,0,0,0,0,0,0,7,7,7,7,7,7,7,2,1,1,1,1]},"wind":{"status":0,"value":[{"datetime":"2017-08-30T15:00:00.000+08:00","direction":"171.92","speed":"11.03"},{"datetime":"2017-08-30T16:00:00.000+08:00","direction":"174.13","speed":"10.86"},{"datetime":"2017-08-30T17:00:00.000+08:00","direction":"175.42","speed":"10.04"},{"datetime":"2017-08-30T18:00:00.000+08:00","direction":"175.34","speed":"8.64"},{"datetime":"2017-08-30T19:00:00.000+08:00","direction":"174.04","speed":"7.13"},{"datetime":"2017-08-30T20:00:00.000+08:00","direction":"172.39","speed":"6.08"},{"datetime":"2017-08-30T21:00:00.000+08:00","direction":"171.95","speed":"5.88"},{"datetime":"2017-08-30T22:00:00.000+08:00","direction":"172.05","speed":"6.24"},{"datetime":"2017-08-30T23:00:00.000+08:00","direction":"171.19","speed":"6.69"},{"datetime":"2017-08-31T00:00:00.000+08:00","direction":"168.68","speed":"6.89"},{"datetime":"2017-08-31T01:00:00.000+08:00","direction":"166.07","speed":"6.89"},{"datetime":"2017-08-31T02:00:00.000+08:00","direction":"165.55","speed":"6.78"},{"datetime":"2017-08-31T03:00:00.000+08:00","direction":"168.51","speed":"6.65"},{"datetime":"2017-08-31T04:00:00.000+08:00","direction":"171.91","speed":"6.44"},{"datetime":"2017-08-31T05:00:00.000+08:00","direction":"171.29","speed":"6.02"},{"datetime":"2017-08-31T06:00:00.000+08:00","direction":"162.4","speed":"5.4"},{"datetime":"2017-08-31T07:00:00.000+08:00","direction":"148.83","speed":"5.1"},{"datetime":"2017-08-31T08:00:00.000+08:00","direction":"141.21","speed":"5.31"},{"datetime":"2017-08-31T09:00:00.000+08:00","direction":"146.05","speed":"5.81"},{"datetime":"2017-08-31T10:00:00.000+08:00","direction":"156.79","speed":"6.57"},{"datetime":"2017-08-31T11:00:00.000+08:00","direction":"166.67","speed":"7.46"},{"datetime":"2017-08-31T12:00:00.000+08:00","direction":"173.54","speed":"8.12"},{"datetime":"2017-08-31T13:00:00.000+08:00","direction":"179.08","speed":"8.7"},{"datetime":"2017-08-31T15:00:00.000+08:00","direction":"188.67","speed":"10.86"},{"datetime":"2017-08-31T16:00:00.000+08:00","direction":"190.46","speed":"12.01"},{"datetime":"2017-08-31T17:00:00.000+08:00","direction":"188.32","speed":"12.21"},{"datetime":"2017-08-31T18:00:00.000+08:00","direction":"180.73","speed":"11.1"},{"datetime":"2017-08-31T19:00:00.000+08:00","direction":"169.72","speed":"9.59"},{"datetime":"2017-08-31T20:00:00.000+08:00","direction":"162.46","speed":"8.29"},{"datetime":"2017-08-31T21:00:00.000+08:00","direction":"169.1","speed":"7.22"},{"datetime":"2017-08-31T22:00:00.000+08:00","direction":"186.11","speed":"6.77"},{"datetime":"2017-08-31T23:00:00.000+08:00","direction":"200.18","speed":"6.8"},{"datetime":"2017-09-01T00:00:00.000+08:00","direction":"203.46","speed":"6.19"},{"datetime":"2017-09-01T01:00:00.000+08:00","direction":"197.58","speed":"5.04"},{"datetime":"2017-09-01T02:00:00.000+08:00","direction":"184.74","speed":"4.09"},{"datetime":"2017-09-01T03:00:00.000+08:00","direction":"172.71","speed":"3.69"},{"datetime":"2017-09-01T04:00:00.000+08:00","direction":"162.1","speed":"3.39"},{"datetime":"2017-09-01T05:00:00.000+08:00","direction":"143.14","speed":"2.87"},{"datetime":"2017-09-01T06:00:00.000+08:00","direction":"102.65","speed":"2.8"},{"datetime":"2017-09-01T07:00:00.000+08:00","direction":"72.75","speed":"4.05"},{"datetime":"2017-09-01T08:00:00.000+08:00","direction":"64.29","speed":"5.31"},{"datetime":"2017-09-01T09:00:00.000+08:00","direction":"68.45","speed":"5.64"},{"datetime":"2017-09-01T10:00:00.000+08:00","direction":"82.37","speed":"5.3"},{"datetime":"2017-09-01T11:00:00.000+08:00","direction":"106.01","speed":"5.06"},{"datetime":"2017-09-01T12:00:00.000+08:00","direction":"132.01","speed":"5.65"}]}}
     * indices : {"indices":[{"type":"uvIndex","value":"2"},{"type":"humidity","value":"54"},{"type":"feelsLike","value":"24"},{"type":"pressure","value":"1014.1"},{"type":"carWash","value":"1"},{"type":"sports","value":"0"}],"pubTime":"","status":0}
     * alerts : []
     * yesterday : {"aqi":"73","date":"2017-08-29T12:10:00+08:00","status":0,"sunRise":"2017-08-29T05:39:00+08:00","sunSet":"2017-08-29T18:51:00+08:00","tempMax":"26","tempMin":"15","weatherEnd":"0","weatherStart":"1","windDircEnd":"180","windDircStart":"180","windSpeedEnd":"8.5","windSpeedStart":"8.5"}
     * url : {"weathercn":"","caiyun":""}
     * brandInfo : {"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/069835733640846b1b2613a855328d2b6df404343/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""},{"brandId":"sina","logo":"http://f5.market.xiaomi.com/download/MiSafe/069835733640846b1b2615a85132892b63f404343/a.webp","names":{"en_US":"新浪天气通","zh_TW":"新浪天氣通","zh_CN":"新浪天气通"},"url":""}]}
     * preHour : [{"pubTime":"2017-08-30T14:00:00+08:00","wind":{"speed":{"unit":"km/h","value":"8.5"},"direction":{"unit":"°","value":"135"}},"humidity":{"unit":"%","value":"58"},"pressure":{"unit":"mb","value":"1014.1"},"visibility":{"unit":"km","value":""},"feelsLike":{"unit":"℃","value":"26"},"aqi":{"pubTime":"2017-08-30T12:00:00+08:00","so2":"3","pm10Desc":"PM10指的是直径小于或等于10微米的颗粒物，又称为可吸入颗粒物","o3":"50","status":0,"no2Desc":"短期浓度超过200微克/立方米时，二氧化氮是一种引起呼吸道严重发炎的有毒气体","suggest":"空气质量可以接受，可能对少数异常敏感的人群健康有较弱影响","so2Desc":"二氧化硫影响呼吸系统和肺功能，并刺激眼睛。呼吸道的炎症导致咳嗽、粘液分泌、加重哮喘和慢性支气管炎","co":"0.9","o3Desc":"臭氧俗称晴空杀手，无色无味，但对人体的伤害不比PM2.5低，浓度高时建议减少夏季午后的外出活动，如果不开窗效果更佳","no2":"72","primary":"","aqi":"88","coDesc":"暴露在一氧化碳中可能严重损害心脏和中枢神经系统，也可能令孕妇胎儿产生严重的不良影响","pm10":"95","pm25Desc":"PM2.5的主要来源是燃料、木材和其他生物质燃料的燃烧","pm25":"65","src":"中国环境监测总站","brandInfo":{"brands":[{"logo":"","names":{"zh_TW":"中國環境監測總站","en_US":"CNEMC","zh_CN":"中国环境监测总站"},"brandId":"CNEMC","url":""}]}},"uvIndex":"2","weather":"0","temperature":{"unit":"℃","value":"24"}}]
     * aqi : {"pubTime":"2017-08-30T13:00:00+08:00","so2":"4","pm10Desc":"PM10的主要来源是建筑活动和从地表扬起的尘土，含有氧化物矿物和其他成分","o3":"76","status":0,"no2Desc":"短期浓度超过200微克/立方米时，二氧化氮是一种引起呼吸道严重发炎的有毒气体","suggest":"户外PM2.5浓度较高，出行时建议佩戴口罩","so2Desc":"二氧化硫是一种无色气体，当空气中SO2达到一定浓度时，空气中会有刺鼻的气味","co":"0.95","o3Desc":"空气中过多臭氧可能导致呼吸问题，引发哮喘，降低肺功能并引起肺部疾病，对人类健康影响较大","no2":"76","primary":"","aqi":"105","pm10":"124","coDesc":"一氧化碳是无色，无臭，无味气体，但吸入对人体有十分大的危害","src":"中国环境监测总站","pm25":"78","pm25Desc":"PM2.5易携带重金属、微生物等有害物质，对人体健康影响较大","brandInfo":{"brands":[{"logo":"","names":{"en_US":"CNEMC","zh_TW":"中國環境監測總站","zh_CN":"中国环境监测总站"},"brandId":"CNEMC","url":""}]}}
     */

    private CurrentBean current;
    private ForecastDailyBean forecastDaily;
    private ForecastHourlyBean forecastHourly;
    private IndicesBeanX indices;
    private YesterdayBean yesterday;
    private UrlBean url;
    private BrandInfoBeanXX brandInfo;
    private AqiBeanXX aqi;
    private List<?> alerts;
    private List<PreHourBean> preHour;

    public CurrentBean getCurrent() {
        return current;
    }

    public void setCurrent(CurrentBean current) {
        this.current = current;
    }

    public ForecastDailyBean getForecastDaily() {
        return forecastDaily;
    }

    public void setForecastDaily(ForecastDailyBean forecastDaily) {
        this.forecastDaily = forecastDaily;
    }

    public ForecastHourlyBean getForecastHourly() {
        return forecastHourly;
    }

    public void setForecastHourly(ForecastHourlyBean forecastHourly) {
        this.forecastHourly = forecastHourly;
    }

    public IndicesBeanX getIndices() {
        return indices;
    }

    public void setIndices(IndicesBeanX indices) {
        this.indices = indices;
    }

    public YesterdayBean getYesterday() {
        return yesterday;
    }

    public void setYesterday(YesterdayBean yesterday) {
        this.yesterday = yesterday;
    }

    public UrlBean getUrl() {
        return url;
    }

    public void setUrl(UrlBean url) {
        this.url = url;
    }

    public BrandInfoBeanXX getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(BrandInfoBeanXX brandInfo) {
        this.brandInfo = brandInfo;
    }

    public AqiBeanXX getAqi() {
        return aqi;
    }

    public void setAqi(AqiBeanXX aqi) {
        this.aqi = aqi;
    }

    public List<?> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<?> alerts) {
        this.alerts = alerts;
    }

    public List<PreHourBean> getPreHour() {
        return preHour;
    }

    public void setPreHour(List<PreHourBean> preHour) {
        this.preHour = preHour;
    }

    @Override
    public FakeWeather.FakeBasic getFakeBasic() {
        return null;
    }

    @Override
    public FakeWeather.FakeNow getFakeNow() {
        return null;
    }

    @Override
    public FakeWeather.FakeAqi getFakeAqi() {
        return null;
    }

    @Override
    public List<FakeWeather.FakeForecastDaily> getFakeForecastDaily() {
        return null;
    }

    @Override
    public List<FakeWeather.FakeForecastHourly> getFakeForecastHourly() {
        return null;
    }

    @Override
    public List<FakeWeather.FakeSuggestion> getFakeSuggestion() {
        return null;
    }

    public static class CurrentBean {
        /**
         * feelsLike : {"unit":"℃","value":"24"}
         * humidity : {"unit":"%","value":"54"}
         * pressure : {"unit":"mb","value":"1014.1"}
         * pubTime : 2017-08-30T13:40:00+08:00
         * temperature : {"unit":"℃","value":"27"}
         * uvIndex : 2
         * visibility : {"unit":"km","value":""}
         * weather : 0
         * wind : {"direction":{"unit":"°","value":"135"},"speed":{"unit":"km/h","value":"8.5"}}
         */

        private FeelsLikeBean feelsLike;
        private HumidityBean humidity;
        private PressureBean pressure;
        private String pubTime;
        private TemperatureBean temperature;
        private String uvIndex;
        private VisibilityBean visibility;
        private String weather;
        private WindBean wind;

        public FeelsLikeBean getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(FeelsLikeBean feelsLike) {
            this.feelsLike = feelsLike;
        }

        public HumidityBean getHumidity() {
            return humidity;
        }

        public void setHumidity(HumidityBean humidity) {
            this.humidity = humidity;
        }

        public PressureBean getPressure() {
            return pressure;
        }

        public void setPressure(PressureBean pressure) {
            this.pressure = pressure;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public TemperatureBean getTemperature() {
            return temperature;
        }

        public void setTemperature(TemperatureBean temperature) {
            this.temperature = temperature;
        }

        public String getUvIndex() {
            return uvIndex;
        }

        public void setUvIndex(String uvIndex) {
            this.uvIndex = uvIndex;
        }

        public VisibilityBean getVisibility() {
            return visibility;
        }

        public void setVisibility(VisibilityBean visibility) {
            this.visibility = visibility;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class FeelsLikeBean {
            /**
             * unit : ℃
             * value : 24
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class HumidityBean {
            /**
             * unit : %
             * value : 54
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class PressureBean {
            /**
             * unit : mb
             * value : 1014.1
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class TemperatureBean {
            /**
             * unit : ℃
             * value : 27
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class VisibilityBean {
            /**
             * unit : km
             * value :
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class WindBean {
            /**
             * direction : {"unit":"°","value":"135"}
             * speed : {"unit":"km/h","value":"8.5"}
             */

            private DirectionBean direction;
            private SpeedBean speed;

            public DirectionBean getDirection() {
                return direction;
            }

            public void setDirection(DirectionBean direction) {
                this.direction = direction;
            }

            public SpeedBean getSpeed() {
                return speed;
            }

            public void setSpeed(SpeedBean speed) {
                this.speed = speed;
            }

            public static class DirectionBean {
                /**
                 * unit : °
                 * value : 135
                 */

                private String unit;
                private String value;

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class SpeedBean {
                /**
                 * unit : km/h
                 * value : 8.5
                 */

                private String unit;
                private String value;

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }

    public static class ForecastDailyBean {
        /**
         * aqi : {"brandInfo":{"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]},"pubTime":"2017-08-30T00:00:00+08:00","status":0,"value":[105,130,145,142,149,142,66,37,59,99,136,141,117,108,119]}
         * precipitationProbability : {"status":0,"value":["5","25","21","70","25"]}
         * pubTime : 2017-08-30T13:40:00+08:00
         * status : 0
         * sunRiseSet : {"status":0,"value":[{"from":"2017-08-30T05:40:00+08:00","to":"2017-08-30T18:49:00+08:00"},{"from":"2017-08-31T05:41:00+08:00","to":"2017-08-31T18:48:00+08:00"},{"from":"2017-09-01T05:42:00+08:00","to":"2017-09-01T18:47:00+08:00"},{"from":"2017-09-02T05:43:00+08:00","to":"2017-09-02T18:45:00+08:00"},{"from":"2017-09-03T05:44:00+08:00","to":"2017-09-03T18:43:00+08:00"},{"from":"2017-09-04T05:45:00+08:00","to":"2017-09-04T18:42:00+08:00"},{"from":"2017-09-05T05:46:00+08:00","to":"2017-09-05T18:40:00+08:00"},{"from":"2017-09-06T05:47:00+08:00","to":"2017-09-06T18:39:00+08:00"},{"from":"2017-09-07T05:48:00+08:00","to":"2017-09-07T18:37:00+08:00"},{"from":"2017-09-08T05:49:00+08:00","to":"2017-09-08T18:35:00+08:00"},{"from":"2017-09-09T05:50:00+08:00","to":"2017-09-09T18:34:00+08:00"},{"from":"2017-09-10T05:51:00+08:00","to":"2017-09-10T18:32:00+08:00"},{"from":"2017-09-11T05:52:00+08:00","to":"2017-09-11T18:31:00+08:00"},{"from":"2017-09-12T05:53:00+08:00","to":"2017-09-12T18:29:00+08:00"},{"from":"2017-09-13T05:53:00+08:00","to":"2017-09-13T18:27:00+08:00"}]}
         * temperature : {"status":0,"unit":"℃","value":[{"from":"28","to":"15"},{"from":"28","to":"18"},{"from":"28","to":"19"},{"from":"24","to":"18"},{"from":"27","to":"19"},{"from":"27","to":"20"},{"from":"28","to":"20"},{"from":"30","to":"18"},{"from":"30","to":"18"},{"from":"30","to":"19"},{"from":"30","to":"18"},{"from":"29","to":"18"},{"from":"29","to":"17"},{"from":"28","to":"18"},{"from":"27","to":"18"}]}
         * weather : {"status":0,"value":[{"from":"0","to":"1"},{"from":"2","to":"2"},{"from":"2","to":"7"},{"from":"7","to":"7"},{"from":"2","to":"1"},{"from":"1","to":"2"},{"from":"7","to":"2"},{"from":"7","to":"1"},{"from":"1","to":"7"},{"from":"7","to":"7"},{"from":"7","to":"1"},{"from":"0","to":"1"},{"from":"1","to":"1"},{"from":"1","to":"2"},{"from":"1","to":"1"}]}
         * wind : {"direction":{"status":0,"unit":"°","value":[{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"90","to":"90"},{"from":"135","to":"135"},{"from":"135","to":"135"}]},"speed":{"status":0,"unit":"km/h","value":[{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"}]}}
         */

        private AqiBean aqi;
        private PrecipitationProbabilityBean precipitationProbability;
        private String pubTime;
        private int status;
        private SunRiseSetBean sunRiseSet;
        private TemperatureBeanX temperature;
        private WeatherBean weather;
        private WindBeanX wind;

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public PrecipitationProbabilityBean getPrecipitationProbability() {
            return precipitationProbability;
        }

        public void setPrecipitationProbability(PrecipitationProbabilityBean precipitationProbability) {
            this.precipitationProbability = precipitationProbability;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public SunRiseSetBean getSunRiseSet() {
            return sunRiseSet;
        }

        public void setSunRiseSet(SunRiseSetBean sunRiseSet) {
            this.sunRiseSet = sunRiseSet;
        }

        public TemperatureBeanX getTemperature() {
            return temperature;
        }

        public void setTemperature(TemperatureBeanX temperature) {
            this.temperature = temperature;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public WindBeanX getWind() {
            return wind;
        }

        public void setWind(WindBeanX wind) {
            this.wind = wind;
        }

        public static class AqiBean {
            /**
             * brandInfo : {"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]}
             * pubTime : 2017-08-30T00:00:00+08:00
             * status : 0
             * value : [105,130,145,142,149,142,66,37,59,99,136,141,117,108,119]
             */

            private BrandInfoBean brandInfo;
            private String pubTime;
            private int status;
            private List<Integer> value;

            public BrandInfoBean getBrandInfo() {
                return brandInfo;
            }

            public void setBrandInfo(BrandInfoBean brandInfo) {
                this.brandInfo = brandInfo;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<Integer> getValue() {
                return value;
            }

            public void setValue(List<Integer> value) {
                this.value = value;
            }

            public static class BrandInfoBean {
                private List<BrandsBean> brands;

                public List<BrandsBean> getBrands() {
                    return brands;
                }

                public void setBrands(List<BrandsBean> brands) {
                    this.brands = brands;
                }

                public static class BrandsBean {
                    /**
                     * brandId : caiyun
                     * logo : http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp
                     * names : {"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"}
                     * url :
                     */

                    private String brandId;
                    private String logo;
                    private NamesBean names;
                    private String url;

                    public String getBrandId() {
                        return brandId;
                    }

                    public void setBrandId(String brandId) {
                        this.brandId = brandId;
                    }

                    public String getLogo() {
                        return logo;
                    }

                    public void setLogo(String logo) {
                        this.logo = logo;
                    }

                    public NamesBean getNames() {
                        return names;
                    }

                    public void setNames(NamesBean names) {
                        this.names = names;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public static class NamesBean {
                        /**
                         * en_US : 彩云天气
                         * zh_TW : 彩雲天氣
                         * zh_CN : 彩云天气
                         */

                        private String en_US;
                        private String zh_TW;
                        private String zh_CN;

                        public String getEn_US() {
                            return en_US;
                        }

                        public void setEn_US(String en_US) {
                            this.en_US = en_US;
                        }

                        public String getZh_TW() {
                            return zh_TW;
                        }

                        public void setZh_TW(String zh_TW) {
                            this.zh_TW = zh_TW;
                        }

                        public String getZh_CN() {
                            return zh_CN;
                        }

                        public void setZh_CN(String zh_CN) {
                            this.zh_CN = zh_CN;
                        }
                    }
                }
            }
        }

        public static class PrecipitationProbabilityBean {
            /**
             * status : 0
             * value : ["5","25","21","70","25"]
             */

            private int status;
            private List<String> value;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<String> getValue() {
                return value;
            }

            public void setValue(List<String> value) {
                this.value = value;
            }
        }

        public static class SunRiseSetBean {
            /**
             * status : 0
             * value : [{"from":"2017-08-30T05:40:00+08:00","to":"2017-08-30T18:49:00+08:00"},{"from":"2017-08-31T05:41:00+08:00","to":"2017-08-31T18:48:00+08:00"},{"from":"2017-09-01T05:42:00+08:00","to":"2017-09-01T18:47:00+08:00"},{"from":"2017-09-02T05:43:00+08:00","to":"2017-09-02T18:45:00+08:00"},{"from":"2017-09-03T05:44:00+08:00","to":"2017-09-03T18:43:00+08:00"},{"from":"2017-09-04T05:45:00+08:00","to":"2017-09-04T18:42:00+08:00"},{"from":"2017-09-05T05:46:00+08:00","to":"2017-09-05T18:40:00+08:00"},{"from":"2017-09-06T05:47:00+08:00","to":"2017-09-06T18:39:00+08:00"},{"from":"2017-09-07T05:48:00+08:00","to":"2017-09-07T18:37:00+08:00"},{"from":"2017-09-08T05:49:00+08:00","to":"2017-09-08T18:35:00+08:00"},{"from":"2017-09-09T05:50:00+08:00","to":"2017-09-09T18:34:00+08:00"},{"from":"2017-09-10T05:51:00+08:00","to":"2017-09-10T18:32:00+08:00"},{"from":"2017-09-11T05:52:00+08:00","to":"2017-09-11T18:31:00+08:00"},{"from":"2017-09-12T05:53:00+08:00","to":"2017-09-12T18:29:00+08:00"},{"from":"2017-09-13T05:53:00+08:00","to":"2017-09-13T18:27:00+08:00"}]
             */

            private int status;
            private List<ValueBean> value;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<ValueBean> getValue() {
                return value;
            }

            public void setValue(List<ValueBean> value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * from : 2017-08-30T05:40:00+08:00
                 * to : 2017-08-30T18:49:00+08:00
                 */

                private String from;
                private String to;

                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public String getTo() {
                    return to;
                }

                public void setTo(String to) {
                    this.to = to;
                }
            }
        }

        public static class TemperatureBeanX {
            /**
             * status : 0
             * unit : ℃
             * value : [{"from":"28","to":"15"},{"from":"28","to":"18"},{"from":"28","to":"19"},{"from":"24","to":"18"},{"from":"27","to":"19"},{"from":"27","to":"20"},{"from":"28","to":"20"},{"from":"30","to":"18"},{"from":"30","to":"18"},{"from":"30","to":"19"},{"from":"30","to":"18"},{"from":"29","to":"18"},{"from":"29","to":"17"},{"from":"28","to":"18"},{"from":"27","to":"18"}]
             */

            private int status;
            private String unit;
            private List<ValueBeanX> value;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public List<ValueBeanX> getValue() {
                return value;
            }

            public void setValue(List<ValueBeanX> value) {
                this.value = value;
            }

            public static class ValueBeanX {
                /**
                 * from : 28
                 * to : 15
                 */

                private String from;
                private String to;

                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public String getTo() {
                    return to;
                }

                public void setTo(String to) {
                    this.to = to;
                }
            }
        }

        public static class WeatherBean {
            /**
             * status : 0
             * value : [{"from":"0","to":"1"},{"from":"2","to":"2"},{"from":"2","to":"7"},{"from":"7","to":"7"},{"from":"2","to":"1"},{"from":"1","to":"2"},{"from":"7","to":"2"},{"from":"7","to":"1"},{"from":"1","to":"7"},{"from":"7","to":"7"},{"from":"7","to":"1"},{"from":"0","to":"1"},{"from":"1","to":"1"},{"from":"1","to":"2"},{"from":"1","to":"1"}]
             */

            private int status;
            private List<ValueBeanXX> value;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<ValueBeanXX> getValue() {
                return value;
            }

            public void setValue(List<ValueBeanXX> value) {
                this.value = value;
            }

            public static class ValueBeanXX {
                /**
                 * from : 0
                 * to : 1
                 */

                private String from;
                private String to;

                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public String getTo() {
                    return to;
                }

                public void setTo(String to) {
                    this.to = to;
                }
            }
        }

        public static class WindBeanX {
            /**
             * direction : {"status":0,"unit":"°","value":[{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"90","to":"90"},{"from":"135","to":"135"},{"from":"135","to":"135"}]}
             * speed : {"status":0,"unit":"km/h","value":[{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"}]}
             */

            private DirectionBeanX direction;
            private SpeedBeanX speed;

            public DirectionBeanX getDirection() {
                return direction;
            }

            public void setDirection(DirectionBeanX direction) {
                this.direction = direction;
            }

            public SpeedBeanX getSpeed() {
                return speed;
            }

            public void setSpeed(SpeedBeanX speed) {
                this.speed = speed;
            }

            public static class DirectionBeanX {
                /**
                 * status : 0
                 * unit : °
                 * value : [{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"180","to":"180"},{"from":"225","to":"225"},{"from":"135","to":"135"},{"from":"180","to":"180"},{"from":"90","to":"90"},{"from":"135","to":"135"},{"from":"135","to":"135"}]
                 */

                private int status;
                private String unit;
                private List<ValueBeanXXX> value;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public List<ValueBeanXXX> getValue() {
                    return value;
                }

                public void setValue(List<ValueBeanXXX> value) {
                    this.value = value;
                }

                public static class ValueBeanXXX {
                    /**
                     * from : 135
                     * to : 135
                     */

                    private String from;
                    private String to;

                    public String getFrom() {
                        return from;
                    }

                    public void setFrom(String from) {
                        this.from = from;
                    }

                    public String getTo() {
                        return to;
                    }

                    public void setTo(String to) {
                        this.to = to;
                    }
                }
            }

            public static class SpeedBeanX {
                /**
                 * status : 0
                 * unit : km/h
                 * value : [{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"},{"from":"8.5","to":"8.5"}]
                 */

                private int status;
                private String unit;
                private List<ValueBeanXXXX> value;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public List<ValueBeanXXXX> getValue() {
                    return value;
                }

                public void setValue(List<ValueBeanXXXX> value) {
                    this.value = value;
                }

                public static class ValueBeanXXXX {
                    /**
                     * from : 8.5
                     * to : 8.5
                     */

                    private String from;
                    private String to;

                    public String getFrom() {
                        return from;
                    }

                    public void setFrom(String from) {
                        this.from = from;
                    }

                    public String getTo() {
                        return to;
                    }

                    public void setTo(String to) {
                        this.to = to;
                    }
                }
            }
        }
    }

    public static class ForecastHourlyBean {
        /**
         * aqi : {"brandInfo":{"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]},"pubTime":"2017-08-30T15:00:00+08:00","status":0,"value":[102,103,105,106,108,109,111,112,113,114,116,118,121,122,123,125,127,128,129,130,132,133,134]}
         * desc : 明天凌晨2点钟后转小雨，其后多云
         * status : 0
         * temperature : {"pubTime":"2017-08-30T15:00:00+08:00","status":0,"unit":"℃","value":[25,26,25,23,22,20,20,20,20,19,19,19,19,19,19,19,20,21,22,23,23,24,25]}
         * weather : {"pubTime":"2017-08-30T15:00:00+08:00","status":0,"value":[0,0,0,0,0,0,0,0,0,0,0,7,7,7,7,7,7,7,2,1,1,1,1]}
         * wind : {"status":0,"value":[{"datetime":"2017-08-30T15:00:00.000+08:00","direction":"171.92","speed":"11.03"},{"datetime":"2017-08-30T16:00:00.000+08:00","direction":"174.13","speed":"10.86"},{"datetime":"2017-08-30T17:00:00.000+08:00","direction":"175.42","speed":"10.04"},{"datetime":"2017-08-30T18:00:00.000+08:00","direction":"175.34","speed":"8.64"},{"datetime":"2017-08-30T19:00:00.000+08:00","direction":"174.04","speed":"7.13"},{"datetime":"2017-08-30T20:00:00.000+08:00","direction":"172.39","speed":"6.08"},{"datetime":"2017-08-30T21:00:00.000+08:00","direction":"171.95","speed":"5.88"},{"datetime":"2017-08-30T22:00:00.000+08:00","direction":"172.05","speed":"6.24"},{"datetime":"2017-08-30T23:00:00.000+08:00","direction":"171.19","speed":"6.69"},{"datetime":"2017-08-31T00:00:00.000+08:00","direction":"168.68","speed":"6.89"},{"datetime":"2017-08-31T01:00:00.000+08:00","direction":"166.07","speed":"6.89"},{"datetime":"2017-08-31T02:00:00.000+08:00","direction":"165.55","speed":"6.78"},{"datetime":"2017-08-31T03:00:00.000+08:00","direction":"168.51","speed":"6.65"},{"datetime":"2017-08-31T04:00:00.000+08:00","direction":"171.91","speed":"6.44"},{"datetime":"2017-08-31T05:00:00.000+08:00","direction":"171.29","speed":"6.02"},{"datetime":"2017-08-31T06:00:00.000+08:00","direction":"162.4","speed":"5.4"},{"datetime":"2017-08-31T07:00:00.000+08:00","direction":"148.83","speed":"5.1"},{"datetime":"2017-08-31T08:00:00.000+08:00","direction":"141.21","speed":"5.31"},{"datetime":"2017-08-31T09:00:00.000+08:00","direction":"146.05","speed":"5.81"},{"datetime":"2017-08-31T10:00:00.000+08:00","direction":"156.79","speed":"6.57"},{"datetime":"2017-08-31T11:00:00.000+08:00","direction":"166.67","speed":"7.46"},{"datetime":"2017-08-31T12:00:00.000+08:00","direction":"173.54","speed":"8.12"},{"datetime":"2017-08-31T13:00:00.000+08:00","direction":"179.08","speed":"8.7"},{"datetime":"2017-08-31T15:00:00.000+08:00","direction":"188.67","speed":"10.86"},{"datetime":"2017-08-31T16:00:00.000+08:00","direction":"190.46","speed":"12.01"},{"datetime":"2017-08-31T17:00:00.000+08:00","direction":"188.32","speed":"12.21"},{"datetime":"2017-08-31T18:00:00.000+08:00","direction":"180.73","speed":"11.1"},{"datetime":"2017-08-31T19:00:00.000+08:00","direction":"169.72","speed":"9.59"},{"datetime":"2017-08-31T20:00:00.000+08:00","direction":"162.46","speed":"8.29"},{"datetime":"2017-08-31T21:00:00.000+08:00","direction":"169.1","speed":"7.22"},{"datetime":"2017-08-31T22:00:00.000+08:00","direction":"186.11","speed":"6.77"},{"datetime":"2017-08-31T23:00:00.000+08:00","direction":"200.18","speed":"6.8"},{"datetime":"2017-09-01T00:00:00.000+08:00","direction":"203.46","speed":"6.19"},{"datetime":"2017-09-01T01:00:00.000+08:00","direction":"197.58","speed":"5.04"},{"datetime":"2017-09-01T02:00:00.000+08:00","direction":"184.74","speed":"4.09"},{"datetime":"2017-09-01T03:00:00.000+08:00","direction":"172.71","speed":"3.69"},{"datetime":"2017-09-01T04:00:00.000+08:00","direction":"162.1","speed":"3.39"},{"datetime":"2017-09-01T05:00:00.000+08:00","direction":"143.14","speed":"2.87"},{"datetime":"2017-09-01T06:00:00.000+08:00","direction":"102.65","speed":"2.8"},{"datetime":"2017-09-01T07:00:00.000+08:00","direction":"72.75","speed":"4.05"},{"datetime":"2017-09-01T08:00:00.000+08:00","direction":"64.29","speed":"5.31"},{"datetime":"2017-09-01T09:00:00.000+08:00","direction":"68.45","speed":"5.64"},{"datetime":"2017-09-01T10:00:00.000+08:00","direction":"82.37","speed":"5.3"},{"datetime":"2017-09-01T11:00:00.000+08:00","direction":"106.01","speed":"5.06"},{"datetime":"2017-09-01T12:00:00.000+08:00","direction":"132.01","speed":"5.65"}]}
         */

        private AqiBeanX aqi;
        private String desc;
        private int status;
        private TemperatureBeanXX temperature;
        private WeatherBeanX weather;
        private WindBeanXX wind;

        public AqiBeanX getAqi() {
            return aqi;
        }

        public void setAqi(AqiBeanX aqi) {
            this.aqi = aqi;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public TemperatureBeanXX getTemperature() {
            return temperature;
        }

        public void setTemperature(TemperatureBeanXX temperature) {
            this.temperature = temperature;
        }

        public WeatherBeanX getWeather() {
            return weather;
        }

        public void setWeather(WeatherBeanX weather) {
            this.weather = weather;
        }

        public WindBeanXX getWind() {
            return wind;
        }

        public void setWind(WindBeanXX wind) {
            this.wind = wind;
        }

        public static class AqiBeanX {
            /**
             * brandInfo : {"brands":[{"brandId":"caiyun","logo":"http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp","names":{"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"},"url":""}]}
             * pubTime : 2017-08-30T15:00:00+08:00
             * status : 0
             * value : [102,103,105,106,108,109,111,112,113,114,116,118,121,122,123,125,127,128,129,130,132,133,134]
             */

            private BrandInfoBeanX brandInfo;
            private String pubTime;
            private int status;
            private List<Integer> value;

            public BrandInfoBeanX getBrandInfo() {
                return brandInfo;
            }

            public void setBrandInfo(BrandInfoBeanX brandInfo) {
                this.brandInfo = brandInfo;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<Integer> getValue() {
                return value;
            }

            public void setValue(List<Integer> value) {
                this.value = value;
            }

            public static class BrandInfoBeanX {
                private List<BrandsBeanX> brands;

                public List<BrandsBeanX> getBrands() {
                    return brands;
                }

                public void setBrands(List<BrandsBeanX> brands) {
                    this.brands = brands;
                }

                public static class BrandsBeanX {
                    /**
                     * brandId : caiyun
                     * logo : http://f5.market.mi-img.com/download/MiSafe/07fa34263d698a7a9a8050dde6a7c63f8f243dbf3/a.webp
                     * names : {"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"}
                     * url :
                     */

                    private String brandId;
                    private String logo;
                    private NamesBeanX names;
                    private String url;

                    public String getBrandId() {
                        return brandId;
                    }

                    public void setBrandId(String brandId) {
                        this.brandId = brandId;
                    }

                    public String getLogo() {
                        return logo;
                    }

                    public void setLogo(String logo) {
                        this.logo = logo;
                    }

                    public NamesBeanX getNames() {
                        return names;
                    }

                    public void setNames(NamesBeanX names) {
                        this.names = names;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public static class NamesBeanX {
                        /**
                         * en_US : 彩云天气
                         * zh_TW : 彩雲天氣
                         * zh_CN : 彩云天气
                         */

                        private String en_US;
                        private String zh_TW;
                        private String zh_CN;

                        public String getEn_US() {
                            return en_US;
                        }

                        public void setEn_US(String en_US) {
                            this.en_US = en_US;
                        }

                        public String getZh_TW() {
                            return zh_TW;
                        }

                        public void setZh_TW(String zh_TW) {
                            this.zh_TW = zh_TW;
                        }

                        public String getZh_CN() {
                            return zh_CN;
                        }

                        public void setZh_CN(String zh_CN) {
                            this.zh_CN = zh_CN;
                        }
                    }
                }
            }
        }

        public static class TemperatureBeanXX {
            /**
             * pubTime : 2017-08-30T15:00:00+08:00
             * status : 0
             * unit : ℃
             * value : [25,26,25,23,22,20,20,20,20,19,19,19,19,19,19,19,20,21,22,23,23,24,25]
             */

            private String pubTime;
            private int status;
            private String unit;
            private List<Integer> value;

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public List<Integer> getValue() {
                return value;
            }

            public void setValue(List<Integer> value) {
                this.value = value;
            }
        }

        public static class WeatherBeanX {
            /**
             * pubTime : 2017-08-30T15:00:00+08:00
             * status : 0
             * value : [0,0,0,0,0,0,0,0,0,0,0,7,7,7,7,7,7,7,2,1,1,1,1]
             */

            private String pubTime;
            private int status;
            private List<Integer> value;

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<Integer> getValue() {
                return value;
            }

            public void setValue(List<Integer> value) {
                this.value = value;
            }
        }

        public static class WindBeanXX {
            /**
             * status : 0
             * value : [{"datetime":"2017-08-30T15:00:00.000+08:00","direction":"171.92","speed":"11.03"},{"datetime":"2017-08-30T16:00:00.000+08:00","direction":"174.13","speed":"10.86"},{"datetime":"2017-08-30T17:00:00.000+08:00","direction":"175.42","speed":"10.04"},{"datetime":"2017-08-30T18:00:00.000+08:00","direction":"175.34","speed":"8.64"},{"datetime":"2017-08-30T19:00:00.000+08:00","direction":"174.04","speed":"7.13"},{"datetime":"2017-08-30T20:00:00.000+08:00","direction":"172.39","speed":"6.08"},{"datetime":"2017-08-30T21:00:00.000+08:00","direction":"171.95","speed":"5.88"},{"datetime":"2017-08-30T22:00:00.000+08:00","direction":"172.05","speed":"6.24"},{"datetime":"2017-08-30T23:00:00.000+08:00","direction":"171.19","speed":"6.69"},{"datetime":"2017-08-31T00:00:00.000+08:00","direction":"168.68","speed":"6.89"},{"datetime":"2017-08-31T01:00:00.000+08:00","direction":"166.07","speed":"6.89"},{"datetime":"2017-08-31T02:00:00.000+08:00","direction":"165.55","speed":"6.78"},{"datetime":"2017-08-31T03:00:00.000+08:00","direction":"168.51","speed":"6.65"},{"datetime":"2017-08-31T04:00:00.000+08:00","direction":"171.91","speed":"6.44"},{"datetime":"2017-08-31T05:00:00.000+08:00","direction":"171.29","speed":"6.02"},{"datetime":"2017-08-31T06:00:00.000+08:00","direction":"162.4","speed":"5.4"},{"datetime":"2017-08-31T07:00:00.000+08:00","direction":"148.83","speed":"5.1"},{"datetime":"2017-08-31T08:00:00.000+08:00","direction":"141.21","speed":"5.31"},{"datetime":"2017-08-31T09:00:00.000+08:00","direction":"146.05","speed":"5.81"},{"datetime":"2017-08-31T10:00:00.000+08:00","direction":"156.79","speed":"6.57"},{"datetime":"2017-08-31T11:00:00.000+08:00","direction":"166.67","speed":"7.46"},{"datetime":"2017-08-31T12:00:00.000+08:00","direction":"173.54","speed":"8.12"},{"datetime":"2017-08-31T13:00:00.000+08:00","direction":"179.08","speed":"8.7"},{"datetime":"2017-08-31T15:00:00.000+08:00","direction":"188.67","speed":"10.86"},{"datetime":"2017-08-31T16:00:00.000+08:00","direction":"190.46","speed":"12.01"},{"datetime":"2017-08-31T17:00:00.000+08:00","direction":"188.32","speed":"12.21"},{"datetime":"2017-08-31T18:00:00.000+08:00","direction":"180.73","speed":"11.1"},{"datetime":"2017-08-31T19:00:00.000+08:00","direction":"169.72","speed":"9.59"},{"datetime":"2017-08-31T20:00:00.000+08:00","direction":"162.46","speed":"8.29"},{"datetime":"2017-08-31T21:00:00.000+08:00","direction":"169.1","speed":"7.22"},{"datetime":"2017-08-31T22:00:00.000+08:00","direction":"186.11","speed":"6.77"},{"datetime":"2017-08-31T23:00:00.000+08:00","direction":"200.18","speed":"6.8"},{"datetime":"2017-09-01T00:00:00.000+08:00","direction":"203.46","speed":"6.19"},{"datetime":"2017-09-01T01:00:00.000+08:00","direction":"197.58","speed":"5.04"},{"datetime":"2017-09-01T02:00:00.000+08:00","direction":"184.74","speed":"4.09"},{"datetime":"2017-09-01T03:00:00.000+08:00","direction":"172.71","speed":"3.69"},{"datetime":"2017-09-01T04:00:00.000+08:00","direction":"162.1","speed":"3.39"},{"datetime":"2017-09-01T05:00:00.000+08:00","direction":"143.14","speed":"2.87"},{"datetime":"2017-09-01T06:00:00.000+08:00","direction":"102.65","speed":"2.8"},{"datetime":"2017-09-01T07:00:00.000+08:00","direction":"72.75","speed":"4.05"},{"datetime":"2017-09-01T08:00:00.000+08:00","direction":"64.29","speed":"5.31"},{"datetime":"2017-09-01T09:00:00.000+08:00","direction":"68.45","speed":"5.64"},{"datetime":"2017-09-01T10:00:00.000+08:00","direction":"82.37","speed":"5.3"},{"datetime":"2017-09-01T11:00:00.000+08:00","direction":"106.01","speed":"5.06"},{"datetime":"2017-09-01T12:00:00.000+08:00","direction":"132.01","speed":"5.65"}]
             */

            private int status;
            private List<ValueBeanXXXXX> value;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<ValueBeanXXXXX> getValue() {
                return value;
            }

            public void setValue(List<ValueBeanXXXXX> value) {
                this.value = value;
            }

            public static class ValueBeanXXXXX {
                /**
                 * datetime : 2017-08-30T15:00:00.000+08:00
                 * direction : 171.92
                 * speed : 11.03
                 */

                private String datetime;
                private String direction;
                private String speed;

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }

                public String getDirection() {
                    return direction;
                }

                public void setDirection(String direction) {
                    this.direction = direction;
                }

                public String getSpeed() {
                    return speed;
                }

                public void setSpeed(String speed) {
                    this.speed = speed;
                }
            }
        }
    }

    public static class IndicesBeanX {
        /**
         * indices : [{"type":"uvIndex","value":"2"},{"type":"humidity","value":"54"},{"type":"feelsLike","value":"24"},{"type":"pressure","value":"1014.1"},{"type":"carWash","value":"1"},{"type":"sports","value":"0"}]
         * pubTime :
         * status : 0
         */

        private String pubTime;
        private int status;
        private List<IndicesBean> indices;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<IndicesBean> getIndices() {
            return indices;
        }

        public void setIndices(List<IndicesBean> indices) {
            this.indices = indices;
        }

        public static class IndicesBean {
            /**
             * type : uvIndex
             * value : 2
             */

            private String type;
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class YesterdayBean {
        /**
         * aqi : 73
         * date : 2017-08-29T12:10:00+08:00
         * status : 0
         * sunRise : 2017-08-29T05:39:00+08:00
         * sunSet : 2017-08-29T18:51:00+08:00
         * tempMax : 26
         * tempMin : 15
         * weatherEnd : 0
         * weatherStart : 1
         * windDircEnd : 180
         * windDircStart : 180
         * windSpeedEnd : 8.5
         * windSpeedStart : 8.5
         */

        private String aqi;
        private String date;
        private int status;
        private String sunRise;
        private String sunSet;
        private String tempMax;
        private String tempMin;
        private String weatherEnd;
        private String weatherStart;
        private String windDircEnd;
        private String windDircStart;
        private String windSpeedEnd;
        private String windSpeedStart;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSunRise() {
            return sunRise;
        }

        public void setSunRise(String sunRise) {
            this.sunRise = sunRise;
        }

        public String getSunSet() {
            return sunSet;
        }

        public void setSunSet(String sunSet) {
            this.sunSet = sunSet;
        }

        public String getTempMax() {
            return tempMax;
        }

        public void setTempMax(String tempMax) {
            this.tempMax = tempMax;
        }

        public String getTempMin() {
            return tempMin;
        }

        public void setTempMin(String tempMin) {
            this.tempMin = tempMin;
        }

        public String getWeatherEnd() {
            return weatherEnd;
        }

        public void setWeatherEnd(String weatherEnd) {
            this.weatherEnd = weatherEnd;
        }

        public String getWeatherStart() {
            return weatherStart;
        }

        public void setWeatherStart(String weatherStart) {
            this.weatherStart = weatherStart;
        }

        public String getWindDircEnd() {
            return windDircEnd;
        }

        public void setWindDircEnd(String windDircEnd) {
            this.windDircEnd = windDircEnd;
        }

        public String getWindDircStart() {
            return windDircStart;
        }

        public void setWindDircStart(String windDircStart) {
            this.windDircStart = windDircStart;
        }

        public String getWindSpeedEnd() {
            return windSpeedEnd;
        }

        public void setWindSpeedEnd(String windSpeedEnd) {
            this.windSpeedEnd = windSpeedEnd;
        }

        public String getWindSpeedStart() {
            return windSpeedStart;
        }

        public void setWindSpeedStart(String windSpeedStart) {
            this.windSpeedStart = windSpeedStart;
        }
    }

    public static class UrlBean {
        /**
         * weathercn :
         * caiyun :
         */

        private String weathercn;
        private String caiyun;

        public String getWeathercn() {
            return weathercn;
        }

        public void setWeathercn(String weathercn) {
            this.weathercn = weathercn;
        }

        public String getCaiyun() {
            return caiyun;
        }

        public void setCaiyun(String caiyun) {
            this.caiyun = caiyun;
        }
    }

    public static class BrandInfoBeanXX {
        private List<BrandsBeanXX> brands;

        public List<BrandsBeanXX> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBeanXX> brands) {
            this.brands = brands;
        }

        public static class BrandsBeanXX {
            /**
             * brandId : caiyun
             * logo : http://f5.market.mi-img.com/download/MiSafe/069835733640846b1b2613a855328d2b6df404343/a.webp
             * names : {"en_US":"彩云天气","zh_TW":"彩雲天氣","zh_CN":"彩云天气"}
             * url :
             */

            private String brandId;
            private String logo;
            private NamesBeanXX names;
            private String url;

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public NamesBeanXX getNames() {
                return names;
            }

            public void setNames(NamesBeanXX names) {
                this.names = names;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public static class NamesBeanXX {
                /**
                 * en_US : 彩云天气
                 * zh_TW : 彩雲天氣
                 * zh_CN : 彩云天气
                 */

                private String en_US;
                private String zh_TW;
                private String zh_CN;

                public String getEn_US() {
                    return en_US;
                }

                public void setEn_US(String en_US) {
                    this.en_US = en_US;
                }

                public String getZh_TW() {
                    return zh_TW;
                }

                public void setZh_TW(String zh_TW) {
                    this.zh_TW = zh_TW;
                }

                public String getZh_CN() {
                    return zh_CN;
                }

                public void setZh_CN(String zh_CN) {
                    this.zh_CN = zh_CN;
                }
            }
        }
    }

    public static class AqiBeanXX {
        /**
         * pubTime : 2017-08-30T13:00:00+08:00
         * so2 : 4
         * pm10Desc : PM10的主要来源是建筑活动和从地表扬起的尘土，含有氧化物矿物和其他成分
         * o3 : 76
         * status : 0
         * no2Desc : 短期浓度超过200微克/立方米时，二氧化氮是一种引起呼吸道严重发炎的有毒气体
         * suggest : 户外PM2.5浓度较高，出行时建议佩戴口罩
         * so2Desc : 二氧化硫是一种无色气体，当空气中SO2达到一定浓度时，空气中会有刺鼻的气味
         * co : 0.95
         * o3Desc : 空气中过多臭氧可能导致呼吸问题，引发哮喘，降低肺功能并引起肺部疾病，对人类健康影响较大
         * no2 : 76
         * primary :
         * aqi : 105
         * pm10 : 124
         * coDesc : 一氧化碳是无色，无臭，无味气体，但吸入对人体有十分大的危害
         * src : 中国环境监测总站
         * pm25 : 78
         * pm25Desc : PM2.5易携带重金属、微生物等有害物质，对人体健康影响较大
         * brandInfo : {"brands":[{"logo":"","names":{"en_US":"CNEMC","zh_TW":"中國環境監測總站","zh_CN":"中国环境监测总站"},"brandId":"CNEMC","url":""}]}
         */

        private String pubTime;
        private String so2;
        private String pm10Desc;
        private String o3;
        private int status;
        private String no2Desc;
        private String suggest;
        private String so2Desc;
        private String co;
        private String o3Desc;
        private String no2;
        private String primary;
        private String aqi;
        private String pm10;
        private String coDesc;
        private String src;
        private String pm25;
        private String pm25Desc;
        private BrandInfoBeanXXX brandInfo;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getPm10Desc() {
            return pm10Desc;
        }

        public void setPm10Desc(String pm10Desc) {
            this.pm10Desc = pm10Desc;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNo2Desc() {
            return no2Desc;
        }

        public void setNo2Desc(String no2Desc) {
            this.no2Desc = no2Desc;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public String getSo2Desc() {
            return so2Desc;
        }

        public void setSo2Desc(String so2Desc) {
            this.so2Desc = so2Desc;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3Desc() {
            return o3Desc;
        }

        public void setO3Desc(String o3Desc) {
            this.o3Desc = o3Desc;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getCoDesc() {
            return coDesc;
        }

        public void setCoDesc(String coDesc) {
            this.coDesc = coDesc;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm25Desc() {
            return pm25Desc;
        }

        public void setPm25Desc(String pm25Desc) {
            this.pm25Desc = pm25Desc;
        }

        public BrandInfoBeanXXX getBrandInfo() {
            return brandInfo;
        }

        public void setBrandInfo(BrandInfoBeanXXX brandInfo) {
            this.brandInfo = brandInfo;
        }

        public static class BrandInfoBeanXXX {
            private List<BrandsBeanXXX> brands;

            public List<BrandsBeanXXX> getBrands() {
                return brands;
            }

            public void setBrands(List<BrandsBeanXXX> brands) {
                this.brands = brands;
            }

            public static class BrandsBeanXXX {
                /**
                 * logo :
                 * names : {"en_US":"CNEMC","zh_TW":"中國環境監測總站","zh_CN":"中国环境监测总站"}
                 * brandId : CNEMC
                 * url :
                 */

                private String logo;
                private NamesBeanXXX names;
                private String brandId;
                private String url;

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public NamesBeanXXX getNames() {
                    return names;
                }

                public void setNames(NamesBeanXXX names) {
                    this.names = names;
                }

                public String getBrandId() {
                    return brandId;
                }

                public void setBrandId(String brandId) {
                    this.brandId = brandId;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public static class NamesBeanXXX {
                    /**
                     * en_US : CNEMC
                     * zh_TW : 中國環境監測總站
                     * zh_CN : 中国环境监测总站
                     */

                    private String en_US;
                    private String zh_TW;
                    private String zh_CN;

                    public String getEn_US() {
                        return en_US;
                    }

                    public void setEn_US(String en_US) {
                        this.en_US = en_US;
                    }

                    public String getZh_TW() {
                        return zh_TW;
                    }

                    public void setZh_TW(String zh_TW) {
                        this.zh_TW = zh_TW;
                    }

                    public String getZh_CN() {
                        return zh_CN;
                    }

                    public void setZh_CN(String zh_CN) {
                        this.zh_CN = zh_CN;
                    }
                }
            }
        }
    }

    public static class PreHourBean {
        /**
         * pubTime : 2017-08-30T14:00:00+08:00
         * wind : {"speed":{"unit":"km/h","value":"8.5"},"direction":{"unit":"°","value":"135"}}
         * humidity : {"unit":"%","value":"58"}
         * pressure : {"unit":"mb","value":"1014.1"}
         * visibility : {"unit":"km","value":""}
         * feelsLike : {"unit":"℃","value":"26"}
         * aqi : {"pubTime":"2017-08-30T12:00:00+08:00","so2":"3","pm10Desc":"PM10指的是直径小于或等于10微米的颗粒物，又称为可吸入颗粒物","o3":"50","status":0,"no2Desc":"短期浓度超过200微克/立方米时，二氧化氮是一种引起呼吸道严重发炎的有毒气体","suggest":"空气质量可以接受，可能对少数异常敏感的人群健康有较弱影响","so2Desc":"二氧化硫影响呼吸系统和肺功能，并刺激眼睛。呼吸道的炎症导致咳嗽、粘液分泌、加重哮喘和慢性支气管炎","co":"0.9","o3Desc":"臭氧俗称晴空杀手，无色无味，但对人体的伤害不比PM2.5低，浓度高时建议减少夏季午后的外出活动，如果不开窗效果更佳","no2":"72","primary":"","aqi":"88","coDesc":"暴露在一氧化碳中可能严重损害心脏和中枢神经系统，也可能令孕妇胎儿产生严重的不良影响","pm10":"95","pm25Desc":"PM2.5的主要来源是燃料、木材和其他生物质燃料的燃烧","pm25":"65","src":"中国环境监测总站","brandInfo":{"brands":[{"logo":"","names":{"zh_TW":"中國環境監測總站","en_US":"CNEMC","zh_CN":"中国环境监测总站"},"brandId":"CNEMC","url":""}]}}
         * uvIndex : 2
         * weather : 0
         * temperature : {"unit":"℃","value":"24"}
         */

        private String pubTime;
        private WindBeanXXX wind;
        private HumidityBeanX humidity;
        private PressureBeanX pressure;
        private VisibilityBeanX visibility;
        private FeelsLikeBeanX feelsLike;
        private AqiBeanXXX aqi;
        private String uvIndex;
        private String weather;
        private TemperatureBeanXXX temperature;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public WindBeanXXX getWind() {
            return wind;
        }

        public void setWind(WindBeanXXX wind) {
            this.wind = wind;
        }

        public HumidityBeanX getHumidity() {
            return humidity;
        }

        public void setHumidity(HumidityBeanX humidity) {
            this.humidity = humidity;
        }

        public PressureBeanX getPressure() {
            return pressure;
        }

        public void setPressure(PressureBeanX pressure) {
            this.pressure = pressure;
        }

        public VisibilityBeanX getVisibility() {
            return visibility;
        }

        public void setVisibility(VisibilityBeanX visibility) {
            this.visibility = visibility;
        }

        public FeelsLikeBeanX getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(FeelsLikeBeanX feelsLike) {
            this.feelsLike = feelsLike;
        }

        public AqiBeanXXX getAqi() {
            return aqi;
        }

        public void setAqi(AqiBeanXXX aqi) {
            this.aqi = aqi;
        }

        public String getUvIndex() {
            return uvIndex;
        }

        public void setUvIndex(String uvIndex) {
            this.uvIndex = uvIndex;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public TemperatureBeanXXX getTemperature() {
            return temperature;
        }

        public void setTemperature(TemperatureBeanXXX temperature) {
            this.temperature = temperature;
        }

        public static class WindBeanXXX {
            /**
             * speed : {"unit":"km/h","value":"8.5"}
             * direction : {"unit":"°","value":"135"}
             */

            private SpeedBeanXX speed;
            private DirectionBeanXX direction;

            public SpeedBeanXX getSpeed() {
                return speed;
            }

            public void setSpeed(SpeedBeanXX speed) {
                this.speed = speed;
            }

            public DirectionBeanXX getDirection() {
                return direction;
            }

            public void setDirection(DirectionBeanXX direction) {
                this.direction = direction;
            }

            public static class SpeedBeanXX {
                /**
                 * unit : km/h
                 * value : 8.5
                 */

                private String unit;
                private String value;

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class DirectionBeanXX {
                /**
                 * unit : °
                 * value : 135
                 */

                private String unit;
                private String value;

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public static class HumidityBeanX {
            /**
             * unit : %
             * value : 58
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class PressureBeanX {
            /**
             * unit : mb
             * value : 1014.1
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class VisibilityBeanX {
            /**
             * unit : km
             * value :
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class FeelsLikeBeanX {
            /**
             * unit : ℃
             * value : 26
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class AqiBeanXXX {
            /**
             * pubTime : 2017-08-30T12:00:00+08:00
             * so2 : 3
             * pm10Desc : PM10指的是直径小于或等于10微米的颗粒物，又称为可吸入颗粒物
             * o3 : 50
             * status : 0
             * no2Desc : 短期浓度超过200微克/立方米时，二氧化氮是一种引起呼吸道严重发炎的有毒气体
             * suggest : 空气质量可以接受，可能对少数异常敏感的人群健康有较弱影响
             * so2Desc : 二氧化硫影响呼吸系统和肺功能，并刺激眼睛。呼吸道的炎症导致咳嗽、粘液分泌、加重哮喘和慢性支气管炎
             * co : 0.9
             * o3Desc : 臭氧俗称晴空杀手，无色无味，但对人体的伤害不比PM2.5低，浓度高时建议减少夏季午后的外出活动，如果不开窗效果更佳
             * no2 : 72
             * primary :
             * aqi : 88
             * coDesc : 暴露在一氧化碳中可能严重损害心脏和中枢神经系统，也可能令孕妇胎儿产生严重的不良影响
             * pm10 : 95
             * pm25Desc : PM2.5的主要来源是燃料、木材和其他生物质燃料的燃烧
             * pm25 : 65
             * src : 中国环境监测总站
             * brandInfo : {"brands":[{"logo":"","names":{"zh_TW":"中國環境監測總站","en_US":"CNEMC","zh_CN":"中国环境监测总站"},"brandId":"CNEMC","url":""}]}
             */

            private String pubTime;
            private String so2;
            private String pm10Desc;
            private String o3;
            private int status;
            private String no2Desc;
            private String suggest;
            private String so2Desc;
            private String co;
            private String o3Desc;
            private String no2;
            private String primary;
            private String aqi;
            private String coDesc;
            private String pm10;
            private String pm25Desc;
            private String pm25;
            private String src;
            private BrandInfoBeanXXXX brandInfo;

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getPm10Desc() {
                return pm10Desc;
            }

            public void setPm10Desc(String pm10Desc) {
                this.pm10Desc = pm10Desc;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getNo2Desc() {
                return no2Desc;
            }

            public void setNo2Desc(String no2Desc) {
                this.no2Desc = no2Desc;
            }

            public String getSuggest() {
                return suggest;
            }

            public void setSuggest(String suggest) {
                this.suggest = suggest;
            }

            public String getSo2Desc() {
                return so2Desc;
            }

            public void setSo2Desc(String so2Desc) {
                this.so2Desc = so2Desc;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getO3Desc() {
                return o3Desc;
            }

            public void setO3Desc(String o3Desc) {
                this.o3Desc = o3Desc;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getPrimary() {
                return primary;
            }

            public void setPrimary(String primary) {
                this.primary = primary;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getCoDesc() {
                return coDesc;
            }

            public void setCoDesc(String coDesc) {
                this.coDesc = coDesc;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25Desc() {
                return pm25Desc;
            }

            public void setPm25Desc(String pm25Desc) {
                this.pm25Desc = pm25Desc;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public BrandInfoBeanXXXX getBrandInfo() {
                return brandInfo;
            }

            public void setBrandInfo(BrandInfoBeanXXXX brandInfo) {
                this.brandInfo = brandInfo;
            }

            public static class BrandInfoBeanXXXX {
                private List<BrandsBeanXXXX> brands;

                public List<BrandsBeanXXXX> getBrands() {
                    return brands;
                }

                public void setBrands(List<BrandsBeanXXXX> brands) {
                    this.brands = brands;
                }

                public static class BrandsBeanXXXX {
                    /**
                     * logo :
                     * names : {"zh_TW":"中國環境監測總站","en_US":"CNEMC","zh_CN":"中国环境监测总站"}
                     * brandId : CNEMC
                     * url :
                     */

                    private String logo;
                    private NamesBeanXXXX names;
                    private String brandId;
                    private String url;

                    public String getLogo() {
                        return logo;
                    }

                    public void setLogo(String logo) {
                        this.logo = logo;
                    }

                    public NamesBeanXXXX getNames() {
                        return names;
                    }

                    public void setNames(NamesBeanXXXX names) {
                        this.names = names;
                    }

                    public String getBrandId() {
                        return brandId;
                    }

                    public void setBrandId(String brandId) {
                        this.brandId = brandId;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public static class NamesBeanXXXX {
                        /**
                         * zh_TW : 中國環境監測總站
                         * en_US : CNEMC
                         * zh_CN : 中国环境监测总站
                         */

                        private String zh_TW;
                        private String en_US;
                        private String zh_CN;

                        public String getZh_TW() {
                            return zh_TW;
                        }

                        public void setZh_TW(String zh_TW) {
                            this.zh_TW = zh_TW;
                        }

                        public String getEn_US() {
                            return en_US;
                        }

                        public void setEn_US(String en_US) {
                            this.en_US = en_US;
                        }

                        public String getZh_CN() {
                            return zh_CN;
                        }

                        public void setZh_CN(String zh_CN) {
                            this.zh_CN = zh_CN;
                        }
                    }
                }
            }
        }

        public static class TemperatureBeanXXX {
            /**
             * unit : ℃
             * value : 24
             */

            private String unit;
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
