package com.kuranado.chapter01.callback;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.function.Consumer;

/**
 * 折扣接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:27
 */
public interface DiscountService {

    void calculate(Input input, Consumer<Output> consumer) throws InterruptedException;
}
