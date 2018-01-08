package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Swachchagrahi;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface FrontLineWorkersDao {
    List<Swachchagrahi> getInactiveFrontLineWorkers(Date toDate);

    List<Swachchagrahi> getInactiveFrontLineWorkersWithStateId(Date toDate, Integer stateId);

    List<Swachchagrahi> getInactiveFrontLineWorkersWithDistrictId(Date toDate, Integer districtId);

    List<Swachchagrahi> getInactiveFrontLineWorkersWithBlockId(Date toDate, Integer blockId);

    Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId);
}
