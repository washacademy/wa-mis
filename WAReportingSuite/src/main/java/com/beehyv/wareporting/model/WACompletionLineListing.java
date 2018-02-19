package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WA_completion_line_listing")
public class WACompletionLineListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long Id;

    @Column(name = "mobile_number", columnDefinition = "BIGINT(20)")
    private Long mobileNumber;

    @Column(name = "state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name = "state_name", columnDefinition = "VARCHAR(45)")
    private String stateName;

    @Column(name = "district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name = "district_name", columnDefinition = "VARCHAR(45)")
    private String districtName;

    @Column(name = "block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name = "block_name", columnDefinition = "VARCHAR(45)")
    private String blockName;

    @Column(name = "panchayat_id", columnDefinition = "BIGINT(20)")
    private Integer panchayatId;

    @Column(name = "panchayat_name", columnDefinition = "VARCHAR(45)")
    private String panchayatName;

    @Column(name = "swachchagrahi_name", columnDefinition = "VARCHAR(255)")
    private String swachchagrahiName;

    @Column(name = "swachchagrahi_id", columnDefinition = "BIGINT(20)")
    private Long swcId;

    @Column(name = "swachchagrahi_creation_date", columnDefinition = "DATETIME")
    private Date swachchagrahiCreationDate;

    @Column(name = "course_start_date", columnDefinition = "DATETIME")
    private Date courseStartDate;

    @Column(name = "first_completion_date", columnDefinition = "DATETIME")
    private Date firstCompletionDate;

    @Column(name = "SMS_sent_notification", columnDefinition = "BIT(1)")
    private Boolean SMSSentNotification;

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

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getFirstCompletionDate() {
        return firstCompletionDate;
    }

    public void setFirstCompletionDate(Date firstCompletionDate) {
        this.firstCompletionDate = firstCompletionDate;
    }

    public Boolean getSMSSentNotification() {
        return SMSSentNotification;
    }

    public void setSMSSentNotification(Boolean SMSSentNotification) {
        this.SMSSentNotification = SMSSentNotification;
    }
}
