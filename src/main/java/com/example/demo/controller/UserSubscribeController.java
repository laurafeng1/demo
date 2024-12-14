package com.example.demo.controller;

import com.example.demo.controller.cmd.AddSubscribeCmd;
import com.example.demo.controller.cmd.CancelSubscribeCmd;
import com.example.demo.controller.converter.UserSubscribeVOConverter;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.UserAllSubscribeVO;
import com.example.demo.controller.vo.UserSubscribeVO;
import com.example.demo.entity.UserSubscribe;
import com.example.demo.exception.UserRegisterFailedException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
// controller的入参和出参都不可以是entity对象
@RestController
@RequestMapping("/user/subscription")
public class UserSubscribeController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public BaseVO addSubscribe(@RequestBody AddSubscribeCmd addSubscribeCmd){
        long start = System.currentTimeMillis();
        long end = 0;
        try{
            userService.addUserSubscribe(addSubscribeCmd.getSubed(), addSubscribeCmd.getSub());
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (UserRegisterFailedException e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, e.getMessage());
        }

    }

    @GetMapping("/listSubed")
    public UserAllSubscribeVO listSubscribed(int userId) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            List<UserSubscribe> userSubscribes = userService.showUserSubscribe(userId);
            List<UserSubscribeVO> userSubscribeVOS = UserSubscribeVOConverter.convertToVOList(userSubscribes);
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(userSubscribes.size(), userSubscribeVOS, BaseVO.buildBaseVo(200, end - start, true, null));
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(0, new ArrayList<>(), BaseVO.buildBaseVo(500, end - start, false, "查询关注列表失败"));
        }
    }

    @GetMapping("/listSub")
    public UserAllSubscribeVO listSubscribe(int userId) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            List<UserSubscribe> userSubscribes = userService.showUserSubscribed(userId);
            List<UserSubscribeVO> userSubscribeVOS = UserSubscribeVOConverter.convertToVOList(userSubscribes);
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(userSubscribes.size(), userSubscribeVOS, BaseVO.buildBaseVo(200, end - start, true, null));
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return UserAllSubscribeVO.buildUserAllSubscribeVO(0, new ArrayList<>(), BaseVO.buildBaseVo(500, end - start, false, "查询关注列表失败"));
        }
    }

    @DeleteMapping("/delete")
    public BaseVO removeSubscribe (@RequestBody CancelSubscribeCmd cancelSubscribeCmd) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            userService.cancelUserSubscribe(cancelSubscribeCmd.getSubed(), cancelSubscribeCmd.getSub());
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "取消关注失败");
        }

    }



}
