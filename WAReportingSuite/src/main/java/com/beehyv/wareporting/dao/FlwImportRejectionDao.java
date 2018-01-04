package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.FlwImportRejection;

import java.util.Date;
import java.util.List;

public interface FlwImportRejectionDao {

    List<FlwImportRejection> getAllRejectedFlwImportRecords(Date fromDate, Date toDate);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithBlockId(Date fromDate,Date toDate, Integer blockId);

    Long getCountOfFlwRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId);


}
