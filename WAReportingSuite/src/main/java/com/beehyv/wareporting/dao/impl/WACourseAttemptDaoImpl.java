package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WACourseAttemptDao;
import com.beehyv.wareporting.model.WACourseFirstCompletion;
import com.beehyv.wareporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
@Repository("waCourseAttemptDao")
public class WACourseAttemptDaoImpl extends AbstractDao<Integer, User> implements WACourseAttemptDao {


    @Override
    public List<WACourseFirstCompletion> getSuccessFulCompletion(Date toDate, Integer courseId) {
        Criteria criteria = getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
            .add(Restrictions.eq("courseId", courseId));
        return criteria.list();
    }

    @Override
    public List<WACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId, Integer courseId) {
        Criteria criteria = getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("stateId",stateId))
                .add(Restrictions.eq("courseId", courseId));
        return criteria.list();
    }

    @Override
    public List<WACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(Date toDate, Integer districtId, Integer courseId) {
        Criteria criteria = getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("courseId", courseId));
        return criteria.list();
    }

    @Override
    public List<WACourseFirstCompletion> getSuccessFulCompletionWithBlockId(Date toDate, Integer blockId, Integer courseId) {
        Criteria criteria = getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("blockId",blockId))
                .add(Restrictions.eq("courseId", courseId));
        return criteria.list();
    }

    @Override
    public Long getCountForGivenDistrict(Date toDate, Integer districtId, Integer courseId) {
        Criteria criteria= getSession().createCriteria(WACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("courseId", courseId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }
}
