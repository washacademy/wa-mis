package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.WACourseFirstCompletion;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
public interface WACourseAttemptDao {

   List<WACourseFirstCompletion> getSuccessFulCompletion(Date toDate, Integer courseId);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId, Integer courseId);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(Date toDate, Integer districtId, Integer courseId);

   List<WACourseFirstCompletion> getSuccessFulCompletionWithBlockId(Date toDate, Integer blockId, Integer courseId);

   Long getCountForGivenDistrict(Date toDate,Integer districtId, Integer courseId);
}
