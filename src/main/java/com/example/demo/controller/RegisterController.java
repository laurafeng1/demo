package com.example.demo.controller;

import com.example.demo.controller.cmd.RegisterCmd;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.CheckRegisterVO;
import com.example.demo.controller.vo.RegisterCountVO;
import com.example.demo.service.RegisterService;
import com.example.demo.service.impl.RegisterMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/user/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterMessageSender registerMessageSender;



    @PostMapping("/sign-in")
    public BaseVO signIn(int userId) {
        long start = System.currentTimeMillis();
        long end;

        try {
            registerService.signIn(userId);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "用户签到失败");
        }
    }

    @PostMapping("/cancel/sign-in")
    public BaseVO cancelSignIn(int userId) {
        long start = System.currentTimeMillis();
        long end;
        try{
            registerService.cancelSignIn(userId);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "用户取消签到失败");
        }
    }

    @GetMapping("/check/sign-in")
    public CheckRegisterVO checkSignIn(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek) {
        long start = System.currentTimeMillis();
        long end;
        CheckRegisterVO checkRegisterVO = new CheckRegisterVO();
        try{
            boolean checkResult = registerService.checkUserSignIn(userId, monthOfYear, weekOfMonth, dayOfWeek);
            checkRegisterVO.setHasSignedIn(checkResult);
            end = System.currentTimeMillis();
            checkRegisterVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return checkRegisterVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            checkRegisterVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "检查用户状态失败"));
            return checkRegisterVO;
        }
    }

    @GetMapping("/count/sign-in")
    public RegisterCountVO countSignIn(@RequestBody RegisterCmd registerCmd) {
        long start = System.currentTimeMillis();
        long end;
        RegisterCountVO registerCountVO = new RegisterCountVO();
        try {
            long count = registerService.countUserSignIn(registerCmd.getUserId(), registerCmd.getRegisterEnum(), registerCmd.getValues());
            registerCountVO.setCount(count);
            registerCountVO.setType(String.valueOf(registerCmd.getRegisterEnum()));
            end = System.currentTimeMillis();
            registerCountVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return registerCountVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            registerCountVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "检查用户签到次数失败"));
            return registerCountVO;
        }
    }

    @GetMapping("/check/sign-in/company")
    public CheckRegisterVO checkSignInCompany(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, int userId) {
        long start = System.currentTimeMillis();
        long end;
        CheckRegisterVO checkRegisterVO = new CheckRegisterVO();
        try {
            boolean checkResult = registerService.checkSignInCompany(date, userId);
            checkRegisterVO.setHasSignedIn(checkResult);
            end = System.currentTimeMillis();
            checkRegisterVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return checkRegisterVO;
        } catch (Exception e) {
            end = System.currentTimeMillis();
            checkRegisterVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "查询用户签到情况失败"));
            return checkRegisterVO;
        }
    }

    @GetMapping("/count/sign-in/company")
    // date默认形式2024/12/26，否则加@DateTimeFormat(pattern = "yyyy-MM-dd")
    public RegisterCountVO countSignIn(Date date) {
        long start = System.currentTimeMillis();
        long end;
        RegisterCountVO registerCountVO = new RegisterCountVO();
        try {
            long count = registerService.countSignInCompany(date);
            registerCountVO.setCount(count);
            registerCountVO.setType("DAY");
            end = System.currentTimeMillis();
            registerCountVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return registerCountVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            registerCountVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "检查用户签到次数失败"));
            return registerCountVO;
        }
    }

    @GetMapping("/test")
    public void dateTest(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date date){
        System.out.println(date);
    }

}
