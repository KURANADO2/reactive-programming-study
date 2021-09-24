package com.kuranado.chapter01.completion;

import com.kuranado.chapter01.common.Input;
import com.kuranado.chapter01.common.Output;

import java.util.concurrent.CompletionStage;

/**
 * 折扣接口
 *
 * @author Xinling Jing
 * @date 2021-09-24 11:31
 */
public interface DiscountService {

    CompletionStage<Output> calculate(Input input);
}
