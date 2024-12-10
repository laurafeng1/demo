package com.example.demo.repository;

import com.example.demo.entity.UserSubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSubscribeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addSubscribe(int userId, UserSubscribe value) {
        redisTemplate.opsForList().leftPush(buildKey(userId), value);
    }

    public List<UserSubscribe> showSubscribe(int userId, UserSubscribe value) {
        return redisTemplate.opsForList().range(buildKey(userId), 0, -1);
    }

    public String buildKey(int userId) {
        return "subscribe:" + userId;
    }
}
