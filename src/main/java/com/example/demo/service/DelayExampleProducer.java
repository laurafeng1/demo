package com.example.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.DelayConfig.DELAY_EXCHANGE;
import static com.example.demo.config.DelayConfig.DELAY_ROUTING;

@Service
public class DelayExampleProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender() {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE, DELAY_ROUTING, "消息发送成功");
    }
}
