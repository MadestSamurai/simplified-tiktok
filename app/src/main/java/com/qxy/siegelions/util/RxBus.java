package com.qxy.siegelions.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * RxBus管理类
 * @author MadSamurai
 */
public class RxBus {
    private static volatile RxBus instance;
    private Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    /**
     * 发送事件
     * @param object
     */
    public void post(Object object) {
        bus.onNext(object);
    }

    /**
     * 根据类型接收相应类型事件
     * @param eventType
     */
    public <T> Observable toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
