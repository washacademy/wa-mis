package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {


    waPerformance("WA_Performance","WA Performance","M","WA Performance Aggregate Report"),
    waSubscriber("WA_Subscriber","WA Subscriber","M","WA Subscriber Aggregate Report"),
    waCumulative("WA_Cumulative_Summary","WA Cumulative Summary","M","WA Cumulative Summery Aggregate Report"),
    waCourse("WA_Cumulative_Course_Completion", "Cumulative Course Completion", "M","WA Completion Line-Listing Report"),
    waAnonymous("WA_Anonymous_Users", "Circle wise Anonymous Users", "M", "WA Circle Wise Anonymous Line-Listing Report"),
    waInactive("WA_Cumulative_Inactive_Users", "Cumulative Inactive Users", "M", "WA Inactive Users Line-Listing Report"),
    swcRejected("WA_Swachchagrahi_Import_Rejects","Swachchagrahi Rejected Records", "M", "WA Rejected Line-Listing Report");

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
