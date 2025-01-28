package com.example.demo.integration;

import com.example.demo.controller.vo.BaseVO;
import com.example.demo.integration.cmd.EmailNotificationCmd;
import com.example.demo.integration.cmd.EmailUnpaidNotificationCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class EmailIntegration {
    @Autowired
    private RestTemplate restTemplate;

    public void sendEmail(EmailNotificationCmd emailNotificationCmd) {
        // 通过restTemplate调下游发邮件的接口 完成发邮件 同步
        String url = "http://localhost:8081/email/notification/send";
        // 原数据信息：对body的声明 约束
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailNotificationCmd> request = new HttpEntity<>(emailNotificationCmd, headers);
        ResponseEntity<BaseVO> exchange = restTemplate.exchange(url, HttpMethod.POST, request, BaseVO.class);
    }

    public void sendUnpaidEmail(EmailUnpaidNotificationCmd  emailUnpaidNotificationCmd) {
        // 通过restTemplate调下游发邮件的接口 完成发邮件 同步
        String url = "http://localhost:8081/email/notification/send/unpaid/notification";
        // 原数据信息：对body的声明 约束
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailUnpaidNotificationCmd> request = new HttpEntity<>(emailUnpaidNotificationCmd, headers);
        ResponseEntity<BaseVO> exchange = restTemplate.exchange(url, HttpMethod.POST, request, BaseVO.class);
    }

}
