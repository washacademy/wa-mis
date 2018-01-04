package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.enums.ReportType;

/**
 * Created by beehyv on 26/5/17.
 */
public interface ReportTypeDao {

    ReportType getReportTypeByName(String reportName);

}
