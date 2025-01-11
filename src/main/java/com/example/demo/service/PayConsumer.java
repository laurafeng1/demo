package com.example.demo.service;

import com.example.demo.constant.DemoConstant;
import com.example.demo.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PayProducer payProducer;

    @Autowired
    private BuyService buyService;

    // 收到就是没支付成功
    @RabbitListener(queues = "MY_QUEUE4")
    public void receiver(Order order) {
        for(int i = 0; i < DemoConstant.PAY_RETRY_TIMES; i++) {
            try {
                buyService.payWithoutException(order.getId());
                break;
            } catch(Exception e) {
                payProducer.sender(order);
            }

        }
    }
}
