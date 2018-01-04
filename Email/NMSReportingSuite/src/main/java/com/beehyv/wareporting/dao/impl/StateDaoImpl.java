package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.StateDao;
import com.beehyv.wareporting.model.State;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("stateDao")
public class StateDaoImpl extends AbstractDao<Integer, State> implements StateDao {

    @Override
    public State findByStateId(Integer stateId) {
        return getByKey(stateId);
    }

}
