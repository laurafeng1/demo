package com.example.demo.service;

import com.example.demo.constant.DemoConstant;
import com.example.demo.entity.Buy;
import com.example.demo.entity.DelayOrder;
import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.OrderMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BuyConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodService goodService;

    @Autowired
    private BuyProducer buyProducer;

    @Autowired
    private OrderExpireProducer orderExpireProducer;

    @RabbitListener(queues = "MY_QUEUE1")
    @Transactional
    public void receiver(Buy buy) {
        Good good = buy.getGood();
        Order order = buy.getOrder();
        goodService.updateGood(good.getId(), good);
        orderMapper.add(order);

        DelayOrder delayOrder = new DelayOrder();
        delayOrder.setBuy(buy);
        delayOrder.setExpireTime(System.currentTimeMillis() + DemoConstant.ORDER_EXPIRE_INTERVAL);
        orderExpireProducer.sender(delayOrder); //有另一个listener监听这个sender
    }
}
