package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.StateCircle;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface StateCircleDao {

    List<StateCircle> getRelByCircleId(Integer circleId);

    List<StateCircle> getRelByStateId(Integer stateId);

}
