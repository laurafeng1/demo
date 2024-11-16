package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseVO {
    private int code;

    private long time;

    private boolean success;

    private String errorMsg;
}
