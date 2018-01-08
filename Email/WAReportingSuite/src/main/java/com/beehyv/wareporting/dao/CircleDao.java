package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Circle;

/**
 * Created by beehyv on 23/5/17.
 */
public interface CircleDao {

    Circle getByCircleId(Integer circleId);
}
