package com.beehyv.wareporting.dao;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariLowUsageDao {

    Long getCountOfLowUsageUsersForGivenDistrict(String month, Integer districtId);
}
