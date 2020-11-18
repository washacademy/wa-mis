package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {


    waPerformance("Performance_Report","Performance Report","M","Performance Aggregate Report"),
    waSubscriber("Subscriber_Report","Subscriber Report","M","Subscriber Aggregate Report"),
    waCumulativeSummary("Cumulative_Summary","Cumulative Summary Report","M","Cumulative Summery Aggregate Report"),
    waCourseCompletion("Cumulative_Course_Completion", "Completion Line-Listing Report", "M","Completion Line-Listing Report"),
    waCircleWiseAnonymous("Circle_Wise_Anonymous_Users", "Circle-Wise Anonymous Users Line-Listing Report", "M", "Circle Wise Anonymous Line-Listing Report"),
    waInactive("Cumulative_Inactive_Users", "Inactive Users Line-Listing Report", "M", "Inactive Users Line-Listing Report"),
    swcRejected("User_Import_Rejects","Academy Rejected Line-Listing Report", "M", "Rejected Line-Listing Report"),
    waAnonymousSummary("Anonymous_Users_Summary", "Anonymous Users Summary Report", "M", "Anonymous Users Summary Report");

    private String reportType;
    private String reportName;
    private String serviceType;
    private String reportHeader;

    public String getReportType() {
        return reportType;
    }
    public String getReportName(){
        return reportName;
    }
    public String getServiceType(){
        return serviceType;
    }
    public String getReportHeader() {
        return reportHeader;
    }

    ReportType(String reportType, String reportName, String serviceType, String reportHeader) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.serviceType = serviceType;
        this.reportHeader=reportHeader;
    }

    public static ReportType getType(String test){
        for (ReportType type: ReportType.values()) {
            if(type.reportType.equalsIgnoreCase(test)){
                return type;
            }
        }
        return null;
    }


}
