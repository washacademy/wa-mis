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
@Table(name="swachchagrahi")
public class Swachchagrahi {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT(20)")
    private Long Id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name = "mobile_number", columnDefinition = "BIGINT(10)")
    private Long mobileNumber;

    @Column(name = "gender", columnDefinition = "VARCHAR(45)")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "qualification", columnDefinition = "VARCHAR(255)")
    private String qualification;

    @Column(name = "swc_id", columnDefinition = "BIGINT(20)")
    private String swcId;

    @Column(name = "swc_designation", columnDefinition = "VARCHAR(255)")
    private String swcDesignation;

    @Column(name = "language", columnDefinition = "VARCHAR(255)")
    private String language;

    @Column(name = "swc_status", columnDefinition = "VARCHAR(255)")
    private String swcStatus;

    @Column(name = "job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name = "state_id", columnDefinition = "TINYINT")
    private Integer state;

    @Column(name = "district_id", columnDefinition = "SMALLINT(6)")
    private Integer district;

    @Column(name = "block_id", columnDefinition = "INT(11)")
    private Integer block;

    @Column(name = "panchayat_id", columnDefinition = "BIGINT(20)")
    private Integer panchayat;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name = "last_modified", columnDefinition = "DATE")
    private Date lastModifiedDate;

    @Column(name = "course_first_completion_date", columnDefinition = "DATE")
    private Date firstCompletionDate;

    @Column(name = "course_start_date", columnDefinition = "DATE")
    private Date courseStartDate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
        return swcStatus;
    }

    public void setSwStatus(String swcStatus) {
        this.swcStatus = swcStatus;
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
}