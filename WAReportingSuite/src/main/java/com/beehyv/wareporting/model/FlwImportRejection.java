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
@Table(name="flw_import_rejection")
public class FlwImportRejection {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "_block_id")
    private Integer BlockId;

    @Column(name = "_block_name")
    private String BlockName;

    @Column(name = "phc_id")
    private Long phcId;

    @Column(name = "phc_name")
    private String phcName;

    @Column(name = "panchayat_id")
    private Long panchayatId;

    @Column(name = "panchayat_name")
    private String panchayatName;

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

    public Integer getBlockId() {
        return BlockId;
    }

    public void setBlockId(Integer BlockId) {
        this.BlockId = BlockId;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String BlockName) {
        this.BlockName = BlockName;
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

    public Long getpanchayatId() {
        return panchayatId;
    }

    public void setpanchayatId(Long panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getpanchayatName() {
        return panchayatName;
    }

    public void setpanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
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

