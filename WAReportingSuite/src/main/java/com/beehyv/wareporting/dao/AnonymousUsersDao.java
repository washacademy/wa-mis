package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.AnonymousUsers;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface AnonymousUsersDao {

    List<AnonymousUsers> getAnonymousUsers(Date fromDate, Date toDate);

    List<AnonymousUsers> getAnonymousUsersCircle(Date fromDate,Date toDate,String circleName);

}
