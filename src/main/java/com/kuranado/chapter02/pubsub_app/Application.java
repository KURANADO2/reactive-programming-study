package com.kuranado.chapter02.pubsub_app;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 温度应用
 *
 * @author Xinling Jing
 * @date 2021-09-24 20:34
 */
@SpringBootApplication
@Async
public class Application implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 为 @Async 标记的异步方法手动配置线程池
     *
     * @return Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("sse-");
        // 线程池基本大小
        threadPoolTaskExecutor.setCorePoolSize(2);
        // 线程池最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(100);
        // 阻塞队列大小
        threadPoolTaskExecutor.setQueueCapacity(5);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
