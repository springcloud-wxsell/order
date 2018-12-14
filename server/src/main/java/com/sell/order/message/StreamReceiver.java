package com.sell.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author WangWei
 * @Title: StreamReceiver
 * @ProjectName order
 * @date 2018/12/12 16:28
 * @description:
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(value = StreamClient.INPUT)
    public void processInput(Object message) {
        log.info("StreamReceiver: {}", message);
    }

    @StreamListener(value = StreamClient.OUTPUT)
    public void processOutput(Object message) {
        log.info("StreamReceiver: {}", message);
    }

}
