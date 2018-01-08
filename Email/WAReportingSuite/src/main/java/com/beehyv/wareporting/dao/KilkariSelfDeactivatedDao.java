package com.beehyv.wareporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariSelfDeactivatedDao {

    Long getCountOfSelfDeactivatedUsers(Date fromDate, Date toDate, Integer districtId);

}
