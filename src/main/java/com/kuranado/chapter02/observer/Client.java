package com.kuranado.chapter02.observer;

/**
 * 观察者模式测试客户端
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:46
 */
public class Client {

    public static void main(String[] args) {
        Subject<String> subject = new ConcreteSubject();

        Observer<String> observerA = new ConcreteObserverA();
        subject.registerObserver(observerA);
        subject.notifyObservers("第一条新闻");

        Observer<String> observerB = new ConcreteObserverB();
        subject.registerObserver(observerB);
        subject.notifyObservers("第二条新闻");

        subject.unRegisterObserver(observerA);
        subject.notifyObservers("第三条新闻");
    }
}
