package com.example.demo.controller;

import com.example.demo.controller.vo.RefundVO;
import com.example.demo.entity.Order;
import com.example.demo.exception.PayNoRetryException;
import com.example.demo.exception.PayRetryExeption;
import com.example.demo.exception.RefundNoRetryException;
import com.example.demo.exception.RefundRetryException;
import com.example.demo.integration.cmd.PayCmd;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.PayVO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.BuyService;
import com.example.demo.service.DelayExampleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Ref;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DelayExampleProducer delayExampleProducer;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/select")
    public BaseVO select(int userId, int goodId, int count) {
        long start = System.currentTimeMillis();
        long end;
        try {
            buyService.select(userId, goodId, count);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "下单失败");
        }
    }

    @PostMapping("/pay/without/another/service")
    public BaseVO payWithout(int orderId) {
        long start = System.currentTimeMillis();
        long end;
        try {
            buyService.pay(orderId);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "支付失败");
        }
    }

    @GetMapping("/pay")
    public PayVO pay(int orderId) {
        // 同步：通过API调用
        // 异步：通过消息队列发出请求，网址写进配置文件
        PayVO payVO = new PayVO();
        long start = System.currentTimeMillis();
        long end;
        try {
            int userId = buyService.pay(orderId);
            payVO.setUserId(userId);
            end = System.currentTimeMillis();
            payVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return payVO;
        } catch (PayRetryExeption e) {
            end = System.currentTimeMillis();
            payVO.setBaseVO(BaseVO.buildBaseVo(501, end - start, false, "支付失败，正在重试"));
            return payVO;
        } catch (PayNoRetryException e) {
            end = System.currentTimeMillis();
            payVO.setBaseVO(BaseVO.buildBaseVo(502, end - start, false, "支付失败，不可重试"));
            return payVO;
        } catch (Exception e) {
            end = System.currentTimeMillis();
            payVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "未知异常，不可重试"));
            return payVO;
        }
    }

    @GetMapping("/refund")
    public RefundVO refund(int orderId) {
        // 同步：通过API调用
        // 异步：通过消息队列发出请求，网址写进配置文件
        RefundVO refundVO = new RefundVO();
        long start = System.currentTimeMillis();
        long end;
        try {
            int userId = buyService.refund(orderId);
            refundVO.setUserId(userId);
            end = System.currentTimeMillis();
            refundVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return refundVO;
        } catch (RefundRetryException e) {
            end = System.currentTimeMillis();
            refundVO.setBaseVO(BaseVO.buildBaseVo(503, end - start, false, "退款失败，正在重试"));
            return refundVO;
        } catch (RefundNoRetryException e) {
            end = System.currentTimeMillis();
            refundVO.setBaseVO(BaseVO.buildBaseVo(504, end - start, false, "退款失败，不可重试"));
            return refundVO;
        } catch (Exception e) {
            end = System.currentTimeMillis();
            refundVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "未知异常，不可重试"));
            return refundVO;
        }
    }
}
