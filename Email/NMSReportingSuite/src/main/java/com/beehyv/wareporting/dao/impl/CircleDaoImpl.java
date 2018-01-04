package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.CircleDao;
import com.beehyv.wareporting.model.Circle;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("circleDao")
public class CircleDaoImpl extends AbstractDao<Integer, Circle> implements CircleDao {

    @Override
    public Circle getByCircleId(Integer circleId) {
        return getByKey(circleId);
    }
}
