package com.sell.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author WangWei
 * @Title: MqReceiver
 * @ProjectName order
 * @date 2018/12/11 15:47
 * @description:
 */
@Slf4j
@Component
public class MqReceiver {

    // 手动创建队列  @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    // 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    // 自动创建队列 Exchange和Queue
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message) {
        log.info("MqReceiver:{}", message);
    }

    /**
     * 数码供应商服务，接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("mydOrder"),
            key = "digital",
            exchange = @Exchange("digitalOrder")
    ))
    public void processDigital(String message) {
        log.info("digital MqReceiver:{}", message);
    }

    /**
     * 水果供应商服务，接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("mydOrder"),
            key = "fruit",
            exchange = @Exchange("fruitOrder")
    ))
    public void processFruit(String message) {
        log.info("fruit MqReceiver:{}", message);
    }
}
