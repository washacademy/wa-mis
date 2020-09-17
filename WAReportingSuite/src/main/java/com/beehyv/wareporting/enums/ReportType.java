package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {


    waPerformance("SBM_Performance_Report","Performance Report","M","SBM Performance Aggregate Report"),
    waSubscriber("SBM_Subscriber_Report","Subscriber Report","M","SBM Subscriber Aggregate Report"),
    waCumulativeSummary("SBM_Cumulative_Summary","Cumulative Summary Report","M","SBM Cumulative Summery Aggregate Report"),
    waCourseCompletion("SBM_Cumulative_Course_Completion", "Completion Line-Listing Report", "M","SBM Completion Line-Listing Report"),
    waCircleWiseAnonymous("SBM_Circle_Wise_Anonymous_Users", "Circle-Wise Anonymous Users Line-Listing Report", "M", "SBM Circle Wise Anonymous Line-Listing Report"),
    waInactive("SBM_Cumulative_Inactive_Users", "Inactive Users Line-Listing Report", "M", "SBM Inactive Users Line-Listing Report"),
    swcRejected("SBM_Swachchagrahi_Import_Rejects","Academy Rejected Line-Listing Report", "M", "SBM Rejected Line-Listing Report"),
    waAnonymousSummary("SBM_Anonymous_Users_Summary", "Anonymous Users Summary Report", "M", "SBM Anonymous Users Summary Report");

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
