package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.WAAnonymousUsersSummary;

import java.util.Date;

public interface WAAnonymousUsersCumulativeDao {

    WAAnonymousUsersSummary getWAAnonymousCumulativeSummary(Integer circleId, Date toDate, Integer courseId);
}
