package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.CircleDao;
import com.beehyv.wareporting.model.Circle;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("circleDao")
public class CircleDaoImpl extends AbstractDao<Integer,Circle> implements CircleDao{

    @Override
    public List<Circle> getAllCircles() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("circleName"));
        return (List<Circle>) criteria.list();
    }

    @Override
    public Circle findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (Circle) criteria.list().get(0);
    }

    @Override
    public Circle getByCircleId(Integer circleId) {
        return getByKey(circleId);
    }

    @Override
    public Circle getByCircleName(String circleName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("circleName", circleName).ignoreCase());
        return (Circle) criteria.list().get(0);
    }
}
