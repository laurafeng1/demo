package com.example.demo.controller.converter;

import com.example.demo.controller.vo.UserVO;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserVOConverter {
    public static UserVO convertUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setName(user.getName());
        vo.setAge(user.getAge());
        vo.setGender(user.getGender());
        vo.setJob(user.getJob());
        vo.setPassword(user.getPassword());
        vo.setScore(user.getScore());
        return vo;
    }

    public static List<UserVO> convertUserVOList(List<User> users) {
        List<UserVO> userVOList = new ArrayList<>();
        for(User user : users) {
            UserVO userVO = convertUserVO(user);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    public static Set<UserVO> convertUserVOSet(Set<User> users) {
        Set<UserVO> userVOSet = new HashSet<>();
        for(User user : users) {
            UserVO userVO = convertUserVO(user);
            userVOSet.add(userVO);
        }
        return userVOSet;
    }

    public static List<UserVO> convertUserVOList(Set<User> users) {
        List<UserVO> userVOList = new ArrayList<>();
        for(User user : users) {
            UserVO userVO = convertUserVO(user);
            userVOList.add(userVO);
        }
        return userVOList;
    }
}
