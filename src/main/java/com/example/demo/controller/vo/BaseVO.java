package com.example.demo.controller.vo;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseVO {
    private int code;

    private long time;

    private boolean success;

    private String errorMsg;

    public static BaseVO buildBaseVo(int code, long time, boolean success, String errorMsg) {
        BaseVO baseVO = new BaseVO();
        baseVO.setCode(code);
        baseVO.setTime(time);
        baseVO.setSuccess(success);
        baseVO.setErrorMsg(errorMsg);
        return baseVO;
    }
}
