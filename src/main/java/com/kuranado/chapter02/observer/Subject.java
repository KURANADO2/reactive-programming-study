package com.kuranado.chapter02.observer;

/**
 * 主题接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:46
 */
public interface Subject<T> {

    void registerObserver(Observer<T> observer);

    void unRegisterObserver(Observer<T> observer);

    void notifyObservers(T event);
}
