package com.example.demo.controller;

import com.example.demo.controller.vo.BaseVO;
import com.example.demo.service.BuyService;
import com.example.demo.service.DelayExampleProducer;
import org.apache.ibatis.executor.BaseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;

    @Autowired
    private DelayExampleProducer delayExampleProducer;

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

    @PostMapping("/pay")
    public BaseVO pay(int orderId) {
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

    @PostMapping("/test")
    public BaseVO test() {
        long start = System.currentTimeMillis();
        long end;
        try {
            delayExampleProducer.sender();
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "延迟消息测试失败");
        }
    }
}
