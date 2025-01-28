package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.integration.EmailIntegration;
import com.example.demo.integration.cmd.EmailNotificationCmd;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CheckRegisterService;
import com.example.demo.service.RegisterService;
import com.example.demo.service.UserService;
import com.example.demo.util.TimeCalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckRegisterServiceImpl implements CheckRegisterService {
    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private EmailIntegration emailIntegration;

    @Override
    public void checkAndNotify() {
        List<User> users = userService.findAllUser();
        // 转换为userRegister list
        for(User user : users) {
            if(!registerService.checkUserSignIn(user.getId(), TimeCalculateUtil.calculateMonthOfYear(), TimeCalculateUtil.calculateWeekOfMonth(), TimeCalculateUtil.calculateDayOfWeek())) {
                // 发邮件
                EmailNotificationCmd emailNotificationCmd = new EmailNotificationCmd();
                emailNotificationCmd.setUserId(user.getId());
                emailIntegration.sendEmail(emailNotificationCmd);
            }
        }
    }
}
