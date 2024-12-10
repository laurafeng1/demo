package com.example.demo.service.impl;

import com.example.demo.constant.DemoConstant;
import com.example.demo.entity.User;
import com.example.demo.entity.UserSubscribe;
import com.example.demo.entity.UserToken;
import com.example.demo.enums.GenderEnum;
import com.example.demo.exception.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserSubscribeRepository;
import com.example.demo.repository.UserTokenRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.TimeCalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户实现类，实现用户的注册，登录模块
 */
// 向spring注册的是{"userServec": new UserServiceImpl()}

// @Service必须放在实现类
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserSubscribeRepository userSubscribeRepository;

    /**
     * 用户注册功能
     * @param name
     * @param age
     * @param gender
     * @param job
     */
    @Override
    public void register(String name, String password, int age, String gender, String job) {
        // 1. 校验参数
        checkParameter(name, password, age, gender);

        // 2. 检查用户是否已存在
        checkUserNameExisted(name);

        // 3. 创建新用户
        User user = buildUser(name, password, age, gender, job);

        // 4. 插入数据库
        insertDB(user);
    }

    @Override
    public void login( String name, String password) {
        User user = userMapper.findByName(name);

        if (user == null) {
            throw new UserNotExistedException("登陆的用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new UserPasswordInvalidException("密码输入错误");
        }

        UserToken userToken = userTokenRepository.loadToken("userTokenHash2", user.getId());
        UserToken userToken1 = new UserToken();
        if (userToken == null) {
            userToken1.setUserId(user.getId());
            userToken1.setExpireTime(TimeCalculateUtil.calculateExpireTime());
            userTokenRepository.saveToken("userTokenHash2", user.getId(), userToken1);
        } else {
            userTokenRepository.updateToken("userTokenHash2", user.getId(), userToken1);
        }

        System.out.println(String.format("%s登陆成功", user.getName()));
    }

    @Override
    public void modifyPassword(String name, String password) {
        // 1. 校验新的密码是否正确

        // 2. 查询用户

        //3. 校验用户是否存在

        // 4. user.set（）

        // 5. 调用数据库更新
    }

    @Override
    public void deleteUser(int id) {
        // 1. 查询
        // 2. 删除
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAll();
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findByName(name);
    }

    private boolean formatCheck(String name) {
        if (Character.isDigit(name.charAt(0))) {
            return false;
        }

        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        for(int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))){
                count1++;
            } else if (Character.isUpperCase(name.charAt(i))) {
                count2++;
            } else if (Character.isLowerCase(name.charAt(i))) {
                count3++;
            } else {
                count4++;
            }
        }
        return count1 > 0 && count2 > 0 && count3 > 0 && count4 == 0;
    }

    private void checkParameter(String name, String password, int age, String gender) {
        // 1. 用户名介于6-10位之间，只能同时包含大小写和数字，不能以数字开始，不允许有其他字符
        if (name.length() < DemoConstant.MIN_NAME_LENGTH || name.length() > DemoConstant.MAX_NAME_LENGTH) {
            throw new UserNameException("姓名的长度非法！");
        }

        if (!formatCheck(name)) {
            throw new UserNameException("姓名的格式非法！");
        }

        if (password.length() < DemoConstant.MIN_NAME_LENGTH || password.length() > DemoConstant.MAX_NAME_LENGTH) {
            throw new UserPasswordException("姓名的格式非法！");
        }

        if (age < DemoConstant.MIN_AGE || age > DemoConstant.MAX_AGE) {
            throw new UserAgeException("年龄的范围非法！");
        }

        if (!gender.equals(GenderEnum.MALE.getCode()) && !gender.equals(GenderEnum.FEMALE.getCode())) {
            throw new UserGenderException("性别非法！");
        }
    }


    private void checkUserNameExisted(String name) {
        User user = userMapper.findByName(name);
        if (user != null) {
            throw new UserHasRegisteredException("该用户名已存在！");
        }
    }

    /**
     *
     * @param name
     * @param age
     * @param gender
     * @param job
     * @return
     */
    private User buildUser(String name,String password, int age, String gender, String job) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setAge(age);
        newUser.setGender(gender);
        newUser.setJob(job);
        return newUser;
    }

    private void insertDB(User user) {
        try {
            userMapper.add(user);
        } catch (Exception e) {
            throw new UserRegisterFailedException("插入数据库失败！");
        }
    }

    public void addUserSubscribe(User user) {
        try{
            userSubscribeRepository.addSubscribe(buildUserSubscribe(user).getUserId(), buildUserSubscribe(user));
        } catch (Exception e) {
            throw new UserSubscribeFailException(String.format("关注%d用户失败!", user.getId()));
        }

    }

    public List<UserSubscribe> showUserSubscribe(User user) {
        return userSubscribeRepository.showSubscribe(buildUserSubscribe(user).getUserId(), buildUserSubscribe(user));
    }

    private UserSubscribe buildUserSubscribe(User user) {
        UserSubscribe us1 = new UserSubscribe();
        us1.setUserId(user.getId());
        us1.setUserName(user.getName());
        us1.setSubscribeTime(new Date());
        return us1;
    }
}
