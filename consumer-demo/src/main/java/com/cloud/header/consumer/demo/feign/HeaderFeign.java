package com.cloud.header.consumer.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author linzf
 * @since 2019/7/23
 * 类描述：
 */
@FeignClient(value="PRODUCE-DEMO")
public interface HeaderFeign {

    /**
     * 功能描述：实现调用生产者的方法
     * @return 返回调用结果
     */
    @GetMapping("getConsumerHeader")
    String getConsumerHeader();

}
