package com.example.demo.mapper;

import com.example.demo.entity.CourseSelection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseSelectionMapper {
    void insert(CourseSelection courseSelection);

    List<CourseSelection> queryByStuId(int stuId);

    List<CourseSelection> queryByCourId(int courId);

    void delete(int stuId, int courId);

    CourseSelection queryByStuIdAndCourId(int stuId, int courId);

    CourseSelection queryByStuIdAndCourIdForCheck(int stuId, int courId);

    void updateStatus(CourseSelection courseSelection);

}
