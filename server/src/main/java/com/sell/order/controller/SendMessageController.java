package com.sell.order.controller;

import com.sell.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author WangWei
 * @Title: SendMessageController
 * @ProjectName order
 * @date 2018/12/12 17:00
 * @description:
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process() {
        String message = "now: " + new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }
}
