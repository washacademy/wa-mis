package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.MotherImportRejectionDao;
import com.beehyv.wareporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {

    @Override
    public Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","ACTIVE_CHILD_PRESENT"))
                .add(Restrictions.ne("rejectionReason","ABORT_STILLBIRTH_DEATH"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

}
