package com.sell.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author WangWei
 * @Title: HystrixController
 * @ProjectName order
 * @date 2018/12/20 11:12
 * @description:
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

  /*  // 超时配置
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })*/
    // 服务熔断
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    //@HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("number") Integer number) {
        if (number % 2 == 0) {
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/listForOrder", Arrays.asList("123456"), String.class);
//        throw new RuntimeException("出异常了!!!");
    }

    private String fallback() {
        return "太拥挤了, 请稍后再试~~";
    }

    private String defaultFallback() {
        return "默认: 太拥挤了, 请稍后再试~~";
    }
}
