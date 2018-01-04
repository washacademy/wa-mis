package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.FrontLineWorkersDao;
import com.beehyv.wareporting.model.FrontLineWorkers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("frontLineWorkersDao")
public class FrontLineWorkersDaoImpl extends AbstractDao<Integer, FrontLineWorkers> implements FrontLineWorkersDao {

    @Override
    public Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("status", "INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate", toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation", "ASHA").ignoreCase(),
                Restrictions.eq("district", districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
