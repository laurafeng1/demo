package com.example.demo.mapper;

import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    void add(Order order);

    void update(Order order);

    Order queryById(int id);

    List<Order> queryAll();

    Order queryByIds(int userId, int goodId);
}
