package com.example.demo.controller.cmd;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelCourseCmd {
    private int stuId;
    private int courId;
}