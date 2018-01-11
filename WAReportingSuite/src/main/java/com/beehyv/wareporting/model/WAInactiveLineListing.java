package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WA_inactive_line_listing")
public class WAInactiveLineListing {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="mobile_no",columnDefinition = "BIGINT")
    private Long mobileNumber;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name="state_name")
    private String stateName;

    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtId;

    @Column(name="district_name")
    private String districtName;

    @Column(name="block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name="block_name")
    private String blockName;

    @Column(name="panchayat_id", columnDefinition = "BIGINT(20)")
    private Integer panchayatId;

    @Column(name="panchayat_name")
    private String panchayatName;

    @Column(name="swachchagrahi_name")
    private String swachchagrahiName;

    @Column(name="swachchagrahi_id")
    private Long swcId;

    @Column(name="swachchagrahi_creation_date")
    private Date swachchagrahiCreationDate;

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

    public Long getSwcId() {
        return swcId;
    }

    public void setSwcId(Long swcId) {
        this.swcId = swcId;
    }

    public Date getSwachchagrahiCreationDate() {
        return swachchagrahiCreationDate;
    }

    public void setSwachchagrahiCreationDate(Date swachchagrahiCreationDate) {
        this.swachchagrahiCreationDate = swachchagrahiCreationDate;
    }
}
