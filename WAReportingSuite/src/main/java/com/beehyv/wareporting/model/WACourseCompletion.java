package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 3/5/17.
 */
@Entity
@Table(name="WA_course_completion")
public class WACourseCompletion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="swc_id", columnDefinition = "BIGINT(20)")
    private Long swcId;

    @Column(name="score", columnDefinition = "INT(11)")
    private Integer score;

    @Column(name="has_passed", columnDefinition = "BIT(1)")
    private Boolean passed;

    @Column(name="last_delivery_status", columnDefinition = "VARCHAR(255)")
    private String lastDeliveryStatus;

    @Column(name="sent_notification", columnDefinition = "BIT(1)")
    private Boolean sentNotification;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModifiedDate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getSwcId() {
        return swcId;
    }

    public void setSwcId(Long swcId) {
        this.swcId = swcId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getLastDeliveryStatus() {
        return lastDeliveryStatus;
    }

    public void setLastDeliveryStatus(String lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
    }

    public Boolean getsentNotification() {
        return sentNotification;
    }

    public void setsentNotification(Boolean sentNotification) {
        this.sentNotification = sentNotification;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
