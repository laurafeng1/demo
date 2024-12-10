package com.example.demo.controller;

import com.example.demo.controller.converter.UserSubscribeVOConverter;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.UserAllSubscribeVO;
import com.example.demo.controller.vo.UserSubscribeVO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserSubscribe;
import com.example.demo.exception.UserRegisterFailedException;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/subscription")
public class UserSubscribeController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public BaseVO addSubscribe(@RequestBody User user){
        long start = System.currentTimeMillis();
        long end = 0;
        try{
            userService.addUserSubscribe(user);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (UserRegisterFailedException e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, e.getMessage());
        }

    }

    @GetMapping("/list")
    public UserAllSubscribeVO listSubscribe(@RequestBody User user) {
        UserAllSubscribeVO userAllSubscribeVO = new UserAllSubscribeVO();
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            List<UserSubscribe> userSubscribes = userService.showUserSubscribe(user);
            List<UserSubscribeVO> userSubscribeVOS = UserSubscribeVOConverter.convertToVOList(userSubscribes);
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(userSubscribes.size(), userSubscribeVOS, BaseVO.buildBaseVo(200, end - start, true, null));
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(0, new ArrayList<>(), BaseVO.buildBaseVo(500, end - start, false, "查询关注列表失败"));
        }

    }



}
