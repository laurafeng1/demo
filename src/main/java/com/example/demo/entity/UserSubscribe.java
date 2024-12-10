package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSubscribe {
    private int userId;
    private String userName;
    private Date subscribeTime;
}
