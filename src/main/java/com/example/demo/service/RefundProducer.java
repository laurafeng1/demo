package com.example.demo.service;

import com.example.demo.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(Order order) {
        rabbitTemplate.convertAndSend("MY_EXCHANGE7", "MY_ROUTING7", order);
    }
}
