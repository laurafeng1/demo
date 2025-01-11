package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.mapper.OrderMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @RabbitListener(queues = "MY_QUEUE")
    public void receiver(Order order) {
        orderMapper.add(order);
    }
}
