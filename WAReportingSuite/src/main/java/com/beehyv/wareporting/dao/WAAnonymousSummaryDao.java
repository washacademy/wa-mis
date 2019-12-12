package com.beehyv.wareporting.dao;

import java.util.Date;

public interface WAAnonymousSummaryDao {

    Long accessedAtLeastOnce(Integer circleId, Date fromDate, Date toDate, Integer courseId);

    Long accessedNotOnce(Integer circleId, Date fromDate, Date toDate);

    Integer getAnonUsersFailed(Integer circleId, Date fromDate, Date toDate, Integer courseId);
}
