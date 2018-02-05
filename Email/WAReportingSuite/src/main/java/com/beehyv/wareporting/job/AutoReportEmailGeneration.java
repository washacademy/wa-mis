package com.beehyv.wareporting.job;

import com.beehyv.wareporting.business.EmailService;
import com.beehyv.wareporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by beehyv on 31/5/17.
 */

public class AutoReportEmailGeneration {


    @Autowired
    private EmailService emailService;

    public HashMap sendFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.waCircleWiseAnonymous);
        System.out.println("WA_Anonymous: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.waCourseCompletion);
        System.out.println("WA_Course: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.waInactive);
        System.out.println("WA_Inactive: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.swcRejected);
        System.out.println("Swc Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public void test() {
        HashMap reports = emailService.sendAllMails(ReportType.waCircleWiseAnonymous);
        HashMap errors = emailService.sendAllMails(ReportType.waCourseCompletion);
        System.out.println("Anonymous : \n" + reports.toString());
        System.out.println("Course Completion : \n" + errors.toString());
    }
}
