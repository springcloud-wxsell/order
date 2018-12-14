package com.sell.order.message;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.tools.json.JSONUtil;
import com.sell.common.dto.ProductInfoDTO;
import com.sell.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WangWei
 * @Title: ProductInfoReceiver
 * @ProjectName order
 * @date 2018/12/13 15:43
 * @description:
 */
@Component
@Slf4j
@Transactional
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message => productInfoDTO
        List<ProductInfoDTO> productInfoDTOList = (List<ProductInfoDTO>) JsonUtils.fromJson(message,
                new TypeReference<List<ProductInfoDTO>>() {});
        log.info("从队列【{}】接收消息: {}", "productInfo", productInfoDTOList);

        // 存储到redis中
        productInfoDTOList.stream().forEach(e ->
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, e.getProductId()),
                    String.valueOf(e.getProductStock()))
        );

    }
}
