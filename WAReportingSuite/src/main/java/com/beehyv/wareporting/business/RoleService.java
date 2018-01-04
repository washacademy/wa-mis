package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.Role;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface RoleService {
    public Role findRoleByRoleId(Integer roleId);

    public Role findRoleByRoleDesc(String desc);

    public List<Role> getAllRoles();

    public List<Role> getRoles();

    public void createNewRole(Role role);

    public void updateExistingRole(Role role);

    public void deleteExistingRole(Role role);
}
