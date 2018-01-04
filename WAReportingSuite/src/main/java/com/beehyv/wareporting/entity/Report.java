package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 4/6/17.
 */
public class Report {
    private String name;

    private String reportEnum;

    private String icon;

    private String service;

    public Report(String name, String reportEnum, String icon, String service){
        this.name = name;
        this.reportEnum = reportEnum;
        this.icon = icon;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportEnum() {
        return reportEnum;
    }

    public void setReportEnum(String reportEnum) {
        this.reportEnum = reportEnum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
