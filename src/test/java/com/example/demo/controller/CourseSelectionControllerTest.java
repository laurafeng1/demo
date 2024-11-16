package com.example.demo.controller;

import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.CourseSelectionVO;
import com.example.demo.controller.vo.StudentSelectionVO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseSelectionControllerTest {
    @Autowired
    private CourseSelectionController courseSelectionController;

    @Test
    public void selectCourse() {
        BaseVO vo1 = courseSelectionController.selectCourse(1,1);
        Assertions.assertEquals(200, vo1.getCode());
        Assertions.assertTrue(vo1.getTime() < 3000);
        Assertions.assertTrue(vo1.isSuccess());
        Assertions.assertNull(vo1.getErrorMsg());
    }

    @Test
    public void querySelectionByStuId() {
        CourseSelectionVO vo = courseSelectionController.querySelectionByStuId(1);
        Assertions.assertEquals(200, vo.getBaseVO().getCode());
        Assertions.assertTrue(vo.getBaseVO().getTime() < 3000);
        Assertions.assertTrue(vo.getBaseVO().isSuccess());
        Assertions.assertNull(vo.getBaseVO().getErrorMsg());

        System.out.println(vo);
    }

    @Test
    public void querySelectionByCourId() {
        StudentSelectionVO vo = courseSelectionController.querySelectionByCourId(2);
        Assertions.assertEquals(200, vo.getBaseVO().getCode());
        Assertions.assertTrue(vo.getBaseVO().getTime() < 3000);
        Assertions.assertTrue(vo.getBaseVO().isSuccess());
        Assertions.assertNull(vo.getBaseVO().getErrorMsg());

        System.out.println(vo);
    }
}