package com.beehyv.wareporting.dao;

import java.util.Date;

public interface MotherImportRejectionDao {

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

}
