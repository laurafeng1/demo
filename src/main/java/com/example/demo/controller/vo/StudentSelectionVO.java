package com.example.demo.controller.vo;

import com.example.demo.entity.to.StudentSelectionDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentSelectionVO {
    private StudentSelectionDetail detail;

    private BaseVO baseVO;
}
