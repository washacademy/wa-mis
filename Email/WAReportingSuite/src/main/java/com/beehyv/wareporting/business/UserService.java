package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserService {
    User findUserByUserId(Integer userId);

    List<User> findAllActiveUsers();
}
