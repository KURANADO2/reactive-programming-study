package com.kuranado.chapter01.future;

import com.alibaba.fastjson.JSON;
import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 订单服务
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:14
 */
@RequiredArgsConstructor
public class OrderService {

    private final DiscountService discountService;

    void process(Input input) throws InterruptedException, ExecutionException {
        System.out.println("开始调用折扣接口");
        Future<Output> result = discountService.calculate(input);
        // 调用 get() 将会阻塞当前线程，直到 get() 返回
        /// System.out.println("折扣接口返回结果：" + JSON.toJSONString(result.get()));
        System.out.println("继续执行其他业务逻辑");
        // 模拟耗时操作
        Thread.sleep(1000);
        System.out.println("其他业务逻辑处理完毕");
        // 最后调用 get()，以使其他业务逻辑不被阻塞
        System.out.println("折扣接口返回结果：" + JSON.toJSONString(result.get()));
    }
}
