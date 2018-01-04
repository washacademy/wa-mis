package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.District;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    District findByDistrictId(Integer districtId);

    List<District> getDistrictsOfState(Integer state);

}
