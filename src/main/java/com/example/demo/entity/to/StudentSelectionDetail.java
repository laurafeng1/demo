package com.example.demo.entity.to;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StudentSelectionDetail {
    private List<Student> studentList;

    private Course course;
}
