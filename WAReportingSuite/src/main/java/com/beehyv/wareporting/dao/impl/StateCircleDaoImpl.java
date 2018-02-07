package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.StateCircleDao;
import com.beehyv.wareporting.model.StateCircle;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
@Repository("stateCircleDao")
public class StateCircleDaoImpl extends AbstractDao<Integer,StateCircle> implements StateCircleDao{

    @Override
    public List<StateCircle> getRelByCircleId(Integer circleId){
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("circleId", circleId));
        return criteria.list();
    }

    @Override
    public List<StateCircle> getRelByStateId(Integer stateId) {
        Criteria criteria = createEntityCriteria();
        if (stateId != null)
            criteria.add(Restrictions.eq("stateId", stateId));
        return criteria.list();
    }
}
