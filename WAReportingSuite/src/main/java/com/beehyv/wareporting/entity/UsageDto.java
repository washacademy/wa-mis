package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 9/10/17.
 */
public class UsageDto {

    private String locationType;
    private String locationName;
    private Long locationId;
    private Long beneficiariesCalled;
    private Long calls_75_100;
    private Long calls_50_75;
    private Long calls_25_50;
    private Long calls_1_25;
    private Long answeredCall;
    private Long calledInbox;

    public Long getCalledInbox() {
        return calledInbox;
    }

    public void setCalledInbox(Long calledInbox) {
        this.calledInbox = calledInbox;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getBeneficiariesCalled() {
        return beneficiariesCalled;
    }

    public void setBeneficiariesCalled(Long beneficiariesCalled) {
        this.beneficiariesCalled = beneficiariesCalled;
    }

    public Long getCalls_75_100() {
        return calls_75_100;
    }

    public void setCalls_75_100(Long calls_75_100) {
        this.calls_75_100 = calls_75_100;
    }

    public Long getCalls_50_75() {
        return calls_50_75;
    }

    public void setCalls_50_75(Long calls_50_75) {
        this.calls_50_75 = calls_50_75;
    }

    public Long getCalls_25_50() {
        return calls_25_50;
    }

    public void setCalls_25_50(Long calls_25_50) {
        this.calls_25_50 = calls_25_50;
    }

    public Long getCalls_1_25() {
        return calls_1_25;
    }

    public void setCalls_1_25(Long calls_1_25) {
        this.calls_1_25 = calls_1_25;
    }

    public Long getAnsweredCall() {
        return answeredCall;
    }

    public void setAnsweredCall(Long answeredCall) {
        this.answeredCall = answeredCall;
    }
}
