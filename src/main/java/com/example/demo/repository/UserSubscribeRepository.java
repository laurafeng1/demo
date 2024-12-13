package com.example.demo.repository;

import com.example.demo.entity.UserSubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSubscribeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addSubscribe(int userId, UserSubscribe value) {
        buildListOperation().leftPush(buildKey(userId), value);
    }

    public List<UserSubscribe> showSubscribe(int userId) {
        return buildListOperation().range(buildKey(userId), 0, -1);
    }

    public void removeSubscribe(int userId, long count, UserSubscribe value) {
        buildListOperation().remove(buildKey(userId), count, value);
    }

//    public void updateAllSubscribe(int userId, List<UserSubscribe> values) {
//        redisTemplate.delete(buildKey(userId));
//        redisTemplate.opsForList().leftPushAll(buildKey(userId), values);
//    }

    // keep consistency of keys
    private String buildKey(int userId) {
        return "subscribe:" + userId;
    }

    private ListOperations<String, UserSubscribe> buildListOperation() {
        return redisTemplate.opsForList();
    }
}
