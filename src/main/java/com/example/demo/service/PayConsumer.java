package com.example.demo.service;


import com.example.demo.entity.Order;
import com.example.demo.exception.PayRetryExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
public class PayConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PayProducer payProducer;

    @Autowired
    private BuyService buyService;

    Logger logger = LoggerFactory.getLogger(PayConsumer.class);

    // 收到就是没支付成功
    @RabbitListener(queues = "MY_QUEUE6")
    @Retryable(value = {PayRetryExeption.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    public void receiver(Order order) {
        logger.info("重试");
        buyService.payWithoutException(order.getId());
    }

    @Recover
    public void recover() {
        logger.error("执行失败");
    }
}
