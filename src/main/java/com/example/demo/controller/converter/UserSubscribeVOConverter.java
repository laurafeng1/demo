package com.example.demo.controller.converter;

import com.example.demo.controller.vo.UserSubscribeVO;
import com.example.demo.entity.UserSubscribe;

import java.util.ArrayList;
import java.util.List;

public class UserSubscribeVOConverter {
    public static UserSubscribeVO convertToVO(UserSubscribe userSubscribe) {
        UserSubscribeVO userSubscribeVO = new UserSubscribeVO();
        userSubscribeVO.setUserId(userSubscribe.getUserId());
        userSubscribeVO.setSubscribeTime(userSubscribe.getSubscribeTime());
        userSubscribeVO.setUserName(userSubscribe.getUserName());
        return userSubscribeVO;
    }

    public static List<UserSubscribeVO> convertToVOList(List<UserSubscribe> userSubList) {
        List<UserSubscribeVO> convertedList = new ArrayList<>();
        for(UserSubscribe userSubscribe : userSubList){
            convertedList.add(convertToVO(userSubscribe));
        }
        return convertedList;

    }
}
