package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    MALE("0", "男"),
    FEMALE("1", "女");

    private String code;
    private String desc;
}
