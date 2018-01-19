package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Circle;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface CircleDao {

    List<Circle> getAllCircles();

    Circle findByLocationId(Long locationId);

    Circle getByCircleId(Integer circleId);

    Circle getByCircleName(String circleName);
}
