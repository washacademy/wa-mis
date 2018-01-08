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

    @Column(name = "swcachchagrahis_registered", columnDefinition = "INT(11)")
    private Integer swcachchagrahisRegistered;

    @Column(name = "swcachchagrahis_started_course", columnDefinition = "INT(11)")
    private Integer swcachchagrahisStarted; //sum of swcachchagrahisStarted from daily

    @Column(name = "swcachchagrahis_not_started_course", columnDefinition = "INT(11)")
    private Integer swcachchagrahisNotStarted;

    @Column(name = "swcachchagrahis_completed_successfully", columnDefinition = "INT(11)")
    private Integer swcachchagrahisCompleted; //sum of swcachchagrahisCompleted from daily

    @Column(name = "swcachchagrahis_failed_course", columnDefinition = "INT(11)")
    private Integer swcachchagrahisFailed; //sum of swcachchagrahisFailed from daily

    @Column(name = "swcachchagrahis_rejected", columnDefinition = "INT(11)")
    private Integer swcachchagrahisRejected;

    public WACumulativeSummary(Integer id, Long serialNumber, String state, Long locationId, String locationType, Integer swcachchagrahisRegistered, Integer swcachchagrahisStarted, Integer swcachchagrahisNotStarted, Integer swcachchagrahisCompleted, Integer swcachchagrahisFailed, Integer swcachchagrahisRejected) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.state = state;
        this.locationId = locationId;
        this.locationType = locationType;
        this.swcachchagrahisRegistered = swcachchagrahisRegistered;
        this.swcachchagrahisStarted = swcachchagrahisStarted;
        this.swcachchagrahisNotStarted = swcachchagrahisNotStarted;
        this.swcachchagrahisCompleted = swcachchagrahisCompleted;
        this.swcachchagrahisFailed = swcachchagrahisFailed;
        this.swcachchagrahisRejected = swcachchagrahisRejected;
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
        return swcachchagrahisRegistered;
    }

    public void setSwachchagrahisRegistered(Integer swcachchagrahisRegistered) {
        this.swcachchagrahisRegistered = swcachchagrahisRegistered;
    }

    public Integer getSwachchagrahisStarted() {
        return swcachchagrahisStarted;
    }

    public void setSwachchagrahisStarted(Integer swcachchagrahisStarted) {
        this.swcachchagrahisStarted = swcachchagrahisStarted;
    }

    public Integer getSwachchagrahisNotStarted() {
        return swcachchagrahisNotStarted;
    }

    public void setSwachchagrahisNotStarted(Integer swcachchagrahisNotStarted) {
        this.swcachchagrahisNotStarted = swcachchagrahisNotStarted;
    }

    public Integer getSwachchagrahisCompleted() {
        return swcachchagrahisCompleted;
    }

    public void setSwachchagrahisCompleted(Integer swcachchagrahisCompleted) {
        this.swcachchagrahisCompleted = swcachchagrahisCompleted;
    }

    public Integer getSwachchagrahisFailed() {
        return swcachchagrahisFailed;
    }

    public void setSwachchagrahisFailed(Integer swcachchagrahisFailed) {
        this.swcachchagrahisFailed = swcachchagrahisFailed;
    }

    public Integer getSwachchagrahisRejected() {
        return swcachchagrahisRejected;
    }

    public void setSwachchagrahisRejected(Integer swcachchagrahisRejected) {
        this.swcachchagrahisRejected = swcachchagrahisRejected;
    }
}

