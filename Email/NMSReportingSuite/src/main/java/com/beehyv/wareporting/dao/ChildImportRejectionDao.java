package com.beehyv.wareporting.dao;

import java.util.Date;

public interface ChildImportRejectionDao {

    Long getCountOfRejectedChildRecords(Date fromDate, Date toDate, Integer districtId);

}
