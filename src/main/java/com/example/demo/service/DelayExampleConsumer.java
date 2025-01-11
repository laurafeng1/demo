package com.example.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.DelayConfig.DEAD_LETTER_QUEUE;

@Service
public class DelayExampleConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = DEAD_LETTER_QUEUE)
    public void receiver(String message) {
        System.out.println(message);
    }
}
