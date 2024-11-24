package com.example.demo.controller;

import com.example.demo.controller.cmd.CancelCourseCmd;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.CourseSelectionVO;
import com.example.demo.controller.vo.StudentSelectionVO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.entity.to.CourseSelectionDetail;
import com.example.demo.entity.to.StudentSelectionDetail;
import com.example.demo.exception.*;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.CourseSelectionService;
import com.example.demo.util.TimeConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/course/selection")
public class CourseSelectionController {
    @Autowired
    private CourseSelectionService courseSelectionService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    Logger logger = LoggerFactory.getLogger(CourseSelectionController.class);

    @PostMapping("/add")
    public BaseVO selectCourse(int stuId, int courId) {
        long start = System.currentTimeMillis();
        long end;
        BaseVO vo = null;
        Course course = null;
        Student student = null;
        try {
            course = courseMapper.findById(courId);
            student = studentMapper.findById(stuId);
            courseSelectionService.selectCourse(stuId, courId);

            end = System.currentTimeMillis();
            vo = buildBaseVO(200, end - start, true, null);
        } catch (StudentNotExistException e) {
            end = System.currentTimeMillis();
            vo = buildBaseVO(500, end - start, false, "学生不存在");
        } catch (CourseNotExistException e) {
            end = System.currentTimeMillis();
            vo = buildBaseVO(500, end - start, false, "课程不存在");
        } catch (CourseSelectionInsertException e) {
            end = System.currentTimeMillis();
            vo = buildBaseVO(500, end - start, false, "学生选课记录插入失败");
        } catch (CourseNotAvailableException e) {
            end = System.currentTimeMillis();
            vo = buildBaseVO(500, end - start, false, String.format("课程%s已经被选满了", course.getName()));
        } catch (CannotReselectionException e) {
            end = System.currentTimeMillis();
            vo = buildBaseVO(500, end - start, false, String.format("学生%s已经选过了课程%s", student.getName(), course.getName()));
        } finally {
            return vo;
        }

    }

    @GetMapping("/list/byStu")
    public CourseSelectionVO querySelectionByStuId(int stuId) {
        long start = System.currentTimeMillis();
        long end;
        CourseSelectionVO vo = new CourseSelectionVO();
        try {
            CourseSelectionDetail detail = courseSelectionService.queryByStuId(stuId);
            vo.setDetail(detail);
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(200, end - start, true, null);
            vo.setBaseVO(baseVO);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(500, end - start, false, "学生不存在");
            vo.setBaseVO(baseVO);
        } finally {
            return vo;
        }
    }

    @GetMapping("/list/byCour")
    public StudentSelectionVO querySelectionByCourId(int courId) {
        long start = System.currentTimeMillis();
        long end;
        StudentSelectionVO vo = new StudentSelectionVO();
        try {
            StudentSelectionDetail detail = courseSelectionService.queryByCourId(courId);
            vo.setDetail(detail);
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(200, end - start, true, null);
            vo.setBaseVO(baseVO);
        } catch (Exception e) {
            end = System.currentTimeMillis();
            BaseVO baseVO = buildBaseVO(500, end - start, false, "学生不存在");
            vo.setBaseVO(baseVO);
        } finally {
            return vo;
        }
    }

    @DeleteMapping("/delete")
    public BaseVO cancelSelection (int stuId, int courId) {
        BaseVO baseVo = null;
        long start = System.currentTimeMillis();
        long end;
        try{
            courseSelectionService.cancelSelection(stuId, courId);
            end = System.currentTimeMillis();
            baseVo = buildBaseVO(200, end - start, true, "");
        } catch (StudentNotExistException e){
            end = System.currentTimeMillis();
            baseVo = buildBaseVO(500, end - start, false, "学生不存在");
        } catch (CourseNotExistException e) {
            end = System.currentTimeMillis();
            baseVo = buildBaseVO(500, end - start, false, "课程不存在");
        } catch (CourseNotSelectedException e) {
            end = System.currentTimeMillis();
            baseVo = buildBaseVO(500, end - start, false, "该学生未选该课");
        } catch (Exception e) {
            end = System.currentTimeMillis();
            baseVo = buildBaseVO(500, end - start, false, "其他未知异常");
        }
        return baseVo;
    }

    @PutMapping("/cancel")
    public BaseVO cancelSelection (@RequestBody CancelCourseCmd cmd){
        logger.info("student ID = {}, course ID = {}",cmd.getStuId(), cmd.getCourId());
        courseSelectionService.cancelCourse(cmd);
        logger.info("{} has deselected the course {} at {}",cmd.getStuId(), cmd.getCourId(), TimeConvertUtil.format(new Date()));
        return buildBaseVO(200, 1000, true, "");
    }

    private BaseVO buildBaseVO(int code, long time,boolean success, String errorMsg) {
        BaseVO vo = new BaseVO();
        vo.setCode(code);
        vo.setTime(time);
        vo.setSuccess(success);
        vo.setErrorMsg(errorMsg);
        return vo;
    }


}
