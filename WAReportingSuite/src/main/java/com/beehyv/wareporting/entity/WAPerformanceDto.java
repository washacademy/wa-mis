package com.beehyv.wareporting.entity;


/**
 * Created by beehyv on 21/9/17.
 */

public class WAPerformanceDto {

    private Integer id;
    private String locationType;
    private String locationName;
    private Long locationId;
    private Integer swachchagrahisStartedCourse;
    private Long swachchagrahisPursuingCourse;
    private Long swachchagrahisNotPursuingCourse;
    private Integer swachchagrahisCompletedCourse;
    private Integer swachchagrahisFailedCourse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getSwachchagrahisStartedCourse() {
        return swachchagrahisStartedCourse;
    }

    public void setSwachchagrahisStartedCourse(Integer swachchagrahisStartedCourse) {
        this.swachchagrahisStartedCourse = swachchagrahisStartedCourse;
    }

    public Long getSwachchagrahisPursuingCourse() {
        return swachchagrahisPursuingCourse;
    }

    public void setSwachchagrahisPursuingCourse(Long swachchagrahisPursuingCourse) {
        this.swachchagrahisPursuingCourse = swachchagrahisPursuingCourse;
    }

    public Long getSwachchagrahisNotPursuingCourse() {
        return swachchagrahisNotPursuingCourse;
    }

    public void setSwachchagrahisNotPursuingCourse(Long swachchagrahisNotPursuingCourse) {
        this.swachchagrahisNotPursuingCourse = swachchagrahisNotPursuingCourse;
    }

    public Integer getSwachchagrahisCompletedCourse() {
        return swachchagrahisCompletedCourse;
    }

    public void setSwachchagrahisCompletedCourse(Integer swachchagrahisCompletedCourse) {
        this.swachchagrahisCompletedCourse = swachchagrahisCompletedCourse;
    }

    public Integer getSwachchagrahisFailedCourse() {
        return swachchagrahisFailedCourse;
    }

    public void setSwachchagrahisFailedCourse(Integer swachchagrahisFailedCourse) {
        this.swachchagrahisFailedCourse = swachchagrahisFailedCourse;
    }
}
