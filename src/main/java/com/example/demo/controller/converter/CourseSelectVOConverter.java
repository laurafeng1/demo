package com.example.demo.controller.converter;

import com.example.demo.controller.vo.CourseSelectVO;
import com.example.demo.entity.CourseSelection;

public class CourseSelectVOConverter {
    public static CourseSelectVO convertToVO(CourseSelection courseSelection) {
        CourseSelectVO courseSelectVO = new CourseSelectVO();
        courseSelectVO.setId(courseSelection.getId());
        courseSelectVO.setStatus(courseSelection.getStatus());
        courseSelectVO.setStuId(courseSelection.getStuId());
        courseSelectVO.setCourId(courseSelection.getCourId());
        return courseSelectVO;
    }
}
