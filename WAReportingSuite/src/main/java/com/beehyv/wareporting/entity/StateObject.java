package com.beehyv.wareporting.entity;

import com.beehyv.wareporting.model.State;

import java.util.Date;

/**
 * Created by beehyv on 7/6/17.
 */
public class StateObject {

    private Integer stateId;

    private String stateName;

    private Long locationId;

    private Date lastModified;

    private String serviceType;

    private Date serviceStartDate;

    public StateObject(State state){
        this.stateId = state.getStateId();
        this.stateName = state.getStateName();
        this.locationId = state.getLocationId();
        this.lastModified = state.getLastModified();
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }
}
