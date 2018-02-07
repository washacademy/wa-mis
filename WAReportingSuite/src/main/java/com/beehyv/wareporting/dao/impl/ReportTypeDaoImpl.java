package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.ReportTypeDao;
import com.beehyv.wareporting.enums.ReportType;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 26/5/17.
 */
@Repository("reportTypeDao")
public class ReportTypeDaoImpl extends AbstractDao<String,ReportType> implements ReportTypeDao {

    @Override
    public ReportType getReportTypeByName(String reportName) {
        return ReportType.valueOf(reportName);
    }

    @Override
    public ReportType getReportTypeByReportEnum(String reportEnum) {
        return ReportType.getType(reportEnum);
    }
}
