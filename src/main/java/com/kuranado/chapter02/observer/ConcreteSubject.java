package com.kuranado.chapter02.observer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 具体主题实现
 *
 * @author Xinling Jing
 * @date 2021-09-24 13:50
 */
public class ConcreteSubject implements Subject<String> {

    /**
     * <p>保存已订阅的观察者列表</p>
     * <p>CopyOnWriteArraySet 在每次更新操作时，都会创建元素的新副本，可以保证线程安全，但相应的代价比较高</p>
     * <p>不过，订阅者列表并不会经常发生变动，所以影响不大</p>
     */
    private final Set<Observer<String>> observers = new CopyOnWriteArraySet<>();

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    private final ExecutorService executorService = new ThreadPoolExecutor(5, 200,
        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory,
        new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void registerObserver(Observer<String> observer) {
        observers.add(observer);
    }

    @Override
    public void unRegisterObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        observers.forEach(observer -> {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    observer.observe(event);
                }
            });
        });
    }
}
