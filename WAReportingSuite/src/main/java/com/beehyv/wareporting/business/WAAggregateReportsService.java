package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.AggregateResponseDto;

import java.util.Date;


/**
 * Created by beehyv on 19/9/17.
 */
public interface WAAggregateReportsService {

    AggregateResponseDto getWAPerformanceReport(Date fromDate, Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId);

    AggregateResponseDto getWASubscriberReport(Date fromDate, Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId);

    AggregateResponseDto getWACumulativeSummaryReport(Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId);

    AggregateResponseDto getWAAnonymousSummaryReport(Date fromDate, Date toDate, Integer circleId);
}
