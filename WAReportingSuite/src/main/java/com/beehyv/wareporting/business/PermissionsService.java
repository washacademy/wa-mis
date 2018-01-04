package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.Permissions;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface PermissionsService {
    public Permissions findPermissionByPermissionId(Integer permissionId);

    public List<Permissions> getAllPermissions();

    public void createNewPermission(Permissions permission);

    public void updateExistingPermission(Permissions permission);

    public void deleteExistingPermission(Permissions permission);
}
