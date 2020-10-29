package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {


    waPerformance("Bharpoor_Performance_Report","Performance Report","M","Bharpoor Performance Aggregate Report"),
    waSubscriber("Bharpoor_Subscriber_Report","Subscriber Report","M","Bharpoor Subscriber Aggregate Report"),
    waCumulativeSummary("Bharpoor_Cumulative_Summary","Cumulative Summary Report","M","Bharpoor Cumulative Summery Aggregate Report"),
    waCourseCompletion("Bharpoor_Cumulative_Course_Completion", "Completion Line-Listing Report", "M","Bharpoor Completion Line-Listing Report"),
    waCircleWiseAnonymous("Bharpoor_Circle_Wise_Anonymous_Users", "Circle-Wise Anonymous Users Line-Listing Report", "M", "Bharpoor Circle Wise Anonymous Line-Listing Report"),
    waInactive("Bharpoor_Cumulative_Inactive_Users", "Inactive Users Line-Listing Report", "M", "Bharpoor Inactive Users Line-Listing Report"),
    swcRejected("Bharpoor_Swachchagrahi_Import_Rejects","Academy Rejected Line-Listing Report", "M", "Bharpoor Rejected Line-Listing Report"),
    waAnonymousSummary("Bharpoor_Anonymous_Users_Summary", "Anonymous Users Summary Report", "M", "Bharpoor Anonymous Users Summary Report");

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
