package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.WACourseFirstCompletion;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface WACourseAttemptDao {

   List<WACourseFirstCompletion> getSuccessFulCompletion(Date toDate);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(Date toDate, Integer districtId);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithBlockId(Date toDate, Integer blockId);

   Long getCountForGivenDistrict(Date toDate,Integer districtId);
}
