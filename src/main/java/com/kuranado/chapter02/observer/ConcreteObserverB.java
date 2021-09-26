package com.kuranado.chapter02.observer;

/**
 * 具体观察者 B
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:48
 */
public class ConcreteObserverB implements Observer<String> {

    @Override
    public void observe(String event) {
        System.out.println("观察者 B 收到：" + event);
    }
}
