package com.kuranado.chapter02.observer;

/**
 * 观察者接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:47
 */
public interface Observer<T> {

    void observe(T event);
}
