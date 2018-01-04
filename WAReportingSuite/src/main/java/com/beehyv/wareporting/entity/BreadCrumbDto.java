package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 29/9/17.
 */
public class BreadCrumbDto {

    String locationType;
    String locationName;
    Integer locationId;

    public BreadCrumbDto(String locationType,String locationName, Integer locationId) {
        this.locationType = locationType;
        this.locationName = locationName;
        this.locationId = locationId;
    }
    public BreadCrumbDto(){

    }
    public String getLocationType() {
        return locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

}
