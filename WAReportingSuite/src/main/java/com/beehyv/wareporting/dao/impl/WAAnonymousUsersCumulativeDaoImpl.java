package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WAAnonymousUsersCumulativeDao;
import com.beehyv.wareporting.model.WAAnonymousUsersSummary;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository("waAnonymousUsersCumulativeDao")
public class WAAnonymousUsersCumulativeDaoImpl extends AbstractDao<Integer,WAAnonymousUsersSummary> implements WAAnonymousUsersCumulativeDao{

    @Override
    public WAAnonymousUsersSummary getWAAnonymousCumulativeSummary(Integer circleId, Date toDate){

        WAAnonymousUsersSummary waAnonymousUsersSummary;

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("circleId",circleId),
                Restrictions.eq("date",toDate)
        ));

        List<WAAnonymousUsersSummary> result = (List<WAAnonymousUsersSummary>) criteria.list();
        if(result.isEmpty()){
            waAnonymousUsersSummary = new WAAnonymousUsersSummary(0,"NA",0,0,0,0,0,0);
            return waAnonymousUsersSummary;
        }

        waAnonymousUsersSummary = result.get(0);
        waAnonymousUsersSummary.setCircleName(waAnonymousUsersSummary.getCircleName() == null ? "NA": waAnonymousUsersSummary.getCircleName());
        waAnonymousUsersSummary.setCircleId(waAnonymousUsersSummary.getCircleId() == null ? 0 : waAnonymousUsersSummary.getCircleId());
        waAnonymousUsersSummary.setAnonymousUsersStartedCourse(waAnonymousUsersSummary.getAnonymousUsersStartedCourse() == null ? 0 : waAnonymousUsersSummary.getAnonymousUsersStartedCourse());
        waAnonymousUsersSummary.setAnonymousUsersPursuingCourse(waAnonymousUsersSummary.getAnonymousUsersPursuingCourse() == null ? 0 : waAnonymousUsersSummary.getAnonymousUsersPursuingCourse());
        waAnonymousUsersSummary.setAnonymousUsersNotPursuingCourse(waAnonymousUsersSummary.getAnonymousUsersNotPursuingCourse() == null ? 0 : waAnonymousUsersSummary.getAnonymousUsersNotPursuingCourse());
        waAnonymousUsersSummary.setAnonymousUsersCompletedCourse(waAnonymousUsersSummary.getAnonymousUsersCompletedCourse() == null ? 0 : waAnonymousUsersSummary.getAnonymousUsersCompletedCourse());
        waAnonymousUsersSummary.setAnonymousUsersCompletedCourse(waAnonymousUsersSummary.getAnonymousUsersFailedCourse() == null ? 0 : waAnonymousUsersSummary.getAnonymousUsersFailedCourse());

        return waAnonymousUsersSummary;
    }
}
