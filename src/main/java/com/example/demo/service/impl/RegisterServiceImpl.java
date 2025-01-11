package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.enums.RegisterEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRegisterRepository;
import com.example.demo.service.RegisterService;
import com.example.demo.util.TimeConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRegisterRepository userRegisterRepository;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RegisterMessageSender registerMessageSender;

    Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Override
    @Transactional
    public void signIn(int userId) {
        User user = checkUserExistence(userId);
        try {
            userRegisterRepository.updateUserRegisterDay(userId, true);
            userRegisterRepository.updateUserRegisterWeek(userId, true);
            userRegisterRepository.updateUserRegisterMonth(userId, true);
            updateSignInCompany(userId, true);
        } catch (RuntimeException e) {
            logger.error(String.format("用户%d在%s签到失败", userId, TimeConvertUtil.format(new Date())));
            throw new RuntimeException(String.format("用户%d在%s签到失败", userId, TimeConvertUtil.format(new Date())));
        }
        try {
            registerMessageSender.sender("MY_EXCHANGE", "MY_ROUTING", user);
            logger.info("消息发送成功");
        } catch (RuntimeException e) {
            logger.error("消息发送失败");
            throw new RuntimeException(String.format("%s，%s消息发送失败", "MY_EXCHANGE", "MY_ROUTING"));
        }
    }

    @Override
    @Transactional
    public void cancelSignIn(int userId) {
        checkUserExistence(userId);
        try {
            userRegisterRepository.updateUserRegisterDay(userId, false);
            userRegisterRepository.updateUserRegisterWeek(userId, false);
            userRegisterRepository.updateUserRegisterMonth(userId, false);
            updateSignInCompany(userId, false);
        } catch (RuntimeException e) {
            logger.error(String.format("用户%d在%s取消签到失败", userId, TimeConvertUtil.format(new Date())));
            throw new RuntimeException(String.format("用户%d在%s取消签到失败", userId, TimeConvertUtil.format(new Date())));
        }
    }

    @Override
    public boolean checkUserSignIn(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek) {
        return userRegisterRepository.checkUserRegister(userId, monthOfYear, weekOfMonth, dayOfWeek, dayOfWeek);
    }

    @Override
    // int[] values = [monthOfYear, weekOfMonth, dayOfWeek]
    public long countUserSignIn(int userId, RegisterEnum registerEnum, int...values) {
        if(values.length != registerEnum.getParamCount()) {
            logger.warn("参数个数不合法");
            throw new IllegalArgumentException(registerEnum + " requires " + registerEnum.getParamCount() + " value(s).");
        }
        switch (registerEnum) {
            case MONTH:
                return userRegisterRepository.countUserRegister(userId, values[0]);
            case WEEK:
                return userRegisterRepository.countUserRegister(userId, values[0], values[1]);
            case DAY:
                return userRegisterRepository.countUserRegister(userId, values[0], values[1], values[2]);
            default:
                throw new IllegalArgumentException("Unsupported RegisterEnum: " + registerEnum);
        }
    }

    private void updateSignInCompany(int userId, boolean value) {
        long offset = getOffset(userId);
        userRegisterRepository.updateRegisterCompany(offset, value);
    }

    private long getOffset(int userId) {
        List<User> users = userMapper.findAll();
        List<Integer> ids = new ArrayList<>();
        for(User user : users) {
            ids.add(user.getId());
        }
        long offset = ids.indexOf(userId);
        if(offset == -1) {
            throw new RuntimeException("用户不存在");
        }
        return offset;
    }

    @Override
    public boolean checkSignInCompany(Date date, int userId) {
        long offset = getOffset(userId);
        return userRegisterRepository.checkUserRegisterCompany(date, offset);
    }

    @Override
    public long countSignInCompany(Date date) {
        return userRegisterRepository.countRegisterCompany(date);
    }


    private User checkUserExistence(int userId) {
        User user = userMapper.findById(userId);
        if(user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }
}
