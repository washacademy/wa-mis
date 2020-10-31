package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.StateServiceDao;
import com.beehyv.wareporting.model.State;
import com.beehyv.wareporting.model.StateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stateServiceDao")
public class StateServiceDaoImpl extends AbstractDao<Integer, StateService> implements StateServiceDao {

    @Override
    public List<StateService> getAllStatesService() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
        return (List<StateService>) criteria.list();
    }
}
