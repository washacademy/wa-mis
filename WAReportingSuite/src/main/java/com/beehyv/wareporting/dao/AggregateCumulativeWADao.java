package com.beehyv.wareporting.dao;


import com.beehyv.wareporting.model.WACumulativeSummary;

import java.util.Date;

public interface AggregateCumulativeWADao {


    WACumulativeSummary getWACumulativeSummary(Integer locationId, String locationType, Date toDate);

}