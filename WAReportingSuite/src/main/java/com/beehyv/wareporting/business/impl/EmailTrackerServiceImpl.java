package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.EmailTrackerService;
import com.beehyv.wareporting.dao.EmailTrackerDao;
import com.beehyv.wareporting.model.EmailTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("emailTrackerSe")
public class EmailTrackerServiceImpl implements EmailTrackerService {

    @Autowired
    private EmailTrackerDao emailTrackerDao;

    @Override
    public void saveEmailDetails(EmailTracker emailTracker) {
        emailTrackerDao.saveEmailTracker(emailTracker);
    }

    @Override
    public List<EmailTracker> getAllFailedEmailsOfGivenReportTypeFromGivenDate(String reportType, Date fromDate) {
        return emailTrackerDao.getAllFailedEmailsForGivenReportType(reportType, fromDate);
    }
}
