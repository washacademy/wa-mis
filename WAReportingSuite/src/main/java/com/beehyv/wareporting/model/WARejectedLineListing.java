package com.beehyv.wareporting.model;

import javax.persistence.*;

@Entity
@Table(name="WA_rejected_line_listing")
public class WARejectedLineListing {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Long Id;

    @Column(name="mobile_no", columnDefinition = "BIGINT(20)")
    private Long mobileNumber;

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

    @Column(name="swachchagrahi_name", columnDefinition = "VARCHAR(255)")
    private String swachchagrahiName;

    @Column(name="swachchagrahi_id", columnDefinition = "BIGINT(20)")
    private String swcId;

    @Column(name="status", columnDefinition = "VARCHAR(255)")
    private String status;

    @Column(name="reason_for_rejection", columnDefinition = "VARCHAR(255)")
    private String reasonForRejection;

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

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
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

    public String getSwachchagrahiName() {
        return swachchagrahiName;
    }

    public void setSwachchagrahiName(String swachchagrahiName) {
        this.swachchagrahiName = swachchagrahiName;
    }

    public String getSwcId() {
        return swcId;
    }

    public void setSwcId(String swcId) {
        this.swcId = swcId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }
}
