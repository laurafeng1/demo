package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 注解
@Mapper
// 1. 项目启动的时候将{"userMapper": new UserMapper()}
public interface UserMapper {
    User findById(int id);
    User findByName(String name);
    List<User> findAll();
    void add(User user);
}
