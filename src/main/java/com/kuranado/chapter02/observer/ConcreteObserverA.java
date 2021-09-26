package com.kuranado.chapter02.observer;

/**
 * 具体观察者 A
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:48
 */
public class ConcreteObserverA implements Observer<String> {

    @Override
    public void observe(String event) {
        System.out.println("观察者 A 收到：" + event);
    }
}
