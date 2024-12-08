package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserTokenRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public UserToken loadToken(String userTokenHash, int userId) {
        return (UserToken) redisTemplate.opsForHash().get(userTokenHash, userId);
    }

    public void saveToken(String userTokenHash, int userId, UserToken userToken) {
        redisTemplate.opsForHash().putIfAbsent(userTokenHash, userId, userToken);
    }

    public void updateToken(String userTokenHash, int userId, UserToken userToken) {
        redisTemplate.opsForHash().put(userTokenHash, userId, userToken);
    }
}
