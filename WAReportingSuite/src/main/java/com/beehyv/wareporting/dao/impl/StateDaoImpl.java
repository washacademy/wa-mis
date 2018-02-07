package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.StateDao;
import com.beehyv.wareporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("stateDao")
public class StateDaoImpl extends AbstractDao<Integer, State> implements StateDao {


    @Override
    public State findByStateId(Integer stateId) {
        return getByKey(stateId);
    }

    @Override
    public State findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId));
        return (State)criteria.list().get(0);
    }

    @Override
    public List<State> findByName(String stateName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateName", stateName).ignoreCase());
        return (List<State>) criteria.list();

    }

    @Override
    public List<State> getAllStates() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("stateId"));
        return (List<State>) criteria.list();
    }

    /*@Override
    public List<State> getStatesByServiceType(String type) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("stateId"));

        criteria.add(Restrictions.or(
                Restrictions.eq("serviceType", type).ignoreCase(),
                Restrictions.eq("serviceType", "ALL")));

        return (List<State>) criteria.list();
    }*/

}
