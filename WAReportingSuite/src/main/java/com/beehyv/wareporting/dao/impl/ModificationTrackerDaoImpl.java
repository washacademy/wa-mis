package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.ModificationTrackerDao;
import com.beehyv.wareporting.model.ModificationTracker;
import com.beehyv.wareporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("modificationTrackerDao")
public class ModificationTrackerDaoImpl extends AbstractDao<Integer, ModificationTracker> implements ModificationTrackerDao {

    @Override
    public ModificationTracker findModificationById(Integer modificationId) {
        return getByKey(modificationId);
    }

    @Override
    public void saveModification(ModificationTracker modification) {
        persist(modification);
    }

    @Override
    public void deleteModification(ModificationTracker modification) {
        delete(modification);
    }

    @Override
    public List<ModificationTracker> getAllModifications() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("modificationId"));
        return (List<ModificationTracker>) criteria.list();
    }

    @Override
    public List<ModificationTracker> getAllModificationsByUser(User userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modifiedByUserId", userId));
        return (List<ModificationTracker>) criteria.list();
    }

    @Override
    public List<ModificationTracker> getAllModifiersForUser(User userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modifiedUserId", userId));
        return (List<ModificationTracker>) criteria.list();
    }

    @Override
    public List<ModificationTracker> getAllModificationsByDate(Date date) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("modificationDate", date));
        return (List<ModificationTracker>) criteria.list();
    }
}
