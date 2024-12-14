package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubscribeEum {
    // 枚举的名字和code保持一致 用大写
    SUBSCRIBE("SUBSCRIBE", "关注"),
    SUBSCRIBED("SUBSCRIBED", "被关注");

    private String code;
    private String desc;
}
