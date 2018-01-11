package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="WA_aggregate_daily_counts")
public class WAAggregateDaily {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="swachchagrahis_started", columnDefinition = "INT(11)")
    private Integer swachchagrahisStarted;

    @Column(name="swachchagrahis_completed", columnDefinition = "INT(11)")
    private Integer swachchagrahisCompleted;

    @Column(name="swachchagrahis_failed", columnDefinition = "INT(11)")
    private Integer swachchagrahisFailed;

    @Column(name="swachchagrahis_recieved", columnDefinition = "INT(11)")
    private Integer swachchagrahisRecieved;

    @Column(name="swachchagrahis_rejected", columnDefinition = "INT(11)")
    private Integer swachchagrahisRejected;

    //swachchagrahisAdded = swachchagrahisRecieved - swachchagrahisRejected


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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Integer getSwachchagrahisRecieved() {
        return swachchagrahisRecieved;
    }

    public void setSwachchagrahisRecieved(Integer swachchagrahisRecieved) {
        this.swachchagrahisRecieved = swachchagrahisRecieved;
    }

    public Integer getSwachchagrahisRejected() {
        return swachchagrahisRejected;
    }

    public void setSwachchagrahisRejected(Integer swachchagrahisRejected) {
        this.swachchagrahisRejected = swachchagrahisRejected;
    }
}
