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
import com.example.demo.repository.CourseSelectionRepository;
import com.example.demo.service.CourseSelectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    private Logger logger = LoggerFactory.getLogger(CourseSelectionServiceImpl.class);

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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

    // Redis(in-memory database) working with SQL database(persistent datastore)
    // 1. search: look in the cache first: 1) cache hit-> return data 2) cache miss-> look in the sql database-> get data and prime cache with data
    // @Transactional为事务
    @Transactional
    public CourseSelection searchCourseSelection(int id) {
        CourseSelection courseSelection = courseSelectionRepository.searchCourseSelection(id);
        if(courseSelection == null) {
            courseSelection = courseSelectionMapper.queryById(id);
            courseSelectionRepository.insertCourseSelection(id, courseSelection);
            logger.info("从数据库中读取数据, 且数据插入缓存成功");
            return courseSelection;
        } else {
            logger.info("从缓存中读取数据");
            return courseSelection;
        }
    }

    // 2. add: add data in the persistent datastore
    public void insertCourseSelection(int stuId, int courId) {
        selectCourse(stuId, courId);
        logger.info("数据插入数据库成功");
    }

    // 3. delete: delete data from both im-memory and persistent datastore
    @Transactional
    public void deleteCourseSelection(int id) {
        CourseSelection courseSelection = courseSelectionMapper.queryById(id);
        courseSelectionRepository.deleteCourseSelectionByIds(courseSelection.getStuId(), courseSelection.getCourId());
        courseSelectionRepository.deleteCourseSelection(id);
        logger.info("数据从缓存中删除");
        courseSelectionMapper.deleteById(id);
        logger.info("数据从数据库中删除");
    }

    @Transactional
    public void updateCourseSelection(int id) {
        CourseSelection courseSelection = courseSelectionMapper.queryById(id);
        courseSelection.setStatus("INVALID");
        try {
            courseSelectionMapper.updateStatus(courseSelection);
            courseSelectionRepository.deleteCourseSelection(id);
            courseSelectionRepository.deleteCourseSelectionByIds(courseSelection.getStuId(), courseSelection.getCourId());
            logger.info("数据已在数据库中更新，且在缓存中删除");
        } catch(Exception e) {
            logger.info("数据更新失败");
            throw new RuntimeException("数据更新失败");
        }
    }

    @Transactional
    public CourseSelection searchCourseSelectionByIds(int stuId, int courId) {
        CourseSelection courseSelection = courseSelectionRepository.searchCourseSelectionByIds(stuId, courId);
        if (courseSelection == null) {
            courseSelection = courseSelectionMapper.queryByStuIdAndCourId(stuId, courId);
            courseSelectionRepository.insertCourseSelectionByIds(stuId, courId, courseSelection);
        }
        return courseSelection;
    }

}
