package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 31/5/17.
 */
@Entity
@Table(name = "ma_course_completion_first")
public class MACourseFirstCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "flw_id", columnDefinition = "BIGINT(20)")
    private Long flwId;

    @Column(name = "flw_msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name = "state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name = "district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name = "taluka_id", columnDefinition = "SMALLINT(6)")
    private Integer talukaId;

    @Column(name = "village_id", columnDefinition = "BIGINT(20)")
    private Integer villageId;

    @Column(name = "block_id", columnDefinition = "BIGINT(20)")
    private Integer blockId;

    @Column(name = "healthfacility_id", columnDefinition = "BIGINT(20)")
    private Integer healthFacilityId;

    @Column(name = "healthsubfacility_id", columnDefinition = "BIGINT(20)")
    private Integer healthSubFacilityId;

    @Column(name = "flw_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name = "external_flw_id", columnDefinition = "VARCHAR(255)")
    private Long externalFlwId;

    @Column(name = "job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name = "first_completion", columnDefinition = "DATE")
    private Date firstCompletionDate;

    @Column(name = "sent_notification")
    private Boolean sentNotification;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
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

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getHealthFacilityId() {
        return healthFacilityId;
    }

    public void setHealthFacilityId(Integer healthFacilityId) {
        this.healthFacilityId = healthFacilityId;
    }

    public Integer getHealthSubFacilityId() {
        return healthSubFacilityId;
    }

    public void setHealthSubFacilityId(Integer healthSubFacilityId) {
        this.healthSubFacilityId = healthSubFacilityId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getExternalFlwId() {
        return externalFlwId;
    }

    public void setExternalFlwId(Long externalFlwId) {
        this.externalFlwId = externalFlwId;
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
