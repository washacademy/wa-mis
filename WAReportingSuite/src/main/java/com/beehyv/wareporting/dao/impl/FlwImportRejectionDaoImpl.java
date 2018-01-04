package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.FlwImportRejectionDao;
import com.beehyv.wareporting.model.FlwImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("flwImportRejectionDao")
public class FlwImportRejectionDaoImpl extends AbstractDao<Long, FlwImportRejection> implements FlwImportRejectionDao {
    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecords(Date fromDate, Date toDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"));

        return criteria.list();
    }

    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"));
        return criteria.list();
    }
    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"));

        return criteria.list();
    }

    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("healthBlockId", blockId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_SWACHCHAGRAHI"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"));

        return criteria.list();
    }

    @Override
    public Long getCountOfFlwRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
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
