package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WA_circle_wise_anonymous_line_listing")
public class WACircleWiseAnonymousUsersLineListing {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="mobile_no",columnDefinition = "BIGINT")
    private Long mobileNumber;

    @Column(name="operator")
    private String operator;

    @Column(name="circle")
    private String circle;

    @Column(name="course_start_date")
    private Date courseStartDate;

    @Column(name="first_completion_date")
    private Date firstCompletionDate;

    @Column(name="last_call_end_date")
    private Date lastCallEndDate;

    @Column(name="last_call_end_time")
    private String lastCallEndTime;

    @Column(name="total_minutes_used")
    private Long totalMinutesUsed;

    @Column(name="SMS_sent")
    private String SMSSent;

    @Column(name="SMS_reference_no")
    private Long SMSReferenceNo;

    @Column(name="no_of_attempts")
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

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
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
