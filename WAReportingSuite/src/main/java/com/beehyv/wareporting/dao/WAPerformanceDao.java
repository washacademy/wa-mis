package com.beehyv.wareporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 22/9/17.
 */
public interface WAPerformanceDao {
    Long accessedAtLeastOnce(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long accessedNotOnce(Integer locationId, String locationType, Date fromDate, Date toDate);

    Integer getSwachchagrahisFailed(Integer locationId, String locationType, Date fromDate, Date toDate);

}
