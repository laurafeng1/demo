package com.example.demo.service.impl;

import com.example.demo.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(String exchange, String key, User user) {
        rabbitTemplate.convertAndSend(exchange, key, user);
    }
}
