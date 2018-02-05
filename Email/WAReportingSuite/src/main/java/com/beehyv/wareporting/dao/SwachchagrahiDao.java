package com.beehyv.wareporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
public interface SwachchagrahiDao {

    Long getCountOfInactiveSwachchagrahisForGivenDistrict(Date toDate, Integer districtId);
}
