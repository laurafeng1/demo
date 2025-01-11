package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    INIT("INIT", "初始化"),
    PAID("PAID", "已支付"),
    COMPLETED("COMPLETED", "订单完成"),
    CANCELLED("CANCELLED", "订单取消");

    private String code;
    private String desc;
}
