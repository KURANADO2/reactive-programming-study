package com.kuranado.chapter02.pubsub_app;

import com.kuranado.chapter02.common.Temperature;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 温度传感器
 *
 * @author Xinling Jing
 * @date 2021-09-24 19:21
 */
@Component
@RequiredArgsConstructor
public class TemperatureSensor {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Random random = new Random();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void startProcessing() {
        // 1 秒后调用读取
        this.scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                probe();
            }
        }, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        // 模拟传感器输出的温度值
        double temperature = 16 + random.nextGaussian() * 10;
        // 发布事件到事件通道中
        applicationEventPublisher.publishEvent(new Temperature(temperature));
        // 随机延迟 0 - 5 秒调用下一次读取
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                probe();
            }
        }, random.nextInt(5000), TimeUnit.MILLISECONDS);
    }
}
