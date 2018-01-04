package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.WACumulativeSummary;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface AggregateReportsService {

    List<WACumulativeSummary> getCumulativeSummaryWAReport(Integer locationId, String locationType, Date toDate);

}
