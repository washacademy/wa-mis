package com.beehyv.wareporting.entity;

import java.util.Date;

/**
 * Created by beehyv on 19/9/17.
 */
public class AggregateCumulativeWADto {
    int id;
    String locationType;
    String locationName;
    Long locationId;
    Integer swachchagrahisRegistered;
    Integer swachchagrahisStarted;
    Integer swachchagrahisNotStarted;
    Integer swachchagrahisCompleted;
    Integer swachchagrahisFailed;
    Integer swachchagrahisRejected;
    Integer recordsReceived;
    float notStartedpercentage;
    float completedPercentage;
    float failedpercentage;

    public Integer getRecordsReceived() {
        return recordsReceived;
    }

    public void setRecordsReceived(Integer recordsReceived) {
        this.recordsReceived = recordsReceived;
    }

    public Integer getSwachchagrahisRejected() {
        return swachchagrahisRejected;
    }

    public void setSwachchagrahisRejected(Integer swachchagrahisRejected) {
        this.swachchagrahisRejected = swachchagrahisRejected;
    }

    public float getNotStartedpercentage() {
        return notStartedpercentage;
    }

    public void setNotStartedpercentage(float notStartedpercentage) {
        this.notStartedpercentage = notStartedpercentage;
    }

    public float getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(float completedPercentage) {
        this.completedPercentage = completedPercentage;
    }

    public float getFailedpercentage() {
        return failedpercentage;
    }

    public void setFailedpercentage(float failedpercentage) {
        this.failedpercentage = failedpercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getSwachchagrahisRegistered() {
        return swachchagrahisRegistered;
    }

    public void setSwachchagrahisRegistered(Integer swachchagrahisRegistered) {
        this.swachchagrahisRegistered = swachchagrahisRegistered;
    }

    public Integer getSwachchagrahisStarted() {
        return swachchagrahisStarted;
    }

    public void setSwachchagrahisStarted(Integer swachchagrahisStarted) {
        this.swachchagrahisStarted = swachchagrahisStarted;
    }

    public Integer getSwachchagrahisNotStarted() {
        return swachchagrahisNotStarted;
    }

    public void setSwachchagrahisNotStarted(Integer swachchagrahisNotStarted) {
        this.swachchagrahisNotStarted = swachchagrahisNotStarted;
    }

    public Integer getSwachchagrahisCompleted() {
        return swachchagrahisCompleted;
    }

    public void setSwachchagrahisCompleted(Integer swachchagrahisCompleted) {
        this.swachchagrahisCompleted = swachchagrahisCompleted;
    }

    public Integer getSwachchagrahisFailed() {
        return swachchagrahisFailed;
    }

    public void setSwachchagrahisFailed(Integer swachchagrahisFailed) {
        this.swachchagrahisFailed = swachchagrahisFailed;
    }
}
