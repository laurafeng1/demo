package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Good {
    private int id;

    private String name;

    private Double price;

    private String url;

    private String desc;

    private int stock;
}
