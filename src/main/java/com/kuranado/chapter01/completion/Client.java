package com.kuranado.chapter01.completion;

import com.kuranado.chapter01.common.Input;

/**
 * CompletionState 测试客户端
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:44
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Input input = new Input();
        input.setPrice(100);
        OrderService orderService = new OrderService(new CompletionStateDiscountServiceImpl());
        orderService.process(input);
    }
}
