package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.RoleDao;
import com.beehyv.wareporting.model.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {
    public Role findByRoleId(Integer roleId) {
        return getByKey(roleId);
    }

    @Override
    public List<Role> findByRoleDescription(String role) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("roleDescription", role));
        return criteria.list();
    }

    @Override
    public List<Role> getAllRoles() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("roleId"));
        return (List<Role>) criteria.list();
    }

    @Override
    public void saveRole(Role role) {
        persist(role);
    }

    @Override
    public void deleteRole(Role role) {
        delete(role);
    }
}
