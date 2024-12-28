package com.example.demo.repository;

import com.example.demo.enums.RegisterEnum;
import com.example.demo.util.TimeCalculateUtil;
import com.example.demo.util.TimeConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserRegisterRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    public void updateUserRegisterDay(int userId, boolean value) {
        redisTemplate.opsForValue().setBit(buildKey(userId, TimeCalculateUtil.calculateMonthOfYear(), TimeCalculateUtil.calculateWeekOfMonth(), TimeCalculateUtil.calculateDayOfWeek()), TimeCalculateUtil.calculateDayOfWeek(), value);
    }

    public void updateUserRegisterWeek(int userId, boolean value) {
        redisTemplate.opsForValue().setBit(buildKey(userId, TimeCalculateUtil.calculateMonthOfYear(), TimeCalculateUtil.calculateWeekOfMonth()), TimeCalculateUtil.calculateWeekOfMonth(), value);
    }

    public void updateUserRegisterMonth(int userId, boolean value) {
        redisTemplate.opsForValue().setBit(buildKey(userId, TimeCalculateUtil.calculateMonthOfYear()), TimeCalculateUtil.calculateMonthOfYear(), value);
    }

    public boolean checkUserRegister(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek, long offset) {
        return redisTemplate.opsForValue().getBit(buildKey(userId, monthOfYear, weekOfMonth, dayOfWeek), offset);
    }

    // For MONTHOFYEAR
    public long countUserRegister(int userId, int monthOfYear) {
        String key = buildKey(userId, monthOfYear);
        return executeRedisCommand(key);
    }

    // For WEEKOFMONTH
    public long countUserRegister(int userId, int monthOfYear, int weekOfMonth) {
        String key = buildKey(userId, monthOfYear, weekOfMonth);
        return executeRedisCommand(key);
    }

    // For DAYOFWEEK
    public long countUserRegister(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek) {
        String key = buildKey(userId, monthOfYear, weekOfMonth, dayOfWeek);
        return executeRedisCommand(key);
    }

    private long executeRedisCommand(String key) {
        Object count = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = redisTemplate.getStringSerializer().serialize(key);
                return connection.bitCount(bytes);
            }
        });
        if(count == null) {
            return 0;
        }
        if (count instanceof Long) {
            return (Long) count;
        }
        return 0;
    }

    public void updateRegisterCompany(long offset, boolean value) {
        redisTemplate.opsForValue().setBit(buildKeyCompany(new Date()), offset, value);
    }

    public boolean checkUserRegisterCompany(Date date, long offset) {
        return redisTemplate.opsForValue().getBit(buildKeyCompany(new Date()), offset);
    }

    public long countRegisterCompany(Date date) {
        String key = buildKeyCompany(date);
        return executeRedisCommand(key);
    }

    // For monthOfYear
    private String buildKey(int userId, int monthOfYear) {
        return "UserRegister:monthOfYear:" + monthOfYear + ":userId:" + userId;
    }

    // For monthOfYear:weekOfMonth
    private String buildKey(int userId, int monthOfYear, int weekOfMonth) {
        return "UserRegister:monthOfYear:" + monthOfYear + ":weekOfMonth:" + weekOfMonth + ":userId:" + userId;
    }

    // For monthOfYear:weekOfMonth:dayOfWeek
    private String buildKey(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek) {
        return "UserRegister:monthOfYear:" + monthOfYear + ":weekOfMonth:" + weekOfMonth + ":dayOfWeek:" + dayOfWeek + ":userId:" + userId;
    }

    private String buildKeyCompany(Date date) {
        return "UserRegister:" + TimeConvertUtil.formatDay(date);
    }
}
