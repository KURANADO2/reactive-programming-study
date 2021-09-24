package com.kuranado.chapter01.future;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.concurrent.Future;

/**
 * 折扣接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:11
 */
public interface DiscountService {

    Future<Output> calculate(Input input);
}
