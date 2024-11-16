package com.example.demo.service;

import com.example.demo.controller.cmd.CancelCourseCmd;
import com.example.demo.entity.CourseSelection;
import com.example.demo.entity.to.CourseSelectionDetail;
import com.example.demo.entity.to.StudentSelectionDetail;

import java.util.List;

public interface CourseSelectionService {
    void selectCourse(int stuId, int courId);

    CourseSelectionDetail queryByStuId(int stuId);

    StudentSelectionDetail queryByCourId(int courId);

    void cancelSelection(int stuId, int courId);

    void cancelCourse(CancelCourseCmd cmd);
}
