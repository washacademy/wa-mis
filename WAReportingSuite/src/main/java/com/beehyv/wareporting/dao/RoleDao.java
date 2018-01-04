package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Role;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface RoleDao {
    Role findByRoleId(Integer roleId);

    List<Role> findByRoleDescription(String role);

    List<Role> getAllRoles();

    void saveRole(Role role);

    void deleteRole(Role role);
}
