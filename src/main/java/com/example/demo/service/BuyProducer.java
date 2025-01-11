package com.example.demo.service;

import com.example.demo.entity.Buy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender (Buy buy) {
        rabbitTemplate.convertAndSend("MY_EXCHANGE1", "MY_ROUTING1", buy);
    }
}
