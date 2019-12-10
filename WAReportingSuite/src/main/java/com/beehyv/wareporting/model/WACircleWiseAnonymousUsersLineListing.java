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

    @Column(name="mobile_number",columnDefinition = "BIGINT(20)")
    private Long mobileNumber;

    @Column(name="operator", columnDefinition = "VARCHAR(45)")
    private String operator;

    @Column(name="circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

    @Column(name="course_start_date", columnDefinition = "DATETIME")
    private Date courseStartDate;

    @Column(name="first_completion_date", columnDefinition = "DATETIME")
    private Date firstCompletionDate;

    @Column(name="last_call_end_date", columnDefinition = "DATETIME")
    private Date lastCallEndDate;

    @Column(name="last_call_end_time", columnDefinition = "TIMESTAMP")
    private Date lastCallEndTime;

    @Column(name="total_minutes_used", columnDefinition = "INT(11)")
    private Long totalMinutesUsed;

    @Column(name="SMS_sent", columnDefinition = "BIT(1)")
    private Boolean SMSSent;

    @Column(name="SMS_reference_no", columnDefinition = "VARCHAR(45)")
    private String SMSReferenceNo;

    @Column(name="no_of_attempts", columnDefinition = "SMALLINT(6)")
    private Integer NoOfAttempts;

    @Column(name = "courseId", columnDefinition = "TINYINT(11)")
    private Integer courseId;

    public Integer getCourseId() {  return courseId;  }

    public void setCourseId(Integer courseId) {  this.courseId = courseId;  }

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

    public Date getLastCallEndTime() {
        return lastCallEndTime;
    }

    public void setLastCallEndTime(Date lastCallEndTime) {
        this.lastCallEndTime = lastCallEndTime;
    }

    public Long getTotalMinutesUsed() {
        return totalMinutesUsed;
    }

    public void setTotalMinutesUsed(Long totalMinutesUsed) {
        this.totalMinutesUsed = totalMinutesUsed;
    }

    public Boolean getSMSSent() {
        return SMSSent;
    }

    public void setSMSSent(Boolean SMSSent) {
        this.SMSSent = SMSSent;
    }

    public String getSMSReferenceNo() {
        return SMSReferenceNo;
    }

    public void setSMSReferenceNo(String SMSReferenceNo) {
        this.SMSReferenceNo = SMSReferenceNo;
    }

    public Integer getNoOfAttempts() {
        return NoOfAttempts;
    }

    public void setNoOfAttempts(Integer noOfAttempts) {
        NoOfAttempts = noOfAttempts;
    }
}
