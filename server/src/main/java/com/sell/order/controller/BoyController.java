package com.sell.order.controller;

import com.sell.order.config.BoyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangWei
 * @Title: BoyController
 * @ProjectName order
 * @date 2018/12/10 11:54
 * @description:
 */
@RestController
@RequestMapping("/boy")
public class BoyController {

    @Autowired
    private BoyConfig boyConfig;

    @GetMapping("/print")
    public String print() {
        return "name:" + boyConfig.getName() + "; age:" + boyConfig.getAge();
    }
}
