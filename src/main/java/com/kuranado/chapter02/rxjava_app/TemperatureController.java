package com.kuranado.chapter02.rxjava_app;

import com.kuranado.chapter02.common.Temperature;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rx.Subscriber;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * 温度接口
 *
 * @author Xinling Jing
 * @date 2021-09-26 17:47
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class TemperatureController {

    private final TemperatureSensor temperatureSensor;

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        log.info("为客户端 " + request.getRemoteAddr() + " 打开 SSE 流");
        RxSseEmitter rxSseEmitter = new RxSseEmitter();
        // 温度流
        temperatureSensor.temperatureStream()
            // RxSseEmitter 实例中的订阅者订阅温度流
            .subscribe(rxSseEmitter.getSubscriber());
        // 返回 RxSseEmitter 到 Servlet 容器中
        return rxSseEmitter;
    }

    @Data
    static class RxSseEmitter extends SseEmitter {

        /**
         * SSE 会话超时时间 30 分钟
         */
        private static final Long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;

        // Temperature 事件的订阅者
        private final Subscriber<Temperature> subscriber;

        public RxSseEmitter() {

            // 设置 SSE 会话超时
            super(SSE_SESSION_TIMEOUT);

            this.subscriber = new Subscriber<Temperature>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Temperature temperature) {
                    try {
                        RxSseEmitter.this.send(temperature);
                    } catch (IOException e) {
                        // 发送失败时取消订阅
                        unsubscribe();
                        e.printStackTrace();
                    }
                }
            };

            // SSE 会话完成取消订阅
            RxSseEmitter.this.onCompletion(() -> subscriber.unsubscribe());
            // SSE 会话超时取消订阅
            RxSseEmitter.this.onTimeout(() -> subscriber.unsubscribe());
        }
    }
}
