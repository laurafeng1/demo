package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class User {
    private int id;

    private String name;

    private String password;

    private int age;

    private String gender;

    private String job;

    public static User createByString (List<String> strs){
        User user = new User();
        user.setId(Integer.parseInt(strs.get(0)));
        user.setName(strs.get(1));
        user.setPassword(strs.get(2));
        user.setAge(Integer.parseInt(strs.get(3)));
        user.setGender(strs.get(4));
        user.setJob(strs.get(5));
        return user;
    }
}
