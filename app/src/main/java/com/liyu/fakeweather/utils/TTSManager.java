package com.liyu.fakeweather.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by liyu on 2016/12/27.
 */

public class TTSManager {

    private static TTSManager instance;

    private SpeechSynthesizer mTts;

    public static TTSManager getInstance(Context context) {
        if (instance == null) {
            synchronized (TTSManager.class) {
                instance = new TTSManager(context);
            }
        }
        return instance;
    }

    public static void destroy() {
        if (instance != null) {
            instance.mTts.stopSpeaking();
            instance.mTts.destroy();
            instance = null;
        }
    }

    private TTSManager(Context context) {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=58621a19");
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, SettingsUtil.getTtsVoiceType()); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");//音频保存路径
    }

    public void speak(String text, final TTSCallback callback) {
        if (TextUtils.isEmpty(text) || mTts.isSpeaking())
            return;
        mTts.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                if (callback != null)
                    callback.onStart();
            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                if (callback != null)
                    callback.onCompleted();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

    public interface TTSCallback {

        void onStart();

        void onCompleted();
    }
}
