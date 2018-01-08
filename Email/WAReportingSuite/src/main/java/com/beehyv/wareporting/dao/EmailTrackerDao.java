package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.EmailTracker;

import java.util.Date;
import java.util.List;

public interface EmailTrackerDao {

    void saveEmailTracker(EmailTracker emailTracker);

    List<EmailTracker> getAllFailedEmailsForGivenReportType(String reportType, Date fromDate);
}
