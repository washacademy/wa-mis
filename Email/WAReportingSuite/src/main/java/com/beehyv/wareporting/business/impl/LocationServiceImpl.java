package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.LocationService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    StateDao stateDao;

    @Autowired
    DistrictDao districtDao;

    @Autowired
    BlockDao blockDao;

    @Autowired
    CircleDao circleDao;

    @Autowired
    private StateCircleDao stateCircleDao;

    @Override
    public State findStateById(Integer stateId) {
        return stateDao.findByStateId(stateId);
    }

    @Override
    public District findDistrictById(Integer districtId) throws NullPointerException {
        return districtDao.findByDistrictId(districtId);
    }

    @Override
    public Block findBlockById(Integer blockId) {
        return blockDao.findByBlockId(blockId);
    }

    @Override
    public List<State> getStatesOfCircle(Circle circle) {
        List<StateCircle> stateCircleList = stateCircleDao.getRelByCircleId(circle.getCircleId());
        List<State> states = new ArrayList<>();
        for (StateCircle stateCircle : stateCircleList) {
            states.add(stateDao.findByStateId(stateCircle.getStateId()));
        }
        return states;
    }

    @Override
    public Circle findCircleById(Integer circleId){
        return circleDao.getByCircleId(circleId);
    }

}
