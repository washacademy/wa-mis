package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.model.User;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  HashMap startBulkDataImport(User user);

  void getBulkDataImportCSV();

  void getCumulativeCourseCompletionFiles( Date toDate);

  void getCircleWiseAnonymousFiles(Date fromDate,Date toDate);

  void getCumulativeInactiveFiles(Date toDate);

  void createFiles(String reportType);

  void createFolders(String reportType);

  void createSpecificReport(ReportRequest reportRequest);

  void createFlwImportRejectedFiles(Date toDate);

}
