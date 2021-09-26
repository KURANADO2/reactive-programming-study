package com.kuranado.chapter02.rxjava;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * RxJava 测试
 *
 * @author Xinling Jing
 * @date 2021-09-26 11:11
 */
public class RxJavaTest {

    @Test
    public void testRxJavaWorkflow() {
        // 普通写法
        // 创建不支持背压的 Observable（Observable 可能生成太多元素，导致订阅者超载）
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("发送元素：Hello");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Done");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("异常：" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("收到元素：" + s);
            }
        };

        // 将 Observable 和 Subscriber 实例串在一起
        observable.subscribe(subscriber);
    }

    @Test
    public void testRxJavaWorkflowWithLambda() {
        // Lambda 写法
        Observable.create(subscriber -> {
            subscriber.onNext("Hello");
            subscriber.onCompleted();
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                System.out.println("Done");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("异常：" + e);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("收到元素：" + o);
            }
        });
    }

    @Test
    public void testRxJavaStreams() {
        Observable.just("1", "2", "3", "4");
        Observable.from(new String[]{"1", "2", "3", "4"});
        Observable.from(Collections.emptyList());
        // 基于 Callable 创建 Observable
        Observable<String> hello = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() {
                return "Hello ";
            }
        });
        Future<String> future = Executors.newCachedThreadPool().submit(new Callable<String>() {
            @Override
            public String call() {
                return "World";
            }
        });
        // 基于 Future 创建 Observable
        Observable<String> world = Observable.from(future);
        // 组合 Observable
        Observable<String> concat = Observable.concat(hello, world, Observable.just("!"));
        concat.forEach(System.out::print);
    }

    @Test
    public void testTimeBasedSequence() throws InterruptedException {
        // 生成事件并进行消费的过程发生在一个单独的守护线程中
        // 生产元素 0、1、2、3...，每秒生成一个
        Observable.interval(1, TimeUnit.SECONDS).subscribe(e -> System.out.println("收到元素：" + e));
        // 主线程退出，守护线程也将被退出，为了看到测试结果，让主线程休眠一段时间
        Thread.sleep(5000);
    }

    @Test
    public void testSubscription() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        // 生产元素 0、1、2、3...，每 100 毫秒生成一个
        Subscription subscription = Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(e -> System.out.println(
            "收到元素：" + e));
        // 主线程等待
        countDownLatch.await(450, TimeUnit.MILLISECONDS);
        // 主线程等待 450 毫秒后取消订阅
        subscription.unsubscribe();
    }

    @Test
    public void testZipOperator() {
        Observable<String> zip = Observable.zip(Observable.just("A", "B", "C", "D"), Observable.just("1", "2", "3"),
            (x, y) -> x + y);
        zip.forEach(System.out::print);
    }
}
