package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.business.EmailService;
import com.beehyv.wareporting.business.ReportService;
import com.beehyv.wareporting.entity.EmailInfo;
import com.beehyv.wareporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by beehyv on 16/5/17.
 */
@Controller
@RequestMapping("/wa/mail")
public class EmailController {

    @Autowired
    ReportService reportService;
    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/sendAll/{reportEnum}", method = RequestMethod.GET)
    public
    @ResponseBody
    HashMap sendAllMails(@PathVariable String reportEnum) {
        ReportType reportType = reportService.getReportTypeByName(reportEnum);
        return emailService.sendAllMails(reportType);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public
    @ResponseBody
    String send(@RequestBody EmailInfo mailInfo) {
        EmailInfo newMail = new EmailInfo();
        newMail.setFrom(mailInfo.getFrom());
        newMail.setTo(mailInfo.getTo());
        Calendar c = Calendar.getInstance();   // this takes current date
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DATE, 1);
        String fileName = mailInfo.getFileName();
        String pathName = System.getProperty("user.home") + File.separator;
        newMail.setSubject(mailInfo.getSubject());
        newMail.setFileName(fileName);
        newMail.setBody(mailInfo.getBody());
        newMail.setRootPath(pathName + fileName);
        return emailService.sendMail(newMail);
    }

}
