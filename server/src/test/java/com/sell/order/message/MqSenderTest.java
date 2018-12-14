package com.sell.order.message;

import com.sell.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author WangWei
 * @Title: MqSenderTest
 * @ProjectName order
 * @date 2018/12/11 15:50
 * @description:
 */
public class MqSenderTest extends OrderApplicationTests{

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", " now:" + new Date());
    }

    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder", "digital", " now:" + new Date());
    }

}