package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.KilkariLowUsageDao;
import com.beehyv.wareporting.model.KilkariLowUsage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariLowUsageDao")
public class KilkariLowUsageDaoImpl extends AbstractDao<Integer, KilkariLowUsage> implements KilkariLowUsageDao {

    @Override
    public Long getCountOfLowUsageUsersForGivenDistrict(String month, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth", month)
        )
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());


        return (Long) criteria.uniqueResult();
    }


}
