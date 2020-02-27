package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.LoginTrackerDao;
import com.beehyv.wareporting.model.LoginTracker;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("loginTrackerDao")
public class LoginTrackerDaoImpl extends AbstractDao<Integer, LoginTracker> implements LoginTrackerDao{
    @Override
    public void saveLoginDetails(LoginTracker loginTracker) {
        persist(loginTracker);
    }

    @Override
    public LoginTracker findLoginDetailsById(Integer loginId) {
        return getByKey(loginId);
    }

    @Override
    public void deleteLoginDetails(LoginTracker loginTracker) {
        delete(loginTracker);
    }

    @Override
    public List<LoginTracker> getAllLoginDetailsForUser(String userId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.eq("userId", userId));

        return criteria.list();
    }

    @Override
    public List<LoginTracker> getAllLoginDetailsByDate(Date fromDate, Date toDate) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.ge("loginTime", fromDate));
        criteria.add(Restrictions.lt("loginTime", toDate));

        return criteria.list();
    }
    @Override
    public Date  getLastLoginTime(Integer userId) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projList = Projections.projectionList();
        criteria.add(Restrictions.eq("userId", userId));
        projList.add(Projections.max("loginTime"));
        criteria.setProjection(projList);
        return (Date) criteria.uniqueResult();
    }
}
