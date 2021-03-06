package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.AnonymousUsersDao;
import com.beehyv.wareporting.model.WACircleWiseAnonymousUsersLineListing;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("anonymousUsersDao")
public class  AnonymousUsersDaoImpl extends AbstractDao<Integer,WACircleWiseAnonymousUsersLineListing> implements AnonymousUsersDao{

    @Override
    public List<WACircleWiseAnonymousUsersLineListing> getAnonymousUsers(Date fromDate, Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCallEndTime"));
        criteria.add(Restrictions.and(
                Restrictions.lt("lastCallEndTime",toDate),
                Restrictions.ge("lastCallEndTime",fromDate)
        ));
        return (List<WACircleWiseAnonymousUsersLineListing>)criteria.list();
    }

    @Override
    public List<WACircleWiseAnonymousUsersLineListing> getAnonymousUsersByCircle(Date fromDate, Date toDate, String circleName) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCallEndDate"));
        criteria.add(Restrictions.and(
                Restrictions.lt("lastCallEndTime",toDate),
                Restrictions.ge("lastCallEndTime",fromDate),
                Restrictions.eq("circleName",circleName)));
        return (List<WACircleWiseAnonymousUsersLineListing>)criteria.list();
    }


}

