package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.PanchayatDao;
import com.beehyv.wareporting.model.Panchayat;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
@Repository("panchayatDao")
public class PanchayatDaoImpl extends AbstractDao<Integer, Panchayat> implements PanchayatDao {

    @Override
    public Panchayat findByPanchayatId(Integer panchayatId) {
        return getByKey(panchayatId);
    }

    @Override
    public Panchayat findByLocationId(Integer locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (Panchayat) criteria.list().get(0);
    }

    @Override
    public List<Panchayat> findByName(String panchayatName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("panchayatName", panchayatName).ignoreCase());
        return (List<Panchayat>) criteria.list();
    }

    @Override
    public List<Panchayat> getPanchayatsOfBlock(Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockOfPanchayat", districtId));
        return (List<Panchayat>) criteria.list();
    }

    @Override
    public Integer getBlockOfPanchayat(Panchayat panchayat) {
        return null;
    }
    
}
