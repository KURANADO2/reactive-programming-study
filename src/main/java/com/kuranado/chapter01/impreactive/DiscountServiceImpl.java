package com.kuranado.chapter01.impreactive;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

/**
 * 折扣接口实现类
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:45
 */
public class DiscountServiceImpl implements DiscountService {

    @Override
    public Output calculate(Input input) throws InterruptedException {
        // 模拟耗时操作
        Thread.sleep(1000);
        // 打 8 折
        double total = input.getPrice() * 0.8;
        Output output = new Output();
        output.setTotal(total);
        return output;
    }
}
