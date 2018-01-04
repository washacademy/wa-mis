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

    @Column(name="flw_id")
    private Long flwId;

    @Column(name="score")
    private Integer score;

    @Column(name="has_passed")
    private Boolean passed;

    @Column(name="chapter_wise_score")
    private String chapterWiseScore;

    @Column(name="last_delivery_status")
    private String lastDeliveryStatus;

    @Column(name="sent_notification")
    private Boolean sentNotification;

    @Column(name="last_modified")
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

    public String getChapterWiseScore() {
        return chapterWiseScore;
    }

    public void setChapterWiseScore(String chapterWiseScore) {
        this.chapterWiseScore = chapterWiseScore;
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
