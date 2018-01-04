package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.WACumulativeSummary;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 20/9/17.
 */
public interface WASubscriberDao {

    List<Object> getWASubscriberCountDaily(String locationType, Integer locationId, Date fromDate,Date toDate);

    List<WACumulativeSummary> getWASubscriberCountSummary(String locationType, Integer locationId, Date fromDate, Date toDate);

//    List<WACumulativeSummary> getWASubscriberCountSummaryEnd(String locationType, Integer locationId, Date fromDate, Date toDate);

}
