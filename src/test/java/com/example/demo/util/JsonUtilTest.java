package com.example.demo.util;

import com.example.demo.entity.User;
import org.junit.Test;

public class JsonUtilTest {
    @Test
    public void testJsonUtil() {
        User user = new User();
        user.setId(1);
        user.setName("aaa");
        user.setPassword("123aaa");
        user.setAge(22);
        user.setGender("1");
        user.setJob("java");
        String json = "{\"id\": 2, \"name\" : \"bbb\", \"gender\" : 0}";
        System.out.println(JsonUtil.readFromJson(json, User.class));
        System.out.println(JsonUtil.writeWithJsonFormat(user));
    }
}