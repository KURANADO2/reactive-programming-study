package com.kuranado.chapter01.impreactive;

import com.alibaba.fastjson.JSON;
import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import lombok.RequiredArgsConstructor;

/**
 * 订单服务
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:10
 */
@RequiredArgsConstructor
public class OrderService {

    private final DiscountService discountService;

    void process(Input input) throws InterruptedException {
        System.out.println("开始调用折扣接口");
        Output output = discountService.calculate(input);
        System.out.println("折扣接口返回结果：" + JSON.toJSONString(output));
        System.out.println("继续执行其他业务逻辑");
        // 模拟耗时操作
        Thread.sleep(1000);
        System.out.println("其他业务逻辑处理完毕");
    }
}
