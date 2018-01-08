package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.AnonymousUsersDao;
import com.beehyv.wareporting.model.AnonymousUsers;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("anonymousUsersDao")
public class AnonymousUsersDaoImpl extends AbstractDao<Integer, AnonymousUsers> implements AnonymousUsersDao {


}

