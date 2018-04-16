package com.liyu.fakeweather.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.liyu.fakeweather.App;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.utils.FileSizeUtil;
import com.liyu.fakeweather.utils.FileUtil;
import com.liyu.fakeweather.utils.SettingsUtil;
import com.liyu.fakeweather.utils.SimpleSubscriber;

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
    private ListPreference busRefreshFreq;
    private Preference cleanCache;
    private Preference theme;
    private Preference moduleManage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        weatherShareType = (ListPreference) findPreference(SettingsUtil.WEATHER_SHARE_TYPE);
        busRefreshFreq = (ListPreference) findPreference(SettingsUtil.BUS_REFRESH_FREQ);
        cleanCache = findPreference(SettingsUtil.CLEAR_CACHE);
        theme = findPreference(SettingsUtil.THEME);
        moduleManage = findPreference("module_manage");

        weatherShareType.setSummary(weatherShareType.getValue());
        busRefreshFreq.setSummary(String.format("%s 秒，长按『刷新』按钮即可开启自动模式。", busRefreshFreq.getValue()));
        String[] colorNames = getActivity().getResources().getStringArray(R.array.color_name);
        int currentThemeIndex = SettingsUtil.getTheme();
        if (currentThemeIndex >= colorNames.length) {
            theme.setSummary("自定义色");
        } else {
            theme.setSummary(colorNames[currentThemeIndex]);
        }

        weatherShareType.setOnPreferenceChangeListener(this);
        busRefreshFreq.setOnPreferenceChangeListener(this);
        cleanCache.setOnPreferenceClickListener(this);
        theme.setOnPreferenceClickListener(this);
        moduleManage.setOnPreferenceClickListener(this);

        String[] cachePaths = new String[]{FileUtil.getInternalCacheDir(App.getContext()), FileUtil.getExternalCacheDir(App.getContext())};
        Observable
                .just(cachePaths)
                .map(new Func1<String[], String>() {
                    @Override
                    public String call(String[] strings) {
                        return FileSizeUtil.getAutoFileOrFilesSize(strings);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        cleanCache.setSummary(s);
                    }
                });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == weatherShareType) {
            weatherShareType.setSummary((String) o);
            SettingsUtil.setWeatherShareType((String) o);
        } else if (preference == busRefreshFreq) {
            busRefreshFreq.setSummary(String.format("%s 秒，长按『刷新』按钮即可开启自动模式。", (String) o));
            SettingsUtil.setBusRefreshFreq(Integer.parseInt((String) o));
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == cleanCache) {
            Observable
                    .just(FileUtil.delete(FileUtil.getInternalCacheDir(App.getContext())))
                    .map(new Func1<Boolean, Boolean>() {
                        @Override
                        public Boolean call(Boolean result) {
                            return result && FileUtil.delete(FileUtil.getExternalCacheDir(App.getContext()));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleSubscriber<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            cleanCache.setSummary(FileSizeUtil.getAutoFileOrFilesSize(FileUtil.getInternalCacheDir(App.getContext()), FileUtil.getExternalCacheDir(App.getContext())));
                            Snackbar.make(getView(), "缓存已清除 (*^__^*)", Snackbar.LENGTH_SHORT).show();
                        }
                    });
        } else if (preference == theme) {
            new ColorChooserDialog.Builder((SettingActivity) getActivity(), R.string.theme)
                    .customColors(R.array.colors, null)
                    .doneButton(R.string.done)
                    .cancelButton(R.string.cancel)
                    .allowUserColorInput(false)
                    .allowUserColorInputAlpha(false)
                    .show(((SettingActivity) getActivity()).getSupportFragmentManager());
        } else if (preference == moduleManage) {
            getActivity().startActivity(new Intent(getActivity(), ModuleManageActivity.class));
        }
        return true;
    }
}
