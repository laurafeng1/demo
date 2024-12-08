package com.example.demo.controller;

import com.example.demo.controller.cmd.UserLoginCmd;
import com.example.demo.controller.vo.AllUserVO;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.SingleUserVO;
import com.example.demo.controller.vo.UserVO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void login() {
//        UserLoginCmd cmd1 = buildUserLoginCmd("111", "222");
//        BaseVO vo1 = userController.login(cmd1);
//        Assertions.assertEquals(500, vo1.getCode());
//        Assertions.assertFalse(vo1.isSuccess());
//        Assertions.assertEquals("用户不存在", vo1.getErrorMsg());
//
//        UserLoginCmd cmd2 = buildUserLoginCmd("aaAaaa0a", "222");
//        BaseVO vo2 = userController.login(cmd2);
//        Assertions.assertEquals(500, vo2.getCode());
//        Assertions.assertFalse(vo2.isSuccess());
//        Assertions.assertEquals("用户密码错误", vo2.getErrorMsg());

        UserLoginCmd cmd3 = buildUserLoginCmd("aaa", "aaa123");
        BaseVO vo3 = userController.login(cmd3);

        Assertions.assertEquals(200, vo3.getCode());
        Assertions.assertTrue(vo3.isSuccess());
        Assertions.assertEquals("", vo3.getErrorMsg());
    }

    @Test
    public void testFindAllUser() {
//        AllUserVO allUserVO = userController.findAllUser();
//        Assertions.assertEquals(200, allUserVO.getBaseVO().getCode());
//        Assertions.assertTrue(allUserVO.getBaseVO().isSuccess());
//        Assertions.assertEquals("", allUserVO.getBaseVO().getErrorMsg());
//
//        List<UserVO> userVOs = allUserVO.getUserVOs();
//        Assertions.assertEquals(2, userVOs.size());
//
//        UserVO vo1 = userVOs.get(0);
//        Assertions.assertEquals(1, vo1.getId());
//        Assertions.assertEquals("aaAaaa0a", vo1.getName());
//        Assertions.assertEquals("aaAaaa0a", vo1.getPassword());
//        Assertions.assertEquals("1", vo1.getGender());
//        Assertions.assertEquals("IT", vo1.getJob());
//
//        UserVO vo2 = userVOs.get(1);
//        Assertions.assertEquals(2, vo2.getId());
//        Assertions.assertEquals("safdasf", vo2.getName());
//        Assertions.assertEquals("fasfasfafwef", vo2.getPassword());
//        Assertions.assertEquals("0", vo2.getGender());
//        Assertions.assertEquals("IT", vo2.getJob());

        AllUserVO allUserVO = userController.findAllUser();
        Assertions.assertEquals(500, allUserVO.getBaseVO().getCode());
        Assertions.assertFalse(allUserVO.getBaseVO().isSuccess());
        Assertions.assertEquals("查询用户失败", allUserVO.getBaseVO().getErrorMsg());
    }

    @Test
    public void testFindUser() {
//        AllUserVO allUserVO = userController.findAllUser();
//        Assertions.assertEquals(200, allUserVO.getBaseVO().getCode());
//        Assertions.assertTrue(allUserVO.getBaseVO().isSuccess());
//        Assertions.assertEquals("", allUserVO.getBaseVO().getErrorMsg());
//
//        List<UserVO> userVOs = allUserVO.getUserVOs();
//        Assertions.assertEquals(2, userVOs.size());
//
//        UserVO vo1 = userVOs.get(0);
//        Assertions.assertEquals(1, vo1.getId());
//        Assertions.assertEquals("aaAaaa0a", vo1.getName());
//        Assertions.assertEquals("aaAaaa0a", vo1.getPassword());
//        Assertions.assertEquals("1", vo1.getGender());
//        Assertions.assertEquals("IT", vo1.getJob());
//
//        UserVO vo2 = userVOs.get(1);
//        Assertions.assertEquals(2, vo2.getId());
//        Assertions.assertEquals("safdasf", vo2.getName());
//        Assertions.assertEquals("fasfasfafwef", vo2.getPassword());
//        Assertions.assertEquals("0", vo2.getGender());
//        Assertions.assertEquals("IT", vo2.getJob());

        SingleUserVO vo = userController.findUserByName("safdasf");
        Assertions.assertEquals(200, vo.getBaseVO().getCode());
        Assertions.assertTrue(vo.getBaseVO().isSuccess());
        Assertions.assertEquals("", vo.getBaseVO().getErrorMsg());
        Assertions.assertTrue(vo.getBaseVO().getTime() < 3000);

        UserVO vo1 = vo.getUserVO();
        Assertions.assertEquals(2, vo1.getId());
        Assertions.assertEquals("safdasf", vo1.getName());
        Assertions.assertEquals("fasfasfafwef", vo1.getPassword());
        Assertions.assertEquals("0", vo1.getGender());
        Assertions.assertEquals("IT", vo1.getJob());

    }

    private UserLoginCmd buildUserLoginCmd(String username, String password) {
        UserLoginCmd cmd = new UserLoginCmd();
        cmd.setUsername(username);
        cmd.setPassword(password);
        return cmd;
    }
}