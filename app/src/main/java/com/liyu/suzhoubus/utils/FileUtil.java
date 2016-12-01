package com.liyu.suzhoubus.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by liyu on 2016/12/1.
 */

public class FileUtil {

    public static boolean isExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 如果存在外部SD卡则返回外部缓存路径
     *
     * @param context
     * @return
     */
    public static String getAppCacheDir(Context context) {
        if (context.getExternalCacheDir() != null && isExistSDCard()) {
            return context.getExternalCacheDir().toString();
        } else {
            return context.getCacheDir().toString();
        }
    }

    public static String getInternalCacheDir(Context context) {
        return context.getCacheDir().toString();
    }

    public static String getExternalCacheDir(Context context) {
        if (context.getExternalCacheDir() != null && isExistSDCard()) {
            return context.getExternalCacheDir().toString();
        } else
            return null;
    }

    public static boolean delete(String path) {
        return delete(new File(path));
    }

    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return file.delete();
            }
            for (File childFile : childFiles) {
                delete(childFile);
            }
            return file.delete();
        }
        return false;
    }

    public static Observable<File> mkdirsIfNotExists(final File file) {
        return Observable.defer(new Func0<Observable<File>>() {
            @Override
            public Observable<File> call() {
                if (file.mkdirs() || file.isDirectory()) {
                    return Observable.just(file);
                } else {
                    return Observable.error(new IOException("Failed to mkdirs " + file.getPath()));
                }
            }
        });
    }
}
