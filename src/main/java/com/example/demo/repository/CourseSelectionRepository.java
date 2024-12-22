package com.example.demo.repository;

import com.example.demo.entity.CourseSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseSelectionRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    private String buildKey(int id) {
        return "CourseSelection:" + id;
    }

    private String buildIdKey(int stuId, int courId) {
        return "CourseSelection:" + stuId + ":" + courId;
    }

    public CourseSelection searchCourseSelection(int id) {
        return (CourseSelection) redisTemplate.opsForValue().get(buildKey(id));
    }

    public void insertCourseSelection(int id, CourseSelection courseSelection) {
        redisTemplate.opsForValue().set(buildKey(id), courseSelection);
    }


    public void deleteCourseSelection(int id) {
        redisTemplate.opsForValue().getAndDelete(buildKey(id));
    }

    public CourseSelection searchCourseSelectionByIds(int stuId, int courId) {
        return (CourseSelection) redisTemplate.opsForValue().get(buildIdKey(stuId, courId));
    }

    public void insertCourseSelectionByIds(int stuId, int courId, CourseSelection courseSelection) {
        redisTemplate.opsForValue().set(buildIdKey(stuId, courId), courseSelection);
    }


    public void deleteCourseSelectionByIds(int stuId, int courId) {
        redisTemplate.opsForValue().getAndDelete(buildIdKey(stuId, courId));
    }

}
