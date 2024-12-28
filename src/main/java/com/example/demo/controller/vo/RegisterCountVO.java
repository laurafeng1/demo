package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterCountVO {
    private long count;

    private String type;

    private BaseVO baseVO;
}
