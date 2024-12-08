package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserName() {
        userRepository.saveUserName("name", "aaa", null);
    }

    @Test
    public void loadUserName() {
        System.out.println(userRepository.loadUserName("name"));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setId(1001);
        user.setName("aaa");
        user.setAge(22);
        user.setGender("1");
        user.setJob("java");
        userRepository.saveUser("user", user, null);
    }

    @Test
    public void loadUser() {
        System.out.println(userRepository.loadUser("user"));
    }
}