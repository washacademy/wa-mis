package com.beehyv.wareporting.dao;


import com.beehyv.wareporting.model.LoginTracker;

import java.util.Date;
import java.util.List;

public interface LoginTrackerDao {

    public void saveLoginDetails(LoginTracker loginTracker);

    public LoginTracker findLoginDetailsById(Integer loginId);

    public void deleteLoginDetails(LoginTracker loginTracker);

    public List<LoginTracker> getAllLoginDetailsForUser(String userId);

    public List<LoginTracker> getAllLoginDetailsByDate(Date fromDate, Date toDate);

    public Date getLastLoginTime(Integer userId);

}
