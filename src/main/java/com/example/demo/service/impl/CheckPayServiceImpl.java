package com.example.demo.service.impl;

import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.integration.EmailIntegration;
import com.example.demo.integration.cmd.EmailUnpaidNotificationCmd;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.CheckPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPayServiceImpl implements CheckPayService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EmailIntegration emailIntegration;

    @Override
    public void checkAndEmail() {
        List<Order> orders = orderMapper.queryAll();
        for(Order order : orders) {
            if(order.getStatus().equals(OrderStatusEnum.INIT.getCode())) {
                EmailUnpaidNotificationCmd emailUnpaidNotificationCmd = new EmailUnpaidNotificationCmd();
                // todo: 邮箱根据order里的userId 从user表中获取
                emailUnpaidNotificationCmd.setEmail("1445079235@qq.com");
                emailUnpaidNotificationCmd.setOrderId(order.getId());
                emailIntegration.sendUnpaidEmail(emailUnpaidNotificationCmd);
            }
        }
    }
}
