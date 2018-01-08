package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.SwcImportRejection;

import java.util.Date;
import java.util.List;

public interface SwcImportRejectionDao {

    List<SwcImportRejection> getAllRejectedSwcImportRecords(Date fromDate, Date toDate);

    List<SwcImportRejection> getAllRejectedSwcImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<SwcImportRejection> getAllRejectedSwcImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<SwcImportRejection> getAllRejectedSwcImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOfSwcRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId);


}
