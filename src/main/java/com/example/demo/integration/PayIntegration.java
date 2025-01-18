package com.example.demo.integration;

import com.example.demo.exception.PayNoRetryException;
import com.example.demo.exception.PayRetryExeption;
import com.example.demo.integration.cmd.PayCmd;
import com.example.demo.controller.vo.PayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PayIntegration {
    @Autowired
    private RestTemplate restTemplate;


    public int payIntegration(PayCmd payCmd) {
        String url = "http://localhost:8081/account/pay";
        // 原数据信息：对body的声明 约束
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PayCmd> request = new HttpEntity<>(payCmd, headers);
        ResponseEntity<PayVO> exchange = restTemplate.exchange(url, HttpMethod.POST, request, PayVO.class);

        //不同错误码不同含义不同约束
        if(exchange.getBody().getBaseVO().getCode() == 501 || exchange.getBody().getBaseVO().getCode() == 502){
            throw new PayNoRetryException("不可重试异常");
        } else if (exchange.getBody().getBaseVO().getCode() == 500){
            throw new PayRetryExeption("可重试异常");
        }
        return exchange.getBody().getUserId();
    }

}
