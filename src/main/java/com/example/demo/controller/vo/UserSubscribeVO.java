package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserSubscribeVO {
    private int userId;
    private String userName;
    private Date subscribeTime;
}
