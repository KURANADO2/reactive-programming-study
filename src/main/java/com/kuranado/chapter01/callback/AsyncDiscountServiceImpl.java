package com.kuranado.chapter01.callback;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.function.Consumer;

/**
 * 异步折扣接口实现类
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:57
 */
public class AsyncDiscountServiceImpl implements DiscountService {

    @Override
    public void calculate(Input input, Consumer<Output> consumer) {
        new Thread(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 打 8 折
            double total = input.getPrice() * 0.8;
            Output output = new Output();
            output.setTotal(total);
            consumer.accept(output);
        }).start();
    }
}
