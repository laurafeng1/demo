package com.example.demo.service.impl;

import com.example.demo.entity.Buy;
import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyServiceImpl implements BuyService {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodService goodService;

    @Autowired
    private GoodProducer goodProducer;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private BuyProducer buyProducer;

    @Autowired
    private PayProducer payProducer;

    final static Logger logger = LoggerFactory.getLogger(BuyServiceImpl.class);

    @Override
    @Transactional
    public void select(int userId, int goodId, int count) {
        // 加缓存
        // 商品一定要加缓存，全平台用户都会查商品，尤其在秒杀场景下
        if(userMapper.findById(userId) == null) {
            throw new RuntimeException("该用户不存在");
        }
        Good good = goodService.getGood(goodId);
        if(good.getStock() < count) {
            throw new RuntimeException("该商品库存不足");
        }
        // 在DB加外健约束
        Order order = new Order();
        order.setUserId(userId);
        order.setGoodId(goodId);
        order.setStatus(OrderStatusEnum.INIT.getCode());
        order.setTotalPrice(good.getPrice() * count);
        // orderMapper.add(order);
        // orderProducer.sender("MY_EXCHANGE", "MY_ROUTING", order);
        good.setStock(good.getStock() - count);
        // goodMapper.update(good);
        // goodProducer.sender("MY_EXCHANGE", "MY_ROUTING", good);
        Buy buy = new Buy();
        buy.setOrder(order);
        buy.setGood(good);
        buyProducer.sender(buy);
    }

    @Override
    @Transactional
    public void pay(int orderId) {
        try {
            payWithoutException(orderId);
            logger.info("支付成功");
            // 不同异常需要做不同处理
            // 需重试的异常则重试支付，不需要的则进行其他操作
            // 比如数据库冲突（唯一键冲突）时，不需要重试 因为重试不能解决问题
            // 接口文档：输入，输出，可能出现的异常
        } catch(Exception e) {
            logger.error("支付失败");
            Order order = orderMapper.queryById(orderId);
            payProducer.sender(order);
        }
    }

    @Override
    public void payWithoutException(int orderId) {
        Order order = orderMapper.queryById(orderId);
        order.setStatus(OrderStatusEnum.PAID.getCode());
        // 应设置orderService 并用其中的update方法更新
        orderMapper.update(order);
        logger.info("支付成功");
    }
}
