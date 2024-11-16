package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Course {

    private int id;

    private String name;

    private String level;

    private int teacherNo;

    private int maxCapacity;
}
