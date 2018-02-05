package com.beehyv.wareporting.dao;

import java.util.Date;

public interface SwcImportRejectionDao {

    Long getCountOfSwcRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId);


}
