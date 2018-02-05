package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.District;
import com.beehyv.wareporting.model.Role;
import com.beehyv.wareporting.model.State;
import com.beehyv.wareporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserDao {
    User findByUserId(Integer userId);

    List<User> getActiveUsers();
}
