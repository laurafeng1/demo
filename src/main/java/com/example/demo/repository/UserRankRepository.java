package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserRankRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    public void addUser(int gameId, User user, double score) {
        buildZSetOperations().addIfAbsent(buildKey(gameId), user, score);
    }

    public void removeUser(int gameId, User user) {
        buildZSetOperations().remove(buildKey(gameId), user);
    }

    public void scoreIncreasement(int gameId, User user, double delta) {
        buildZSetOperations().incrementScore(buildKey(gameId), user, delta);
    }

    public void scoreDecreasement(int gameId, User user, double delta) {
        buildZSetOperations().incrementScore(buildKey(gameId), user, -delta);
    }

    public Set<User> listByRank(int gameId, int start, int end) {
        return buildZSetOperations().range(buildKey(gameId), start, end);
    }

    public Set<User> listByScore(int gameId, double min, double max) {
        return buildZSetOperations().rangeByScore(buildKey(gameId), min, max);
    }

    private ZSetOperations<String, User> buildZSetOperations() {
        return redisTemplate.opsForZSet();
    }

    private String buildKey(int gameId) {
        return "game:rank:" + gameId;
    }
}
