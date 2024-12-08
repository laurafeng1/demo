package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserToken implements Serializable {
    private static final long serialVersionUID = -6908033111270652020L;
    private int userId;
    private Date expireTime;
}
