package com.example.demo.service.impl;

import com.example.demo.entity.Buy;
import com.example.demo.entity.Good;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.exception.PayNoRetryException;
import com.example.demo.exception.PayRetryExeption;
import com.example.demo.exception.RefundNoRetryException;
import com.example.demo.exception.RefundRetryException;
import com.example.demo.integration.PayIntegration;
import com.example.demo.integration.RefundIntegration;
import com.example.demo.integration.cmd.PayCmd;
import com.example.demo.integration.cmd.RefundCmd;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.sql.SQLOutput;

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

    @Autowired
    private DelayExampleProducer delayExampleProducer;

    @Autowired
    private PayIntegration payIntegration;

    @Autowired
    private RefundIntegration refundIntegration;

    @Autowired
    private RefundProducer refundProducer;

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
    public int pay(int orderId) {
        try {
            return payWithoutException(orderId);
            // 不同异常需要做不同处理
            // 需重试的异常则重试支付，不需要的则进行其他操作
            // 比如数据库冲突（唯一键冲突）时，不需要重试 因为重试不能解决问题
            // 接口文档：输入，输出，可能出现的异常
            // 根据错误码 进行异常分类 决定是否重试
        } catch (PayNoRetryException e) {
            logger.error("支付失败，不进行重试");
            throw new PayNoRetryException("支付失败");
        } catch(PayRetryExeption e) { //runtime exception 1 + 2
            logger.error("支付失败，进行重试");
            Order order = orderMapper.queryById(orderId);
            payProducer.sender(order);
            throw new PayRetryExeption("支付失败");
        } catch (Exception e) {
            logger.error("支付失败，不进行重试");
            throw new PayNoRetryException("支付失败");
        }
    }

    @Override
    @Transactional
    public int payWithoutException(int orderId) {
        Order order = orderMapper.queryById(orderId);
        PayCmd payCmd = new PayCmd();
        payCmd.setUserId(order.getUserId());
        payCmd.setAmount(order.getTotalPrice());
        int userId = payIntegration.payIntegration(payCmd);
        logger.info(String.format("用户%d支付成功", userId));
        order.setStatus(OrderStatusEnum.PAID.getCode());
        // 应设置orderService 并用其中的update方法更新
        orderMapper.update(order);
        return userId;
    }

    @Override
    public int refund(int orderId) {
        try {
            return refundWithoutException(orderId);
        } catch (RefundNoRetryException e) {
            logger.error("订单不可退款，不可重试");
            throw new RefundNoRetryException("订单不可退款，不可重试");
        } catch(RefundRetryException e) {
            logger.error("订单退款，可重试");
            Order order = orderMapper.queryById(orderId);
            refundProducer.sender(order);
            throw new RefundRetryException("订单退款，可重试");
        }
    }

    @Override
    @Transactional
    public int refundWithoutException(int orderId) {
        Order order = orderMapper.queryById(orderId);
        if(!order.getStatus().equals(OrderStatusEnum.PAID.getCode())) {
            logger.warn("不该给该订单退款");
            return 0;
        }
        RefundCmd refundCmd = new RefundCmd();
        refundCmd.setUserId(order.getUserId());
        refundCmd.setAmount(order.getTotalPrice());
        //退钱
        int userId = refundIntegration.refundIntegration(refundCmd);
        //该订单状态
        order.setStatus(OrderStatusEnum.REFUNDED.getCode());
        orderMapper.update(order);
        return userId;
    }


}
