package com.liyu.fakeweather.model;

import java.util.List;

/**
 * Created by liyu on 2017/9/1.
 */

public interface IFakeWeather {

    FakeWeather.FakeBasic getFakeBasic();
    FakeWeather.FakeNow getFakeNow();
    FakeWeather.FakeAqi getFakeAqi();
    List<FakeWeather.FakeForecastDaily> getFakeForecastDaily();
    List<FakeWeather.FakeForecastHourly> getFakeForecastHourly();
    List<FakeWeather.FakeSuggestion> getFakeSuggestion();

}
