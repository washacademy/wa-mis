package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.entity.AnonymousUser;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface AnonymousUsersDao {

    List<AnonymousUser> getAnonymousUsers(Date fromDate, Date toDate);

    List<AnonymousUser> getAnonymousUsersByCircle(Date fromDate, Date toDate, String circleName);

}
