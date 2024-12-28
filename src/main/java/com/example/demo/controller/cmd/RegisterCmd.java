package com.example.demo.controller.cmd;

import com.example.demo.enums.RegisterEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCmd {
    private int userId;
    private RegisterEnum registerEnum;
    private int[] values;
}
