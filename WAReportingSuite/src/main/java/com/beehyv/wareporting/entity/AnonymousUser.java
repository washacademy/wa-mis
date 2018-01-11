package com.beehyv.wareporting.entity;

import java.util.Date;

public class AnonymousUser {
    
    private Long Id;
    
    private Long mobileNumber;
    
    private String operator;
    
    private String circleName;
    
    private Date courseStartDate;
    
    private Date firstCompletionDate;

    private Date lastCallEndDate;

    private String lastCallEndTime;

    private Long totalMinutesUsed;

    private String SMSSent;
    
    private Long SMSReferenceNo;
    
    private Integer NoOfAttempts;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getFirstCompletionDate() {
        return firstCompletionDate;
    }

    public void setFirstCompletionDate(Date firstCompletionDate) {
        this.firstCompletionDate = firstCompletionDate;
    }

    public Date getLastCallEndDate() {
        return lastCallEndDate;
    }

    public void setLastCallEndDate(Date lastCallEndDate) {
        this.lastCallEndDate = lastCallEndDate;
    }

    public String getLastCallEndTime() {
        return lastCallEndTime;
    }

    public void setLastCallEndTime(String lastCallEndTime) {
        this.lastCallEndTime = lastCallEndTime;
    }

    public Long getTotalMinutesUsed() {
        return totalMinutesUsed;
    }

    public void setTotalMinutesUsed(Long totalMinutesUsed) {
        this.totalMinutesUsed = totalMinutesUsed;
    }

    public String getSMSSent() {
        return SMSSent;
    }

    public void setSMSSent(String SMSSent) {
        this.SMSSent = SMSSent;
    }

    public Long getSMSReferenceNo() {
        return SMSReferenceNo;
    }

    public void setSMSReferenceNo(Long SMSReferenceNo) {
        this.SMSReferenceNo = SMSReferenceNo;
    }

    public Integer getNoOfAttempts() {
        return NoOfAttempts;
    }

    public void setNoOfAttempts(Integer noOfAttempts) {
        NoOfAttempts = noOfAttempts;
    }
}
