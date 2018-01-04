package com.beehyv.wareporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 17/5/17.
 */
public interface MACourseAttemptDao {

    Long getCountForGivenDistrict(Date toDate, Integer districtId);
}
