package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WA_completion_line_listing")
public class WACompletionLineListing {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="mobile_no",columnDefinition = "BIGINT")
    private Long mobileNumber;

    @Column(name="state_name",columnDefinition = "VARCHAR(45)")
    private String state;

    @Column(name="district_name")
    private String district;

    @Column(name="block_name")
    private String block;

    @Column(name="panchayat_name")
    private String panchayat;

    @Column(name="swachchagrahi_name")
    private String swachchagrahiName;

    @Column(name="swachchagrahi_id")
    private Long swachchagrahiId;

    @Column(name="swachchagrahi_creation_date")
    private Date swachchagrahiCreationDate;

    @Column(name="course_start_date")
    private Date courseStartDate;

    @Column(name="first_completion_date")
    private Date firstCompletionDate;

    @Column(name="SMS_sent_notification")
    private String SMSSentNotification;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getSwachchagrahiName() {
        return swachchagrahiName;
    }

    public void setSwachchagrahiName(String swachchagrahiName) {
        this.swachchagrahiName = swachchagrahiName;
    }

    public Long getSwachchagrahiId() {
        return swachchagrahiId;
    }

    public void setSwachchagrahiId(Long swachchagrahiId) {
        this.swachchagrahiId = swachchagrahiId;
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

    public String getSMSSentNotification() {
        return SMSSentNotification;
    }

    public void setSMSSentNotification(String SMSSentNotification) {
        this.SMSSentNotification = SMSSentNotification;
    }
}
