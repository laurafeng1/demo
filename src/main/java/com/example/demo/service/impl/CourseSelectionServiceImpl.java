package com.example.demo.service.impl;

import com.example.demo.controller.cmd.CancelCourseCmd;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseSelection;
import com.example.demo.entity.Student;
import com.example.demo.entity.to.CourseSelectionDetail;
import com.example.demo.entity.to.StudentSelectionDetail;
import com.example.demo.exception.*;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.CourseSelectionMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseSelectionServiceImpl implements CourseSelectionService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseSelectionMapper courseSelectionMapper;

    @Override
    public void selectCourse(int stuId, int courId) {
        List<CourseSelection> list1 = courseSelectionMapper.queryByCourId(courId);
        int hasSelectedCount = list1.size();

        List<CourseSelection> list2 = courseSelectionMapper.queryByStuId(courId);
        for(CourseSelection c : list2) {
            if (courId == c.getCourId()) {
                throw new CannotReselectionException(String.format("学生%d已经选过了课程%d",stuId,courId));
            }
        }


        // 1. 校验学生是否存在
        validStudent(stuId);

        // 2. 校验课程是否存在
        validCourse(courId, hasSelectedCount);

        // 3. 创建插入对象
        CourseSelection courseSelection = buildCourseSelection(stuId,courId);

        // 4. 插入记录
        insertSelectionRecord(courseSelection);
    }

    @Override
    public CourseSelectionDetail queryByStuId(int stuId) {
        List<CourseSelection> list = courseSelectionMapper.queryByStuId(stuId);
        Student student = studentMapper.findById(stuId);

        List<Course> courseList = new ArrayList<>();
        for(CourseSelection c : list) {
            int courId = c.getCourId();
            Course course = courseMapper.findById(courId);
            courseList.add(course);
        }

        CourseSelectionDetail detail = new CourseSelectionDetail();
        detail.setStudent(student);
        detail.setCourseList(courseList);

        return detail;
    }

    @Override
    public StudentSelectionDetail queryByCourId(int courId) {
        List<CourseSelection> list = courseSelectionMapper.queryByCourId(courId);
        Course course = courseMapper.findById(courId);
        List<Student> studentList = new ArrayList<>();
        for(CourseSelection c : list) {
            int stuId = c.getStuId();
            Student student = studentMapper.findById(stuId);
            studentList.add(student);
        }
        StudentSelectionDetail detail = new StudentSelectionDetail();
        detail.setCourse(course);
        detail.setStudentList(studentList);
        return detail;
    }

    @Override
    public void cancelSelection(int stuId, int courId) {
        validStudent(stuId);
        validCourse(courId);

        CourseSelection courseSelected = courseSelectionMapper.queryByStuIdAndCourId(stuId,courId);
        if(courseSelected == null){
            throw new CourseNotSelectedException(String.format("学生%d没有选择课程%s", stuId, courId));
        }
        courseSelectionMapper.delete(stuId, courId);
    }

    @Override
    public void cancelCourse(@RequestBody CancelCourseCmd cmd) {
        // 1. 校验学生是否存在
        validStudent(cmd.getStuId());

        // 2. 校验课程是否存在
        validCourse(cmd.getCourId());

        CourseSelection courseSelection = courseSelectionMapper.queryByStuIdAndCourIdForCheck(cmd.getStuId(), cmd.getCourId());

        courseSelection.setStatus("INVALID");

        courseSelectionMapper.updateStatus(courseSelection);

    }

    private void validStudent(int stuId) {
        Student student = studentMapper.findById(stuId);
        if (student == null) {
            throw new StudentNotExistException("学生不存在");
        }
    }

    private void validCourse(int courId, int curSize) {
        Course course = courseMapper.findById(courId);
        if (course == null) {
            throw new CourseNotExistException("课程不存在");
        }
        if (course.getMaxCapacity() <= curSize) {
            throw new CourseNotAvailableException(String.format("当前课程已经被选满了, courseId = %d", courId));
        }
    }

    private void validCourse(int courId) {
        Course course = courseMapper.findById(courId);
        if (course == null) {
            throw new CourseNotExistException("课程不存在");
        }
    }

    private CourseSelection buildCourseSelection(int stuId, int courId) {
        CourseSelection courseSelection = new CourseSelection();
        courseSelection.setStuId(stuId);
        courseSelection.setCourId(courId);
        return courseSelection;
    }

    private void insertSelectionRecord(CourseSelection courseSelection) {
        try {
            courseSelectionMapper.insert(courseSelection);
        } catch (Exception e) {
            throw new CourseSelectionInsertException("学生选课记录插入失败");
        }
    }
}
