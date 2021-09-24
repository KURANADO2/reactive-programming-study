package com.kuranado.chapter01.completion;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * CompletionState 折扣接口实现类
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:39
 */
public class CompletionStateDiscountServiceImpl implements DiscountService {

    @Override
    public CompletionStage<Output> calculate(Input input) {
        return CompletableFuture.supplyAsync(new Supplier<Output>() {
            @Override
            public Output get() {
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
                return output;
            }
        });
    }
}
