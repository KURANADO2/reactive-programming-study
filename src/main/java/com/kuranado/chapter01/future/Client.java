package com.kuranado.chapter01.future;

import com.kuranado.chapter01.common.Input;

import java.util.concurrent.ExecutionException;

/**
 * Future 测试客户端
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:16
 */
public class Client {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Input input = new Input();
        input.setPrice(100);
        OrderService orderService = new OrderService(new FutureDiscountServiceImpl());
        orderService.process(input);
    }
}
