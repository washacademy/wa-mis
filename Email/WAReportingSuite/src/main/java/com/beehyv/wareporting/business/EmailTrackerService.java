package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.EmailTracker;

import java.util.Date;
import java.util.List;

public interface EmailTrackerService {

    void saveEmailDetails(EmailTracker emailTracker);

    List<EmailTracker> getAllFailedEmailsOfGivenReportTypeFromGivenDate(String reportType, Date fromDate);
}
