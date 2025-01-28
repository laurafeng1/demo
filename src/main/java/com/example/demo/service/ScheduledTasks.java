package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {

    @Autowired
    private CheckRegisterService checkRegisterService;


    @Autowired
    private CheckPayService checkPayService;

//    @Scheduled(fixedDelay = 5000L)
//    public void scheduledTask1() {
//        System.out.println("定时任务1");
//    }

    // cron = "*/0 * * * * ?" */后的数字是间隔
    // cron = "0 0 12 * * ?"  直接是数字是定点发送
    // cron = "0 0/1 12 * * ?" 在12点小时内每分钟执行一次
    // 后面有数字时 前面必须改为0
//    @Scheduled(cron = "*/20 * * * * ?")
//    public void scheduledTask1() {
//       checkRegisterService.checkAndNotify();
//    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void scheduledTask2() {
        checkPayService.checkAndEmail();
    }

}
