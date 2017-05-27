package com.liyu.fakeweather.utils;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/11/9.
 */

public class RxDataBase {

    public static <T extends DataSupport> void save(final T t) {
        Observable.unsafeCreate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                t.save();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static <T extends DataSupport> void deleteThenSave(final T t, final String... condition) {
        Observable.unsafeCreate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DataSupport.deleteAll(t.getClass(), condition);
                t.save();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static <T extends DataSupport> void deleteThenSave(final List<T> list) {
        if (list == null || list.size() == 0)
            return;
        Observable.unsafeCreate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DataSupport.deleteAll(list.get(0).getClass());
                DataSupport.saveAll(list);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static <T extends DataSupport> void saveAll(final List<T> list) {
        Observable.unsafeCreate(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                DataSupport.saveAll(list);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public static <T extends DataSupport> Observable<T> getFirst(final Class<T> clazz) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(DataSupport.findFirst(clazz));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T extends DataSupport> Observable<List<T>> getAll(final Class<T> clazz) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                subscriber.onNext(DataSupport.findAll(clazz));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T extends DataSupport> Observable<T> getFirst(final Class<T> clazz, final String... condition) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(DataSupport.where(condition).findFirst(clazz, true));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T extends DataSupport> Observable<List<T>> getAll(final Class<T> clazz, final String... condition) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                subscriber.onNext(DataSupport.where(condition).find(clazz));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

}
