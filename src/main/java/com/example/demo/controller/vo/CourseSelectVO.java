package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseSelectVO {

    private int id;

    private int stuId;

    private int courId;

    private String status;

    private BaseVO baseVO;
}
