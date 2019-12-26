package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.WACircleWiseAnonymousUsersLineListing;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface AnonymousUsersDao {

    List<WACircleWiseAnonymousUsersLineListing> getAnonymousUsers(Date fromDate, Date toDate, Integer courseId);

    List<WACircleWiseAnonymousUsersLineListing> getAnonymousUsersByCircle(Date fromDate, Date toDate, String circleName, Integer courseId);

}
