package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.LoginTrackerService;
import com.beehyv.wareporting.dao.LoginTrackerDao;
import com.beehyv.wareporting.model.LoginTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service("loginTrackerService")
@Transactional
public class LoginTrackerServiceImpl implements LoginTrackerService{

    @Autowired
    LoginTrackerDao loginTrackerDao;

    @Override
    public void saveLoginDetails(LoginTracker loginTracker) {
        loginTrackerDao.saveLoginDetails(loginTracker);
    }

    @Override
    public Date getLastLoginTime(Integer userId) {
        return loginTrackerDao.getLastLoginTime(userId);
    }
}
