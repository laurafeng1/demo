package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterEnum {
    DAY("DAY", 3, "DAY"),
    WEEK("WEEK", 2, "WEEK"),
    MONTH("MONTH", 1, "MONTH");

    private String code;
    private int paramCount;
    private String desc;
}