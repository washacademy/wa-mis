package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.ChildImportRejectionDao;
import com.beehyv.wareporting.model.ChildImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("childImportRejectionDao")
public class ChildImportRejectionDaoImpl extends AbstractDao<Long, ChildImportRejection> implements ChildImportRejectionDao {

    @Override
    public Long getCountOfRejectedChildRecords(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.ne("rejectionReason","INVALID_DOB"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
