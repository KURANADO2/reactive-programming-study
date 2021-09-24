package com.kuranado.chapter01.impreactive;

import com.kuranado.chapter01.common.Input;

/**
 * 命令式编程测试客户端
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:15
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        OrderService orderService = new OrderService(new DiscountServiceImpl());
        Input input = new Input();
        input.setPrice(100);
        orderService.process(input);
    }
}
