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
@Table(name = "flw_import_rejection")
public class FlwImportRejection {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "taluka_id")
    private String talukaId;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "health_block_id")
    private Integer healthBlockId;

    @Column(name = "health_block_name")
    private String healthBlockName;

    @Column(name = "phc_id")
    private Long phcId;

    @Column(name = "phc_name")
    private String phcName;

    @Column(name = "subcentre_id")
    private Long subcentreId;

    @Column(name = "subcentre_name")
    private String subcentreName;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "flw_id")
    private Long flwId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "gf_name")
    private String gfName;

    @Column(name = "gf_status")
    private String gfStatus;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "rejection_reason")
    private String rejectionReason;

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

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getGfName() {
        return gfName;
    }

    public void setGfName(String gfName) {
        this.gfName = gfName;
    }

    public String getGfStatus() {
        return gfStatus;
    }

    public void setGfStatus(String gfStatus) {
        this.gfStatus = gfStatus;
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

