package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {


    waPerformance("WA_Performance_Report","Performance Report","M","WA Performance Aggregate Report"),
    waSubscriber("WA_Subscriber_Report","Subscriber Report","M","WA Subscriber Aggregate Report"),
    waCumulativeSummary("WA_Cumulative_Summary","Cumulative Summary Report","M","WA Cumulative Summery Aggregate Report"),
    waCourseCompletion("WA_Cumulative_Course_Completion", "Completion Line-Listing Report", "M","WA Completion Line-Listing Report"),
    waCircleWiseAnonymous("WA_Circle_Wise_Anonymous_Users", "Circle-Wise Anonymous Users Line-Listing Report", "M", "WA Circle Wise Anonymous Line-Listing Report"),
    waInactive("WA_Cumulative_Inactive_Users", "Inactive Users Line-Listing Report", "M", "WA Inactive Users Line-Listing Report"),
    swcRejected("WA_Swachchagrahi_Import_Rejects","Academy Rejected Line-Listing Report", "M", "WA Rejected Line-Listing Report"),
    waAnonymousSummary("WA_Anonymous_Users_Summary", "Anonymous Users Summary Report", "M", "WA Anonymous Users Summary Report");

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
