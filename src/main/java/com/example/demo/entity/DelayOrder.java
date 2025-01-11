package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DelayOrder {

    private Buy buy;

    private long expireTime;
}
