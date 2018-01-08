package com.beehyv.wareporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSixWeeksNoAnswerDao {

    Long getCountOfDeactivatedForDistrict(Date fromDate, Date toDate, Integer districtId);

    Long getCountOfLowListenershipUsersForDistrict(Date fromDate, Date toDate, Integer districtId);

}
