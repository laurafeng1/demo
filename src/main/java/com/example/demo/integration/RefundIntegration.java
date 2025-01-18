package com.example.demo.integration;

import com.example.demo.controller.vo.PayVO;
import com.example.demo.exception.PayNoRetryException;
import com.example.demo.exception.PayRetryExeption;
import com.example.demo.exception.RefundNoRetryException;
import com.example.demo.exception.RefundRetryException;
import com.example.demo.integration.cmd.PayCmd;
import com.example.demo.integration.cmd.RefundCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
@Repository
public class RefundIntegration {
    @Autowired
    private RestTemplate restTemplate;

    public int refundIntegration(RefundCmd refundCmd) {
        String url = "http://localhost:8081/account/refund";
        // 原数据信息：对body的声明 约束
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RefundCmd> request = new HttpEntity<>(refundCmd, headers);
        ResponseEntity<PayVO> exchange = restTemplate.exchange(url, HttpMethod.POST, request, PayVO.class);

        //不同错误码不同含义不同约束
        if(exchange.getBody().getBaseVO().getCode() == 503){
            throw new RefundNoRetryException("不可重试异常");
        } else if (exchange.getBody().getBaseVO().getCode() == 500){
            throw new RefundRetryException("可重试异常");
        }
        return exchange.getBody().getUserId();
    }
}
