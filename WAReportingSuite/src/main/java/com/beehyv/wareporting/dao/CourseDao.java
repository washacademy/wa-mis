package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Course;

import java.util.List;

public interface CourseDao {
   Course findByCourseId(Integer courseId);
}
