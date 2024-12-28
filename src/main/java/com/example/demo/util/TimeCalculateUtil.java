package com.example.demo.util;

import com.example.demo.constant.DemoConstant;

import java.util.Calendar;
import java.util.Date;

public class TimeCalculateUtil {
    public static Date calculateExpireTime() {
        return new Date(System.currentTimeMillis() + DemoConstant.TOKEN_EXPIRE_INTERVAL);
    }

    public static int calculateDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int count = calendar.get(Calendar.DAY_OF_WEEK);
        return count;
    }

    public static int calculateWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int count = calendar.get(Calendar.WEEK_OF_MONTH);
        return count;
    }

    public static int calculateMonthOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int count = calendar.get(Calendar.MONTH) + 1;
        return count;
    }
}
