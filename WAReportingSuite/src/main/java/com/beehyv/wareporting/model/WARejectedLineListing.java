package com.beehyv.wareporting.model;

import javax.persistence.*;

@Entity
@Table(name="WA_rejected_line_listing")
public class WARejectedLineListing {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="mobile_no")
    private String mobileNumber;

    @Column(name="state_name")
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
    private String swachchagrahiId;

    @Column(name="status")
    private String status;

    @Column(name="reason_for_rejection")
    private String reasonForRejection;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
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

    public String getSwachchagrahiId() {
        return swachchagrahiId;
    }

    public void setSwachchagrahiId(String swachchagrahiId) {
        this.swachchagrahiId = swachchagrahiId;
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
