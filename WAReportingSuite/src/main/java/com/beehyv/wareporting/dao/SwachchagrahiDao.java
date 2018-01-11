package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Swachchagrahi;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface SwachchagrahiDao {

    List<Swachchagrahi> getInactiveSwachchagrahis(Date toDate);

    List<Swachchagrahi> getInactiveSwachchagrahisWithStateId(Date toDate, Integer stateId);

    List<Swachchagrahi> getInactiveSwachchagrahisWithDistrictId(Date toDate, Integer districtId);

    List<Swachchagrahi> getInactiveSwachchagrahisWithBlockId(Date toDate, Integer blockId);

    Long getCountOfInactiveSwachchagrahisForGivenDistrict(Date toDate, Integer districtId);
}
