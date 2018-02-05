package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.State;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface StateDao {

    State findByStateId(Integer stateId);

}
