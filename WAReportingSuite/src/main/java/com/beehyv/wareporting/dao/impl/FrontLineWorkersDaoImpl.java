package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.FrontLineWorkersDao;
import com.beehyv.wareporting.model.Swachchagrahi;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("frontLineWorkersDao")
public class FrontLineWorkersDaoImpl extends AbstractDao<Integer,Swachchagrahi> implements FrontLineWorkersDao {

    @Override
    public List<Swachchagrahi> getInactiveFrontLineWorkers(Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","SWACHCHAGRAHI").ignoreCase()
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveFrontLineWorkersWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("state",stateId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveFrontLineWorkersWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("district",districtId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveFrontLineWorkersWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("block",blockId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("district",districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
