package com.example.demo.service.impl;

import com.example.demo.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterMessageReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "MY_QUEUE")
    public void receiver(User user) {
        System.out.println("信息接受成功：" + user);
    }
}
