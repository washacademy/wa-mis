package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.EmailInfo;
import com.beehyv.wareporting.enums.ReportType;

import java.util.HashMap;

/**
 * Created by beehyv on 25/5/17.
 */
public interface EmailService {

    String sendMail(EmailInfo emailInfo);

    String getBody(String reportName, String place, String monthAndYear, String name);

    HashMap sendAllMails(ReportType reportType);

}
