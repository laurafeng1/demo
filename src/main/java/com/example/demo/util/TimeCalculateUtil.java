package com.example.demo.util;

import com.example.demo.constant.DemoConstant;

import java.util.Date;

public class TimeCalculateUtil {
    public static Date calculateExpireTime() {
        return new Date(System.currentTimeMillis() + DemoConstant.TOKEN_EXPIRE_INTERVAL);
    }
}
