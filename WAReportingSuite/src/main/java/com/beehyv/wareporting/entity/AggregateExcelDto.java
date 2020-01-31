package com.beehyv.wareporting.entity;

import java.util.ArrayList;

public class AggregateExcelDto {

    private ArrayList<ArrayList<String>> reportData;
    private ArrayList<String> columnHeaders;
    private ArrayList<String> colunmFooters;
    private ArrayList<ArrayList<String>> reportData1;
    private ArrayList<String> columnHeaders1;
    private ArrayList<String> colunmFooters1;
    private String stateName;
    private String districtName;
    private String blockName;
    private String reportName;
    private String timePeriod;
    private String fileName;
    private String circleFullName;



    public String getCircleFullName() {     return circleFullName;    }

    public void setCircleFullName(String circleFullName) {     this.circleFullName = circleFullName;    }

    public ArrayList<String> getColunmFooters1() {
        return colunmFooters1;
    }

    public void setColunmFooters1(ArrayList<String> colunmFooters1) {
        this.colunmFooters1 = colunmFooters1;
    }

    public ArrayList<ArrayList<String>> getReportData() {
        return reportData;
    }

    public void setReportData(ArrayList<ArrayList<String>> reportData) {
        this.reportData = reportData;
    }

    public ArrayList<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(ArrayList<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public ArrayList<String> getColunmFooters() {
        return colunmFooters;
    }

    public void setColunmFooters(ArrayList<String> colunmFooters) {
        this.colunmFooters = colunmFooters;
    }

    public ArrayList<ArrayList<String>> getReportData1() {
        return reportData1;
    }

    public void setReportData1(ArrayList<ArrayList<String>> reportData1) {
        this.reportData1 = reportData1;
    }

    public ArrayList<String> getColumnHeaders1() {
        return columnHeaders1;
    }

    public void setColumnHeaders1(ArrayList<String> columnHeaders1) {
        this.columnHeaders1 = columnHeaders1;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String excelFileName) {
        fileName = excelFileName;
    }
}