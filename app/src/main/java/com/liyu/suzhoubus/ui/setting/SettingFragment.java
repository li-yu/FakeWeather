package com.liyu.suzhoubus.ui.setting;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.bumptech.glide.Glide;
import com.liyu.suzhoubus.App;
import com.liyu.suzhoubus.BuildConfig;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.utils.FileSizeUtil;
import com.liyu.suzhoubus.utils.RxFiles;
import com.liyu.suzhoubus.utils.SettingsUtil;
import com.liyu.suzhoubus.utils.SimpleSubscriber;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {

    private ListPreference weatherShareType;
    private Preference clearCache;
    private Preference about;
    private CheckBoxPreference weatherAlert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        weatherShareType = (ListPreference) findPreference(SettingsUtil.WEATHER_SHARE_TYPE);
        clearCache = findPreference(SettingsUtil.CLEAR_CACHE);
        about = findPreference(SettingsUtil.ABOUT);
        weatherAlert = (CheckBoxPreference) findPreference(SettingsUtil.WEATHER_ALERT);

        weatherShareType.setSummary(weatherShareType.getValue());
        clearCache.setSummary(FileSizeUtil.getAutoFileOrFilesSize(App.getAppCacheDir() + "/NetCache"));
        about.setSummary("v" + BuildConfig.VERSION_NAME);

        weatherAlert.setOnPreferenceChangeListener(this);
        weatherShareType.setOnPreferenceChangeListener(this);
        clearCache.setOnPreferenceClickListener(this);
        about.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == weatherAlert) {
            SettingsUtil.setWeatherAlert((Boolean) o);
        } else if (preference == weatherShareType) {
            weatherShareType.setSummary((String) o);
            SettingsUtil.setWeatherShareType((String) o);
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == clearCache) {
//            Glide.get(getActivity()).clearDiskCache();
            Observable
                    .just(RxFiles.delete(new File(App.getAppCacheDir() + "/NetCache")))
                    .filter(new Func1<Boolean, Boolean>() {
                        @Override
                        public Boolean call(Boolean aBoolean) {
                            return aBoolean;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleSubscriber<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            clearCache.setSummary(FileSizeUtil.getAutoFileOrFilesSize(App.getAppCacheDir() + "/NetCache"));
                            Snackbar.make(getView(), "缓存已清除 (*^__^*)", Snackbar.LENGTH_SHORT).show();
                        }
                    });
        } else if (preference == about) {

        }
        return true;
    }
}
