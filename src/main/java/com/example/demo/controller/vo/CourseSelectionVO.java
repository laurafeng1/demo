package com.example.demo.controller.vo;

import com.example.demo.entity.to.CourseSelectionDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseSelectionVO {
    private CourseSelectionDetail detail;

    private BaseVO baseVO;
}
