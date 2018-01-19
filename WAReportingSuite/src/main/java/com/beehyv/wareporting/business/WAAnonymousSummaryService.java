package com.beehyv.wareporting.business;

import java.util.Date;

public interface WAAnonymousSummaryService {


    Long getAccessedCount(Integer circleId, Date fromDate, Date toDate);

    Long getNotAccessedcount(Integer circleId, Date fromDate, Date toDate);

    Integer getAnonUsersFailed(Integer circleId, Date fromDate, Date toDate);
}
