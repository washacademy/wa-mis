package com.beehyv.wareporting.job;

import com.beehyv.wareporting.business.AdminService;
import com.beehyv.wareporting.business.EmailService;
import com.beehyv.wareporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.beehyv.wareporting.enums.ReportType.waCourse;
import static com.beehyv.wareporting.utils.Global.isAutoGenerate;

/**
 * Created by beehyv on 31/5/17.
 */

public class AutoReportEmailGeneration {
    @Autowired
    AdminService adminService;

    @Autowired
    EmailService emailService;
    public boolean executeInternal() {
        if(isAutoGenerate()) {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.add(Calendar.MONTH, -1);
            aCalendar.set(Calendar.DATE, 1);
            aCalendar.set(Calendar.MILLISECOND, 0);
            aCalendar.set(Calendar.SECOND, 0);
            aCalendar.set(Calendar.MINUTE, 0);
            aCalendar.set(Calendar.HOUR_OF_DAY, 0);

            Date fromDate = aCalendar.getTime();

            aCalendar.add(Calendar.MONTH, 1);

            Date toDate = aCalendar.getTime();

            Date startDate = new Date(0);
        /*adminService.getCircleWiseAnonymousFiles(fromDate,toDate);*/
            System.out.println("Report generation started");
            adminService.createFiles(waCourse.getReportType());
            adminService.createFolders(ReportType.waAnonymous.getReportType());
            adminService.createFiles(ReportType.waInactive.getReportType());

            adminService.getCircleWiseAnonymousFiles(fromDate, toDate);
            System.out.println("WA_Anonymous reports generated");
            adminService.getCumulativeCourseCompletionFiles(toDate);
            System.out.println("WA_Course_Completion reports generated");
            adminService.getCumulativeInactiveFiles(toDate);
            System.out.println("WA_Inactive reports generated");
            System.out.println("Report generation done");

            return true;
        }
        return false;
    }

    public boolean executeWeekly() {
        if(isAutoGenerate()) {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.add(Calendar.DAY_OF_WEEK, -(aCalendar.get(Calendar.DAY_OF_WEEK) - 1));
            Date toDate = aCalendar.getTime();

            adminService.createFiles(ReportType.swcRejected.getReportType());

            adminService.createSwcImportRejectedFiles(toDate);
            System.out.println("SW_Rejection reports generated");
            
            return true;
        }
        return false;
    }

    public HashMap sendFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.waAnonymous);
        System.out.println("WA_Anonymous: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.waCourse);
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

    public void test(){
//        System.out.println(new Date());
    }
}
