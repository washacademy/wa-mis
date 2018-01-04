package com.beehyv.wareporting.entity;

import java.util.Date;

/**
 * Created by beehyv on 21/9/17.
 */

public class WAPerformanceDto {

    int id;
    String locationType;
    String locationName;
    Long locationId;
    Integer swachchagrahisStarted;
    Long swachchagrahisNotAccessed;
    Long swachchagrahisAccessed;
    Integer swachchagrahisCompleted;
    Integer swachchagrahisFailed;

//    Integer swachchagrahisRejected;
//    Integer recordsReceived;
//    Integer registeredNotCompletedStart;
//    Integer registeredNotCompletedend;


    public Long getSwachchagrahisNotAccessed() {
        return swachchagrahisNotAccessed;
    }

    public void setSwachchagrahisNotAccessed(Long swachchagrahisNotAccessed) {
        this.swachchagrahisNotAccessed = swachchagrahisNotAccessed;
    }

    public Long getSwachchagrahisAccessed() {
        return swachchagrahisAccessed;
    }

    public void setSwachchagrahisAccessed(Long swachchagrahisAccessed) {
        this.swachchagrahisAccessed = swachchagrahisAccessed;
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

    public Integer getSwachchagrahisStarted() {
        return swachchagrahisStarted;
    }

    public void setSwachchagrahisStarted(Integer swachchagrahisStarted) {
        this.swachchagrahisStarted = swachchagrahisStarted;
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
