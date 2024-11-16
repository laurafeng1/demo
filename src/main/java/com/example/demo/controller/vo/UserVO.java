package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
    private int id;

    private String name;

    private String password;

    private int age;

    private String gender;

    private String job;
}
