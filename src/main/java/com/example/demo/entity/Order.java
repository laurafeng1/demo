package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
public class Order {
    private int id;

    private int userId;

    private int goodId;

    private Double totalPrice;

    private String status;

    private Date createDate;

    private Date updateDate;
}
