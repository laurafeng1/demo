package com.example.demo.service;

import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(String exchange, String key, Good good) {
        rabbitTemplate.convertAndSend(exchange, key, good);
    }
}
