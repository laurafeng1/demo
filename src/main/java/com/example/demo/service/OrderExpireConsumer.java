package com.example.demo.service;

import com.example.demo.entity.DelayOrder;
import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderExpireConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GoodService goodService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderExpireProducer orderExpireProducer;

    private final static Logger logger = LoggerFactory.getLogger(OrderExpireConsumer.class);

    @RabbitListener(queues = "MY_QUEUE2")
    @Transactional
    public void receiver(DelayOrder delayOrder) {
        if(System.currentTimeMillis() < delayOrder.getExpireTime()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("还未到期");
            orderExpireProducer.sender(delayOrder);
        } else {
            Order order = delayOrder.getBuy().getOrder();
            Good good = delayOrder.getBuy().getGood();
            int count = (int) (order.getTotalPrice() / good.getPrice());
            good.setStock(good.getStock() + count);
            goodService.updateGood(good.getId(), good);
            // java代码中没有对新建立的order的id进行设置（因为在DB中id是自增主键）所以order的id为0 因此在DB中找不到order id为0的记录进行更新
            // 因为id在DB中是自增主键 会给id赋值 所以可以将DB中的order取出进行字段更新
            Order order1 = orderMapper.queryByIds(order.getUserId(), order.getGoodId());
            order1.setStatus(OrderStatusEnum.CANCELLED.getCode());
            orderMapper.update(order1);
            logger.info("订单过期且取消成功");
        }
    }
}
