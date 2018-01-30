package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.SwcImportRejectionDao;
import com.beehyv.wareporting.model.SwcImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("swcImportRejectionDao")
public class SwcImportRejectionDaoImpl extends AbstractDao<Long, SwcImportRejection> implements SwcImportRejectionDao {
    @Override
    public List<SwcImportRejection> getAllRejectedSwcImportRecords(Date fromDate, Date toDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","SWC_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","SWC_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","JOB_STATUS_INACTIVE"));

        return (List<SwcImportRejection>)criteria.list();
    }

    @Override
    public List<SwcImportRejection> getAllRejectedSwcImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","SWC_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","SWC_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","JOB_STATUS_INACTIVE"));
        return (List<SwcImportRejection>) criteria.list();
    }
    @Override
    public List<SwcImportRejection> getAllRejectedSwcImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","SWC_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","SWC_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","JOB_STATUS_INACTIVE"));

        return (List<SwcImportRejection>) criteria.list();
    }

    @Override
    public List<SwcImportRejection> getAllRejectedSwcImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("blockId", blockId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","SWC_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","SWC_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","JOB_STATUS_INACTIVE"));

        return (List<SwcImportRejection>) criteria.list();
    }

    @Override
    public Long getCountOfSwcRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","SWC_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","SWC_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","JOB_STATUS_INACTIVE"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
