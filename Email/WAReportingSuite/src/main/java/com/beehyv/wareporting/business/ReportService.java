package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.enums.ReportType;
import com.beehyv.wareporting.model.Circle;
import com.beehyv.wareporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface ReportService {

    List<String> getReportPathName(ReportRequest reportRequest);

    List<Circle> getUserCircles(User user);

    ReportType getReportTypeByName(String reportName);

    String getReportNameByReportType(ReportType reportType);

    String getMonthName(Date toDate);

    String getDateMonthYear(Date toDate);


}
