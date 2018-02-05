package com.beehyv.wareporting.entity;

import java.util.Date;

/**
 * Created by beehyv on 19/9/17.
 */
public class AggregateCumulativeWADto {

    private int id;
    private String locationType;
    private String locationName;
    private Long locationId;
    private Integer swachchagrahisRegistered;
    private Integer swachchagrahisStarted;
    private Integer swachchagrahisNotStarted;
    private Integer swachchagrahisCompleted;
    private Integer swachchagrahisFailed;
    private Integer swachchagrahisRejected;
    private Integer recordsReceived;
    private float notStartedPercentage;
    private float completedPercentage;
    private float failedPercentage;

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

    public float getNotStartedPercentage() {
        return notStartedPercentage;
    }

    public void setNotStartedPercentage(float notStartedPercentage) {
        this.notStartedPercentage = notStartedPercentage;
    }

    public float getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(float completedPercentage) {
        this.completedPercentage = completedPercentage;
    }

    public float getFailedPercentage() {
        return failedPercentage;
    }

    public void setFailedPercentage(float failedPercentage) {
        this.failedPercentage = failedPercentage;
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
