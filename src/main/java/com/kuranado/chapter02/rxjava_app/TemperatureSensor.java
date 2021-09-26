package com.kuranado.chapter02.rxjava_app;

import com.kuranado.chapter02.common.Temperature;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 温度传感器
 *
 * @author Xinling Jing
 * @date 2021-09-26 16:38
 */
@Component
@Slf4j
public class TemperatureSensor {

    private final Random random = new Random();

    private final Observable<Temperature> dataStream = Observable
        // 生成大量数字流（0，1，2...，MAX_VALUE - 1）
        .range(0, Integer.MAX_VALUE)
        // T -> R
        // flatMap 是无序的，concatMap 是有序的
        .concatMap(e -> Observable
            .just(e)
            // 随机延迟 0 - 5 秒
            .delay(random.nextInt(5000), TimeUnit.MILLISECONDS)
            // Integer -> Temperature
            .map(e2 -> this.probe())
        )
        // 将源流中的事件广播到所有目标流
        .publish()
        // 仅在存在至少一个传出订阅时才创建对于对传入共享流的订阅，
        .refCount();

    public Observable<Temperature> temperatureStream() {
        return dataStream;
    }

    private Temperature probe() {
        // 模拟传感器输出的温度值
        double v = 16 + random.nextGaussian() * 10;
        log.info("探测温度：" + v);
        return new Temperature(v);
    }

}
