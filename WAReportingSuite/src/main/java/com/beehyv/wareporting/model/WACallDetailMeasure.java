package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 22/9/17.
 */
@Entity
@Table(name="WA_call_detail_measure")
public class WACallDetailMeasure {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="flw_id" ,columnDefinition = "BIGINT(20)")
    private Long flwId;

    @Column(name="calling_msisdn",columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="operator_id",columnDefinition = "BIGINT(20)")
    private Long operatorId;

    @Column(name="circle_id",columnDefinition = "BIGINT(20)")
    private String circleId;

    @Column(name="start_time",columnDefinition = "DATETIME")
    private Date startTime;

    @Column(name="end_time",columnDefinition = "DATETIME")
    private Date endTime;

    @Column(name="duration",columnDefinition = "INT(11)")
    private Integer duration;

    @Column(name="duration_in_pulse",columnDefinition = "INT(11)")
    private Integer durationPulse;

    @Column(name="call_id",columnDefinition = "VARCHAR(25)")
    private String callId;

    @Column(name="modificationDate",columnDefinition = "DATETIME")
    private Date lastModifiedDate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationPulse() {
        return durationPulse;
    }

    public void setDurationPulse(Integer durationPulse) {
        this.durationPulse = durationPulse;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
