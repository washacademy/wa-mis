package com.beehyv.wareporting.dao.impl;
import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.CourseDao;
import com.beehyv.wareporting.model.Course;
import org.springframework.stereotype.Repository;

@Repository("courseDao")
public class CourseDaoImpl extends AbstractDao<Integer, Course> implements CourseDao {

    @Override
    public Course findByCourseId(Integer courseId){
        return getByKey(courseId);
    }
}
