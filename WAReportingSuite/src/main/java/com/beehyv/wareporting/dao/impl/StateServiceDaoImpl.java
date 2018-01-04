package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.StateServiceDao;
import com.beehyv.wareporting.model.StateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 6/6/17.
 */
@Repository("stateServiceDao")
public class StateServiceDaoImpl extends AbstractDao<Integer, StateService> implements StateServiceDao {

    @Override
    public List<StateService> getStatesByServiceType(String type) {
        Criteria criteria = getSession().createCriteria(StateService.class);
        criteria.add(
                Restrictions.eq("serviceType", type).ignoreCase()
        );
        return (List<StateService>) criteria.list();
        /*List<State> statesForService=new ArrayList<>();

        for (StateService stateService:states){
            Criteria criteria1=getSession().createCriteria(State.class);
            criteria1.add(Restrictions.eq("stateId", stateService.getStateId()));
            State temporary=(State) criteria1.list().get(0);
            statesForService.add(temporary);
        }*/
//
//        return  states;
    }

    @Override
    public List<String> getServiceTypeOfState(Integer stateId){
        Criteria criteria = getSession().createCriteria(StateService.class);
        criteria.add(
                Restrictions.eq("stateId", stateId)
        );
        List<String> serviceList = new ArrayList<>();
        for(StateService stateService : (List<StateService>) criteria.list()){
            serviceList.add(stateService.getServiceType());
        }
        return serviceList;
    }

    @Override
    public Date getServiceStartDateForState(Integer stateId, String type) {
        Criteria criteria=getSession().createCriteria(StateService.class);
        criteria.add(
                Restrictions.and(
                        Restrictions.eq("stateId",stateId),
                        Restrictions.eq("serviceType",type)
                )
        );
        StateService stateService= (StateService) criteria.uniqueResult();
        return stateService==null ? null : stateService.getServiceStartDate();
    }
}
