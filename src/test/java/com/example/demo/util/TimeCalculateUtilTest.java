package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeCalculateUtilTest {

    @Test
    void calculateDayOfWeek() {
        System.out.println(TimeCalculateUtil.calculateDayOfWeek());
    }

    @Test
    void calculateWeekOfMonth() {
        System.out.println(TimeCalculateUtil.calculateWeekOfMonth());
    }

    @Test
    void calculateMonthOfYear() {
        System.out.println(TimeCalculateUtil.calculateMonthOfYear());
    }
}