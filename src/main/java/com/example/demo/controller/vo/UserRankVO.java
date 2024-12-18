package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserRankVO {
    private List<UserVO> userVOs;
    private BaseVO baseVO;
}
