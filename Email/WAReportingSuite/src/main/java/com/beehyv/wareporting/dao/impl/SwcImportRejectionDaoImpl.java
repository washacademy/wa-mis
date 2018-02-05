package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.SwcImportRejectionDao;
import com.beehyv.wareporting.model.SwcImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("swcImportRejectionDao")
public class SwcImportRejectionDaoImpl extends AbstractDao<Long, SwcImportRejection> implements SwcImportRejectionDao {

    @Override
    public Long getCountOfSwcRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
