package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Circle;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface CircleDao {

    List<Circle> getAllCircles();
    Circle getByCircleId(Integer circleId);
}
