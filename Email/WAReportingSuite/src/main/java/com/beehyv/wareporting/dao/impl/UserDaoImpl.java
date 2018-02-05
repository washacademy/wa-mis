package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.UserDao;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.AccessType;
import com.beehyv.wareporting.enums.AccountStatus;
import com.beehyv.wareporting.model.District;
import com.beehyv.wareporting.model.State;
import com.beehyv.wareporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Override
    public User findByUserId(Integer userId) {
        return getByKey(userId);
    }

    public User findByUserName(String username) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("username", username),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<User> getActiveUsers() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()),
                Restrictions.ne("roleId",1)
        ));
        return (List<User>) criteria.list();
    }

}
