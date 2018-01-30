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
    @Column(name="id", columnDefinition = "INT(11)")
    private Long Id;

    @Column(name="swc_id", columnDefinition = "BIGINT(20)")
    private Long swcId;

    @Column(name="score", columnDefinition = "INT(11)")
    private Integer score;

    @Column(name="has_passed", columnDefinition = "BIT(1)")
    private Boolean passed;

    @Column(name="chapter_wise_score", columnDefinition = "VARCHAR(45)")
    private String chapterWiseScore;

    @Column(name="last_delivery_status", columnDefinition = "VARCHAR(255)")
    private String lastDeliveryStatus;

    @Column(name="sent_notification", columnDefinition = "BIT(1)")
    private Boolean sentNotification;

    @Column(name="creation_date", columnDefinition = "DATETIME")
    private Date creationDate;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getChapterWiseScore() {
        return chapterWiseScore;
    }

    public void setChapterWiseScore(String chapterWiseScore) {
        this.chapterWiseScore = chapterWiseScore;
    }

    public Boolean getSentNotification() {
        return sentNotification;
    }

    public void setSentNotification(Boolean sentNotification) {
        this.sentNotification = sentNotification;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
