package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.SwachchagrahiDao;
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
@Repository("swachchagrahisDao")
public class SwachchagrahiDaoImpl extends AbstractDao<Integer,Swachchagrahi> implements SwachchagrahiDao {

    @Override
    public List<Swachchagrahi> getInactiveSwachchagrahis(Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("courseStatus","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("swcDesignation","SWACHCHAGRAHI").ignoreCase()
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveSwachchagrahisWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("courseStatus","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("swcDesignation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("stateId",stateId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveSwachchagrahisWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("courseStatus","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("swcDesignation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("districtId",districtId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public List<Swachchagrahi> getInactiveSwachchagrahisWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("courseStatus","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("swcDesignation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("blockId",blockId)
        ));
        return (List<Swachchagrahi>) criteria.list();
    }

    @Override
    public Long getCountOfInactiveSwachchagrahisForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("courseStatus","INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("swcDesignation","SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("districtId",districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
