package com.kuranado.chapter01.callback;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.function.Consumer;

/**
 * 同步折扣接口实现类
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:48
 */
public class SyncDiscountServiceImpl implements DiscountService {

    @Override
    public void calculate(Input input, Consumer<Output> consumer) throws InterruptedException {
        // 模拟耗时操作
        Thread.sleep(1000);
        // 打 8 折
        double total = input.getPrice() * 0.8;
        Output output = new Output();
        output.setTotal(total);
        consumer.accept(output);
    }
}
