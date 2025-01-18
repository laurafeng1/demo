package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/two/service")
public class TwoServiceController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get")
    public void getInfo() {
        // 同步：通过API调用
        // 异步：通过消息队列发出请求，网址写进配置文件


        String url = "http://localhost:8081/account/pay";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String requestBody = "{\"userId\":1, \"balance\":30}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        restTemplate.postForObject(url, request, String.class);
        System.out.println(restTemplate.postForObject(url, request, String.class));
    }
}
