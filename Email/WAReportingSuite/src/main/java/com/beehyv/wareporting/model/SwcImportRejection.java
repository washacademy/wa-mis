package com.beehyv.wareporting.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by beehyv on 3/8/17.
 */

@Entity
@Table(name="swc_import_rejection")
public class SwcImportRejection {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT(20)")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name = "mobile_number", columnDefinition = "BIGINT(20)")
    private Long mobileNumber;

    @Column(name = "gender", columnDefinition = "VARCHAR(45)")
    private String gender;

    @Column(name = "age", columnDefinition = "TINYINT(4)")
    private Integer age;

    @Column(name = "qualification", columnDefinition = "VARCHAR(255)")
    private String qualification;

    @Column(name = "swc_id", columnDefinition = "BIGINT(20)")
    private String swcId;

    @Column(name = "swc_designation", columnDefinition = "VARCHAR(255)")
    private String swcDesignation;

    @Column(name = "language", columnDefinition = "VARCHAR(45)")
    private String language;

    @Column(name = "course_status", columnDefinition = "VARCHAR(255)")
    private String courseStatus;

    @Column(name = "job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name="state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name="state_name", columnDefinition = "VARCHAR(45)")
    private String stateName;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name="district_name", columnDefinition = "VARCHAR(45)")
    private String districtName;

    @Column(name="block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name="block_name", columnDefinition = "VARCHAR(45)")
    private String blockName;

    @Column(name="panchayat_id", columnDefinition = "BIGINT(20)")
    private Integer panchayatId;

    @Column(name="panchayat_name", columnDefinition = "VARCHAR(45)")
    private String panchayatName;

    @Column(name = "creation_date", columnDefinition = "DATETIME")
    private Date creationDate;

    @Column(name = "accepted", columnDefinition = "BIT(1)")
    private Boolean accepted;

    @Column(name = "rejection_reason", columnDefinition = "VARCHAR(255)")
    private String rejectionReason;

    @Column(name = "modification_date", columnDefinition = "DATETIME")
    private Date modificationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSwcId() {
        return swcId;
    }

    public void setSwcId(String swcId) {
        this.swcId = swcId;
    }

    public String getSwDesignation() {
        return swcDesignation;
    }

    public void setSwDesignation(String swcDesignation) {
        this.swcDesignation = swcDesignation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSwStatus() {
        return courseStatus;
    }

    public void setSwStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
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

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

