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
        HashMap reports = emailService.sendAllMails(ReportType.maAnonymous);
        System.out.println("MA_Anonymous: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maCourse);
        System.out.println("MA_Course: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maInactive);
        System.out.println("MA_Inactive: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFourthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.sixWeeks);
        System.out.println("Kilkari_SixWeeks: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFifthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.selfDeactivated);
        System.out.println("Kilkari_SelfDeactivated: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSixthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.lowUsage);
        System.out.println("Kilkari_LowUsage: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyFirstMail() {
        HashMap errors = emailService.sendAllMails(ReportType.flwRejected);
        System.out.println("Flw Rejected reports: ");
        System.out.println(errors.toString());
        HashMap reports = emailService.sendAllMails(ReportType.childRejected);
        System.out.println("Child Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklySecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.motherRejected);
        System.out.println("Mother Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.flwRejected);
        System.out.println("Flw Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public void test() {
//        System.out.println(new Date());
        HashMap reports = emailService.sendAllMails(ReportType.maAnonymous);
        HashMap errors = emailService.sendAllMails(ReportType.maCourse);
//        HashMap errors1 = emailService.sendAllMails(ReportType.maInactive);
//        HashMap errors2 = emailService.sendAllMails(ReportType.sixWeeks);
//        HashMap errors3 = emailService.sendAllMails(ReportType.selfDeactivated);
//        HashMap errors4 = emailService.sendAllMails(ReportType.lowUsage);
        System.out.println("Anonymous : \n" + reports.toString());
        System.out.println("Course Completion : \n" + errors.toString());
//        System.out.println("2"+errors1.toString());
//        System.out.println("3"+errors2.toString());
//        System.out.println("4"+errors3.toString());
//        System.out.println("5"+errors4.toString());
//
//

    }
}
