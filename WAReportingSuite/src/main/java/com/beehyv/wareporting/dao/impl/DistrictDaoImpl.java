package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.DistrictDao;
import com.beehyv.wareporting.model.District;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("districtDao")
public class DistrictDaoImpl extends AbstractDao<Integer, District> implements DistrictDao {
    @Override
    public District findByDistrictId(Integer districtId) throws NullPointerException{
        return getByKey(districtId);
    }

    @Override
    public District findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (District) criteria.list().get(0);
    }
    @Override
    public List<District> findByName(String districtName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtName", districtName).ignoreCase());
        return (List<District>) criteria.list();

    }

    public List<District> getDistrictsOfState(Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateOfDistrict", stateId));
        return (List<District>) criteria.list();
    }

}
