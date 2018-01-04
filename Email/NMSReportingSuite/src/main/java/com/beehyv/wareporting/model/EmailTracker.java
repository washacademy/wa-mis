package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_tracker")
public class EmailTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email_successful")
    private Boolean emailSuccessful;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "file_name")
    private String fileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isEmailSuccessful() {
        return emailSuccessful;
    }

    public void setEmailSuccessful(Boolean emailSuccessful) {
        this.emailSuccessful = emailSuccessful;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getTime() {
        return creationDate;
    }

    public void setTime(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
