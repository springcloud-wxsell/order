package com.sell.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author WangWei
 * @Title: StreamClient
 * @ProjectName order
 * @date 2018/12/12 16:27
 * @description:
 */
public interface StreamClient {

    String INPUT = "input";

    String OUTPUT = "output";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}
