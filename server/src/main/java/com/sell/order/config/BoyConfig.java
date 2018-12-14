package com.sell.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author WangWei
 * @Title: BoyConfig
 * @ProjectName order
 * @date 2018/12/10 11:53
 * @description:
 */
@Data
@Component
@ConfigurationProperties("boy")
@RefreshScope // 动态刷新相关值
public class BoyConfig {

    // 姓名
    private String name;

    // 年龄
    private Integer age;
}
