package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WACourseAttemptDao;
import com.beehyv.wareporting.model.Swachchagrahi;
import com.beehyv.wareporting.model.WACourseFirstCompletion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 17/5/17.
 */
@Repository("waCourseAttemptDao")
public class WACourseAttemptDaoImpl extends AbstractDao<Integer,Swachchagrahi> implements WACourseAttemptDao {

    @Override
    public Long getCountForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria= getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("districtId",districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }
}
