package com.example.demo.controller;

import com.example.demo.controller.cmd.UserLoginCmd;
import com.example.demo.controller.converter.UserVOConverter;
import com.example.demo.controller.vo.AllUserVO;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.SingleUserVO;
import com.example.demo.controller.vo.UserVO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotExistedException;
import com.example.demo.exception.UserPasswordInvalidException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

// 将entry-{“userController": new UserController()}放进spring仓库
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public BaseVO login(UserLoginCmd cmd) {
        long start = System.currentTimeMillis();
        long end;
        try {
            userService.login(cmd.getUsername(), cmd.getPassword());
        } catch (UserNotExistedException e) {
            end = System.currentTimeMillis();
            return buildBaseVO(500, end -start, false, "用户不存在");
        } catch (UserPasswordInvalidException e) {
            end = System.currentTimeMillis();
            return buildBaseVO(500, end -start,false, "用户密码错误");
        }
        end = System.currentTimeMillis();
        return buildBaseVO(200, end -start,true, "");
    }

    @RequestMapping("/name")
    public SingleUserVO findUserByName(String name) {
        long start = System.currentTimeMillis();
        long end;
        SingleUserVO vo = new SingleUserVO();
        try {
            User user = userService.findUserByName(name);
            UserVO userVO = UserVOConverter.convertUserVO(user);
            vo.setUserVO(userVO);
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(200, end -start, true, "");
            vo.setBaseVO(baseVO);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(500, end -start,false, "查询用户失败");
            vo.setBaseVO(baseVO);
        } finally {
            return vo;
        }
    }

    @RequestMapping("/list")
    public AllUserVO findAllUser() {
        long start = System.currentTimeMillis();
        long end;
        AllUserVO  allUserVO = new AllUserVO();
        try {
            List<User> userList = userService.findAllUser();
            List<UserVO> userVOList = UserVOConverter.convertUserVOList(userList);
            allUserVO.setUserVOs(userVOList);
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(200, end -start,true, "");
            allUserVO.setBaseVO(baseVO);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(500, end -start,false, "查询用户失败");
            allUserVO.setBaseVO(baseVO);
        } finally {
            return allUserVO;
        }
    }

    private BaseVO buildBaseVO(int code, long time,boolean success, String errorMsg) {
        BaseVO vo = new BaseVO();
        vo.setCode(code);
        vo.setTime(time);
        vo.setSuccess(success);
        vo.setErrorMsg(errorMsg);
        return vo;
    }

}
