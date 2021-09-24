package com.kuranado.chapter01.callback;

import com.kuranado.chapter01.common.Input;

/**
 * 回调测试客户端
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:50
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Input input = new Input();
        input.setPrice(100);

        // 同步回调
        OrderService orderService = new OrderService(new SyncDiscountServiceImpl());
        orderService.process(input);

        // 异步回调
        OrderService orderService2 = new OrderService(new AsyncDiscountServiceImpl());
        orderService2.process(input);
    }
}
