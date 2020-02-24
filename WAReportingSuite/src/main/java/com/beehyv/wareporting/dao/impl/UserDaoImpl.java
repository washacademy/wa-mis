package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.UserDao;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.AccessType;
import com.beehyv.wareporting.enums.AccountStatus;
import com.beehyv.wareporting.model.District;
import com.beehyv.wareporting.model.Role;
import com.beehyv.wareporting.model.State;
import com.beehyv.wareporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public User findByEmailId(String emailId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("emailId", emailId),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("phoneNumber", phoneNumber),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> findByCreationDate(Date creationDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("creationDate", creationDate));
        return (List<User>) criteria.list();
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

    @Override
    public List<User> getAllUsers() {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getUsersByAccountStatus(String accountStatus) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountStatus", accountStatus));
        return (List<User>) criteria.list();
    }

    @Override
    public List<User> getUsersByRole(Role roleId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("roleId", roleId.getRoleId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())
        ));
        return (List<User>) criteria.list();
    }

    @Override
    public <E> List<User> getUsersByLocation(String propertyName, E location) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq(propertyName, location),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())
        ));
        return (List<User>) criteria.list();
    }

    @Override
    public User getNationalAdmin(Role adminRole) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("accessLevel", AccessLevel.NATIONAL.getAccessLevel()),
                Restrictions.eq("roleId", adminRole.getRoleId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())
        ));
        return (criteria.list().size() == 0 ? null : (User) criteria.list().get(0));
    }

    @Override
    public User getStateAdmin(Role adminRole, State state) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("accessLevel", AccessLevel.STATE.getAccessLevel()),
                Restrictions.eq("roleId", adminRole.getRoleId()),
                Restrictions.eq("stateId", state.getStateId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())
        ));
        return (criteria.list().size() == 0 ? null : (User) criteria.list().get(0));
    }

    @Override
    public User getDistrictAdmin(Role adminRole, District district) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("accessLevel", AccessLevel.DISTRICT.getAccessLevel()),
                Restrictions.eq("roleId", adminRole.getRoleId()),
                Restrictions.eq("districtId", district.getDistrictId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus())
        ));
        return (criteria.list().size() == 0 ? null : (User) criteria.list().get(0));
    }

    @Override
    public void saveUser(User user) {
        persist(user);
    }

    @Override
    public boolean isAdminCreated(District district) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("districtId", district.getDistrictId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()),
                Restrictions.eq("accessLevel", AccessLevel.DISTRICT.getAccessLevel()),
                Restrictions.eq("roleName", AccessType.ADMIN.getAccessType())
        ));
        List<User> Admins=(List<User>) criteria.list();
        if(Admins==null||Admins.size()==0){
            return false;
        }
        else return true;
    }

    @Override
    public boolean isAdminCreated(State state) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("stateId", state.getStateId()),
                Restrictions.eq("accountStatus", AccountStatus.ACTIVE.getAccountStatus()),
                Restrictions.eq("accessLevel", AccessLevel.DISTRICT.getAccessLevel()),
                Restrictions.eq("roleName", AccessType.ADMIN.getAccessType())
        ));
        List<User> Admins=(List<User>) criteria.list();
        if(Admins==null||Admins.size()==0){
            return false;
        }
        else return true;
    }

    @Override
    public void updateUser(User user){
        update(user);
    }

}
