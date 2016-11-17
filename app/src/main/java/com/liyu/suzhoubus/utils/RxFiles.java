package com.liyu.suzhoubus.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.liyu.suzhoubus.BuildConfig;
import com.liyu.suzhoubus.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/11/17.
 */

public class RxFiles {

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

    public static Observable<Uri> saveImageAndGetPathObservable(final Context context, final String url, final String title) {
        return Observable
                .create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Glide.with(context)
                                    .load(url)
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        if (bitmap == null) {
                            subscriber.onError(new Exception("无法下载到图片!"));
                        }
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                })
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "Girls");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        String fileName = title.replace('/', '-') + ".jpg";
                        File file = new File(appDir, fileName);
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Uri uri = Uri.fromFile(file);
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        Uri contentURI = getImageContentUri(context, file.getAbsolutePath());
                        return Observable.just(contentURI);
                    }
                }).subscribeOn(Schedulers.io());

    }

    public static Observable<Uri> saveText2ImageObservable(final Context context, final String text) {
        return Observable
                .create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.share_weibo,null);
                        TextView tv = (TextView) view.findViewById(R.id.weibo_text);
                        tv.setText(text);

                        Bitmap bitmap = null;
                        try {
                            bitmap = saveViewBitmap(view);
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                        if (bitmap == null) {
                            subscriber.onError(new Exception("无法生产图片!"));
                        }
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                })
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "Girls");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        String fileName = "share.jpg";
                        File file = new File(appDir, fileName);
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Uri uri = Uri.fromFile(file);
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        Uri contentURI = getImageContentUri(context, file.getAbsolutePath());
                        return Observable.just(contentURI);
                    }
                }).subscribeOn(Schedulers.io());

    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static Uri getImageContentUri(Context context, String absPath) {

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[] { MediaStore.Images.Media._ID }
                , MediaStore.Images.Media.DATA + "=? "
                , new String[] { absPath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

    private static Bitmap saveViewBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    public static Bitmap duplicateBitmap(Bitmap bmpSrc)
    {
        if (null == bmpSrc)
        { return null; }

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888); if (null != bmpDest) { Canvas canvas = new Canvas(bmpDest); final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);

        canvas.drawBitmap(bmpSrc, rect, rect, null); }

        return bmpDest;
    }

}
