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

    @Column(name="swcachchagrahis_started", columnDefinition = "INT(11)")
    private Integer swcachchagrahisStarted;

    @Column(name="swcachchagrahis_completed", columnDefinition = "INT(11)")
    private Integer swcachchagrahisCompleted; //5.2.3:7 , 5.2.2:6

    @Column(name="swcachchagrahis_failed", columnDefinition = "INT(11)")
    private Integer swcachchagrahisFailed;

    @Column(name="swcachchagrahis_recieved", columnDefinition = "INT(11)")
    private Integer swcachchagrahisRecieved;

    @Column(name="swcachchagrahis_rejected", columnDefinition = "INT(11)")
    private Integer swcachchagrahisRejected;

    //swcachchagrahisAdded = swcachchagrahisRecieved - swcachchagrahisRejected


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
        return swcachchagrahisStarted;
    }

    public void setSwachchagrahisStarted(Integer swcachchagrahisStarted) {
        this.swcachchagrahisStarted = swcachchagrahisStarted;
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

    public Integer getSwachchagrahisRecieved() {
        return swcachchagrahisRecieved;
    }

    public void setSwachchagrahisRecieved(Integer swcachchagrahisRecieved) {
        this.swcachchagrahisRecieved = swcachchagrahisRecieved;
    }

    public Integer getSwachchagrahisRejected() {
        return swcachchagrahisRejected;
    }

    public void setSwachchagrahisRejected(Integer swcachchagrahisRejected) {
        this.swcachchagrahisRejected = swcachchagrahisRejected;
    }
}
