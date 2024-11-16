package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {
        String name = "aaAaaa0a";
        String password = "aaAaaa0a";
        int age = 13;
        String gender = "1";
        String job = "IT";

        userService.register(name, password, age, gender, job);
    }

    @Test
    public void testLogin() {
        String name = "aaAaaa0a";
        String password = "aaAaaa0a";

        userService.login(name, password);
    }
}