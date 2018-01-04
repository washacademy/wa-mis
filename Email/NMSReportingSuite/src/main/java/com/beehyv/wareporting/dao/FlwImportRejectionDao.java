package com.beehyv.wareporting.dao;

import java.util.Date;

public interface FlwImportRejectionDao {

    Long getCountOfFlwRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId);


}
