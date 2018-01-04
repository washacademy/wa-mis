package com.beehyv.wareporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by beehyv on 12/7/17.
 */
@Entity
@Table(name = "child_import_rejection")
public class ChildImportRejection {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "phc_id")
    private Long phcId;

    @Column(name = "phc_name")
    private String phcName;

    @Column(name = "subcentre_id")
    private Long subcentreId;

    @Column(name = "taluka_id")
    private String talukaId;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "health_block_id")
    private Integer healthBlockId;

    @Column(name = "health_block_name")
    private String healthBlockName;

    @Column(name = "subcentre_name")
    private String subcentreName;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "id_no")
    private String idNo;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "modification_date")
    private Date modificationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getPhcId() {
        return phcId;
    }

    public void setPhcId(Long phcId) {
        this.phcId = phcId;
    }

    public String getPhcName() {
        return phcName;
    }

    public void setPhcName(String phcName) {
        this.phcName = phcName;
    }

    public Long getSubcentreId() {
        return subcentreId;
    }

    public void setSubcentreId(Long subcentreId) {
        this.subcentreId = subcentreId;
    }

    public String getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(String talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Integer getHealthBlockId() {
        return healthBlockId;
    }

    public void setHealthBlockId(Integer healthBlockId) {
        this.healthBlockId = healthBlockId;
    }

    public String getHealthBlockName() {
        return healthBlockName;
    }

    public void setHealthBlockName(String healthBlockName) {
        this.healthBlockName = healthBlockName;
    }

    public String getSubcentreName() {
        return subcentreName;
    }

    public void setSubcentreName(String subcentreName) {
        this.subcentreName = subcentreName;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
