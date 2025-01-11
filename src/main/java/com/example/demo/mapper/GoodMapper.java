package com.example.demo.mapper;

import com.example.demo.entity.Good;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodMapper {
    Good queryById(int id);

    void update(Good good);

    void add(Good good);

    void deleteById(int id);
}
