package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="dim_state")
public class State {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name="state_name")
    private String stateName;


    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="serviceType",columnDefinition = "VARCHAR(10)")
    private String serviceType;

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
}
