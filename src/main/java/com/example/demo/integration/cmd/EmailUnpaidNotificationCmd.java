package com.example.demo.integration.cmd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailUnpaidNotificationCmd {
    private String email;
    private int orderId;
}
