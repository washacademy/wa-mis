package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.RoleService;
import com.beehyv.wareporting.dao.RoleDao;
import com.beehyv.wareporting.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role findRoleByRoleId(Integer roleId) {
        return roleDao.findByRoleId(roleId);
    }

    public Role findRoleByRoleDesc(String desc){
        return roleDao.findByRoleDescription(desc).get(0);
    }

    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public List<Role> getRoles(){
        List<Role> roles = getAllRoles();
        roles.remove(0);
        return roles;
    }

    public void createNewRole(Role role) {
        roleDao.saveRole(role);
    }

    public void updateExistingRole(Role role) {
        Role entity = roleDao.findByRoleId(role.getRoleId());
        if(entity != null) {
            entity.setRoleDescription(role.getRoleDescription());
            entity.setPermissionId(role.getPermissionId());
        }
    }

    public void deleteExistingRole(Role role) {
        roleDao.deleteRole(role);
    }
}
