package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 21/9/17.
 */
public class WASubscriberDto {

    private int id;
    private String locationType;
    private String locationName;
    private Long locationId;
    private Integer swachchagrahisRegistered;
    private Integer swachchagrahisStarted;
    private Integer swachchagrahisNotStarted;
    private Integer swachchagrahisCompleted;
    private Integer swachchagrahisFailed;
    private Integer recordsRejected;
    private Integer recordsReceived;
    private Integer registeredNotCompletedStart;
    private Integer registeredNotCompletedEnd;
    private Integer successfullyFirstCompleted;

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

    public Integer getRecordsRejected() {
        return recordsRejected;
    }

    public void setRecordsRejected(Integer recordsRejected) {
        this.recordsRejected = recordsRejected;
    }

    public Integer getRecordsReceived() {
        return recordsReceived;
    }

    public void setRecordsReceived(Integer recordsReceived) {
        this.recordsReceived = recordsReceived;
    }

    public Integer getRegisteredNotCompletedStart() {
        return registeredNotCompletedStart;
    }

    public void setRegisteredNotCompletedStart(Integer registeredNotCompletedStart) {
        this.registeredNotCompletedStart = registeredNotCompletedStart;
    }

    public Integer getRegisteredNotCompletedEnd() {
        return registeredNotCompletedEnd;
    }

    public void setRegisteredNotCompletedEnd(Integer registeredNotCompletedEnd) {
        this.registeredNotCompletedEnd = registeredNotCompletedEnd;
    }

    public Integer getSuccessfullyFirstCompleted() {
        return successfullyFirstCompleted;
    }

    public void setSuccessfullyFirstCompleted(Integer successfullyFirstCompleted) {
        this.successfullyFirstCompleted = successfullyFirstCompleted;
    }
}


