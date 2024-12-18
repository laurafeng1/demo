package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameScoreEnum {
    INCREASEMENT("INCREASEMENT", "分数增加"),
    DECREASEMENT("DECREASEMENT", "分数减少");

    private String code;
    private String desc;
}
