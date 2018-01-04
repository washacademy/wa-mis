package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="WA_aggregate_cumulative_summary")
public class WACumulativeSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name = "serialNumber", columnDefinition = "BIGINT")
    private Long serialNumber;

    @Column(name = "state")
    private String state;

    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="loc_type")
    private String locationType;

    @Column(name = "swachchagrahis_registered", columnDefinition = "INT(11)")
    private Integer swachchagrahisRegistered;

    @Column(name = "swachchagrahis_started_course", columnDefinition = "INT(11)")
    private Integer swachchagrahisStarted; //sum of swachchagrahisStarted from daily

    @Column(name = "swachchagrahis_not_started_course", columnDefinition = "INT(11)")
    private Integer swachchagrahisNotStarted;

    @Column(name = "swachchagrahis_completed_successfully", columnDefinition = "INT(11)")
    private Integer swachchagrahisCompleted; //sum of swachchagrahisCompleted from daily

    @Column(name = "swachchagrahis_failed_course", columnDefinition = "INT(11)")
    private Integer swachchagrahisFailed; //sum of swachchagrahisFailed from daily

    @Column(name = "swachchagrahis_rejected", columnDefinition = "INT(11)")
    private Integer swachchagrahisRejected;

    public WACumulativeSummary(Integer id, Long serialNumber, String state, Long locationId, String locationType, Integer swachchagrahisRegistered, Integer swachchagrahisStarted, Integer swachchagrahisNotStarted, Integer swachchagrahisCompleted, Integer swachchagrahisFailed, Integer swachchagrahisRejected) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.state = state;
        this.locationId = locationId;
        this.locationType = locationType;
        this.swachchagrahisRegistered = swachchagrahisRegistered;
        this.swachchagrahisStarted = swachchagrahisStarted;
        this.swachchagrahisNotStarted = swachchagrahisNotStarted;
        this.swachchagrahisCompleted = swachchagrahisCompleted;
        this.swachchagrahisFailed = swachchagrahisFailed;
        this.swachchagrahisRejected = swachchagrahisRejected;
    }

    public WACumulativeSummary(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
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

    public Integer getSwachchagrahisRejected() {
        return swachchagrahisRejected;
    }

    public void setSwachchagrahisRejected(Integer swachchagrahisRejected) {
        this.swachchagrahisRejected = swachchagrahisRejected;
    }
}

