package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.EmailInfo;
import com.beehyv.wareporting.enums.ReportType;

import java.util.HashMap;

/**
 * Created by beehyv on 25/5/17.
 */
public interface EmailService {

    public String sendMail(EmailInfo emailInfo);

    public String getBody(String reportName, String place, String monthAndYear, String name);

    public HashMap sendAllMails(ReportType reportType);

}
