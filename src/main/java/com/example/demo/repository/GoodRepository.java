package com.example.demo.repository;

import com.example.demo.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GoodRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    public void addGood(int id, Good good) {
        redisTemplate.opsForValue().set(buildKey(id), good);
    }

    public void deleteGood(int id) {
        redisTemplate.opsForValue().getAndDelete(buildKey(id));
    }

    public Good searchGood(int id) {
        return (Good) redisTemplate.opsForValue().get(buildKey(id));
    }

    private String buildKey(int id) {
        return "Good:" + id;
    }
}
