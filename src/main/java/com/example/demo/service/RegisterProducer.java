package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRegister;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(UserRegister userRegister) {
        rabbitTemplate.convertAndSend("MY_EXCHANGE8","MY_ROUTING8", userRegister);
    }
}
