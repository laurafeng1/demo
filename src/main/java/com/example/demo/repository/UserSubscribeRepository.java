package com.example.demo.repository;

import com.example.demo.entity.UserSubscribe;
import com.example.demo.enums.SubscribeEum;
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
        buildListOperation().leftPush(buildKey(userId, SubscribeEum.SUBSCRIBE), value);
    }

    public List<UserSubscribe> showSubscribe(int userId) {
        return buildListOperation().range(buildKey(userId, SubscribeEum.SUBSCRIBE), 0, -1);
    }

    public void removeSubscribe(int userId, long count, UserSubscribe value) {
        buildListOperation().remove(buildKey(userId, SubscribeEum.SUBSCRIBE), count, value);
    }

//    public void updateAllSubscribe(int userId, List<UserSubscribe> values) {
//        redisTemplate.delete(buildKey(userId));
//        redisTemplate.opsForList().leftPushAll(buildKey(userId), values);
//    }

    public void addSubscribed(int userId, UserSubscribe value) {
        buildListOperation().leftPush(buildKey(userId, SubscribeEum.SUBSCRIBED), value);
    }

    public List<UserSubscribe> showSubscribed(int userId) {
        return buildListOperation().range(buildKey(userId, SubscribeEum.SUBSCRIBED), 0, -1);
    }

    public void removeSubscribed(int userId, long count, UserSubscribe value) {
        buildListOperation().remove(buildKey(userId, SubscribeEum.SUBSCRIBED), count, value);
    }

    // keep consistency of keys
    private String buildKey(int userId, SubscribeEum status) {
        // 枚举的比价用 == ，常量
        if (status == SubscribeEum.SUBSCRIBE) {
            return "subscribe:" + userId;
        } else {
            return "subscribed:" + userId;
        }
    }

    private ListOperations<String, UserSubscribe> buildListOperation() {
        return redisTemplate.opsForList();
    }
}
