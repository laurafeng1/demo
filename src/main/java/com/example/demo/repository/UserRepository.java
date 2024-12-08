package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public void saveUserName(String key, String value, Integer ttl) {
        if (ttl == null) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl);
        }
    }

    public void saveUser(String key, User value, Integer ttl) {
        if (ttl == null) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl);
        }
    }

    public String loadUserName(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public User loadUser(String key) {
        return (User) redisTemplate.opsForValue().get(key);
    }
}
