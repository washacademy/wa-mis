package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Course;

public interface CourseDao {
   Course findByCourseId(Integer courseId);
}
