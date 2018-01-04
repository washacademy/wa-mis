package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.PermissionsService;
import com.beehyv.wareporting.dao.PermissionsDao;
import com.beehyv.wareporting.model.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("permissionsService")
@Transactional
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    private PermissionsDao permissionsDao;

    public Permissions findPermissionByPermissionId(Integer permissionId) {
        return permissionsDao.findByPermissionId(permissionId);
    }

    public List<Permissions> getAllPermissions() {
        return permissionsDao.getAllPermissions();
    }

    public void createNewPermission(Permissions permission) {
        permissionsDao.savePermission(permission);
    }

    public void updateExistingPermission(Permissions permission) {
        Permissions entity = permissionsDao.findByPermissionId(permission.getPermissionId());
        if(entity != null) {
            entity.setPermission(permission.getPermission());
        }
    }

    public void deleteExistingPermission(Permissions permission) {
        permissionsDao.deletePermission(permission);
    }
}
