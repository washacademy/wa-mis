package com.beehyv.wareporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by beehyv on 3/5/17.
 */
@Entity
@Table(name="front_line_worker")
public class FrontLineWorkers {

    @Id
    @Column(name="flw_id", columnDefinition = "BIGINT(20)")
    private Long flwId;

    @Column(name="flw_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="flw_msisdn", columnDefinition = "BIGINT(10)")
    private Long mobileNumber;

    @Column(name="external_flw_id", columnDefinition = "BIGINT(20)")
    private String externalFlwId;

    @Column(name="flw_designation", columnDefinition = "VARCHAR(255)")
    private String designation;

    @Column(name="language", columnDefinition = "VARCHAR(255)")
    private String language;

    @Column(name="flw_status", columnDefinition = "VARCHAR(255)")
    private String status;

    @Column(name="job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer state;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer district;

    @Column(name="block_id", columnDefinition = "INT(11)")
    private Integer block;

    @Column(name="panchayat_id", columnDefinition = "INT(11)")
    private Integer panchayat;

    @Column(name="creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name="last_modified", columnDefinition = "DATE")
    private Date lastModifiedDate;

    @Column(name="course_first_completion_date", columnDefinition = "DATE")
    private Date firstCompletionDate;

    @Column(name="course_start_date", columnDefinition = "DATE")
    private Date courseStartDate;

    public Date getFirstCompletionDate() {
        return firstCompletionDate;
    }

    public void setFirstCompletionDate(Date firstCompletionDate) {
        this.firstCompletionDate = firstCompletionDate;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getExternalFlwId() {
        return externalFlwId;
    }

    public void setExternalFlwId(String externalFlwId) {
        this.externalFlwId = externalFlwId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(Integer panchayat) {
        this.panchayat = panchayat;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
