package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.AnonymousUsersDao;
import com.beehyv.wareporting.entity.AnonymousUser;
import com.beehyv.wareporting.model.WAAnonymousUsersSummary;
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
public class  AnonymousUsersDaoImpl extends AbstractDao<Integer,WAAnonymousUsersSummary> implements AnonymousUsersDao{

    @Override
    public List<AnonymousUser> getAnonymousUsers(Date fromDate, Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledTime"));
        criteria.add(Restrictions.and(
                Restrictions.lt("lastCalledTime",toDate),
                Restrictions.ge("lastCalledTime",fromDate)
        ));
        return (List<AnonymousUser>)criteria.list();
    }

    @Override
    public List<AnonymousUser> getAnonymousUsersByCircle(Date fromDate, Date toDate, String circleName) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledDate"));
        criteria.add(Restrictions.and(
                Restrictions.lt("lastCalledTime",toDate),
                Restrictions.ge("lastCalledTime",fromDate),
                Restrictions.eq("circleName",circleName)));
        return (List<AnonymousUser>)criteria.list();
    }


}

