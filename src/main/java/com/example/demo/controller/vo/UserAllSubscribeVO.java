package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserAllSubscribeVO {
    private int totalSubscribe;
    private List<UserSubscribeVO> userSubscribeVOList;
    private BaseVO baseVO;

    public static UserAllSubscribeVO buildUserAllSubscribeVO(int totalSubscribe, List<UserSubscribeVO> userSubscribeVOList, BaseVO baseVO) {
        UserAllSubscribeVO userAllSubscribeVO = new UserAllSubscribeVO();
        userAllSubscribeVO.setTotalSubscribe(totalSubscribe);
        userAllSubscribeVO.setUserSubscribeVOList(userSubscribeVOList);
        userAllSubscribeVO.setBaseVO(baseVO);
        return userAllSubscribeVO;
    }
}
