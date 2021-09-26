package com.kuranado.chapter02.pubsub_app;

import com.kuranado.chapter02.common.Temperature;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 温度相关接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 20:21
 */
@RestController
@Slf4j
public class TemperatureController {

    /**
     * SSE 会话超时时间 30 分钟
     */
    private static final Long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;

    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        log.info("为客户端 " + request.getRemoteAddr() + " 打开 SSE 流");
        SseEmitter sseEmitter = new SseEmitter(SSE_SESSION_TIMEOUT);
        clients.add(sseEmitter);

        // 如果超时将自己从 clients 列表中移除
        sseEmitter.onTimeout(new Runnable() {
            @Override
            public void run() {
                clients.remove(sseEmitter);
            }
        });

        // 如果处理完成将自己从 clients 列表中移除
        sseEmitter.onCompletion(new Runnable() {
            @Override
            public void run() {
                clients.remove(sseEmitter);
            }
        });

        return sseEmitter;
    }

    /**
     * 处理温度事件
     *
     * @param temperature 事件值
     */
    @Async
    @EventListener
    public void handleMessage(Temperature temperature) {
        log.info(String.format("温度：%4.2f C，已激活订阅者数目：%d", temperature.getTemperature(), clients.size()));
        List<SseEmitter> deadClients = new ArrayList<>();
        clients.forEach(sseEmitter -> {
            try {
                Instant start = Instant.now();
                sseEmitter.send(temperature, MediaType.APPLICATION_JSON);
                log.info("发送给客户端，花费时间：" + Duration.between(start, Instant.now()));
            } catch (IOException e) {
                // 客户端如果不运作将抛出异常
                deadClients.add(sseEmitter);
            }
        });
        clients.removeAll(deadClients);
    }
}
