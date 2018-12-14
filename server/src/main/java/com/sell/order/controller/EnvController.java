package com.sell.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangWei
 * @Title: EnvController
 * @ProjectName order
 * @date 2018/12/7 15:56
 * @description:
 */
@RestController
@RequestMapping("/env")
@RefreshScope
public class EnvController {

    @Value("${env}")
    public String env;

    @GetMapping("/print")
    public String print(){
        return env;
    }
}
