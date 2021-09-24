package com.kuranado.chapter01.impreactive;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

/**
 * 折扣接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 10:08
 */
public interface DiscountService {

    Output calculate(Input input) throws InterruptedException;
}
