package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.SwachchagrahiDao;
import com.beehyv.wareporting.model.Swachchagrahi;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("swachchagrahiDao")
public class SwachchagrahiDaoImpl extends AbstractDao<Integer,Swachchagrahi> implements SwachchagrahiDao {

    @Override
    public Long getCountOfInactiveSwachchagrahisForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("status", "INACTIVE").ignoreCase(),
                Restrictions.lt("creationDate", toDate),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation", "SWACHCHAGRAHI").ignoreCase(),
                Restrictions.eq("district", districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
