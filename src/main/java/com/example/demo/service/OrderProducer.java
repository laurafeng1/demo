package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.mapper.OrderMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(String exchange, String key, Order order) {
        rabbitTemplate.convertAndSend(exchange, key, order);
    }
}
