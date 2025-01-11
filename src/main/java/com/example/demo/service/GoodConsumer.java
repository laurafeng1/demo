package com.example.demo.service;

import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.OrderMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GoodMapper goodMapper;

    @RabbitListener(queues = "MY_QUEUE")
    public void receiver(Good good) {
        goodMapper.update(good);
    }
}
