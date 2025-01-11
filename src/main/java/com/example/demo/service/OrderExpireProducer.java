package com.example.demo.service;

import com.example.demo.entity.DelayOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderExpireProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(DelayOrder delayOrder) {
        rabbitTemplate.convertAndSend("MY_EXCHANGE2", "MY_ROUTING2", delayOrder);
    }
}
