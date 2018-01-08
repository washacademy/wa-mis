package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 31/5/17.
 */

@Entity
@Table(name="WA_course_completion_first")
public class WACourseFirstCompletion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;

    @Column(name="swc_id", columnDefinition = "BIGINT(20)")
    private Long swcId;

    @Column(name="swc_mobileNumber", columnDefinition = "BIGINT(20)")
    private Long mobileNumber;

    @Column(name="state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name="block_id", columnDefinition = "BIGINT(20)")
    private Integer blockId;

    @Column(name="panchayat_id", columnDefinition = "BIGINT(20)")
    private Integer panchayatId;

    @Column(name="swc_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name="creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name="first_completion", columnDefinition = "DATE")
    private Date firstCompletionDate;

    @Column(name="sent_notification")
    private Boolean sentNotification;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Long getSwcId() {
        return swcId;
    }

    public void setSwcId(Long swcId) {
        this.swcId = swcId;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getFirstCompletionDate() {
        return firstCompletionDate;
    }

    public void setFirstCompletionDate(Date firstCompletionDate) {
        this.firstCompletionDate = firstCompletionDate;
    }

    public Boolean getSentNotification() {
        return sentNotification;
    }

    public void setSentNotification(Boolean sentNotification) {
        this.sentNotification = sentNotification;
    }
}
