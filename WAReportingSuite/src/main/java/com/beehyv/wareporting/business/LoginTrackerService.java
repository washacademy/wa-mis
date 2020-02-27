package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.LoginTracker;

import java.util.Date;

public interface LoginTrackerService {

    void saveLoginDetails(LoginTracker loginTracker);

    Date getLastLoginTime(Integer userId);
}
