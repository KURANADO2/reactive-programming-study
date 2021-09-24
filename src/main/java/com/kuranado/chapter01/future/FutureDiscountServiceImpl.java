package com.kuranado.chapter01.future;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Future 折扣接口实现类
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:13
 */
public class FutureDiscountServiceImpl implements DiscountService {

    @Override
    public Future<Output> calculate(Input input) {
        FutureTask<Output> futureTask = new FutureTask<>(new Callable<Output>() {
            @Override
            public Output call() throws Exception {
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

        new Thread(futureTask).start();

        return futureTask;
    }
}
