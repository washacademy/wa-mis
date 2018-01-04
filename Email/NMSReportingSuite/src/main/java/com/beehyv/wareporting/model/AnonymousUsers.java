package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by beehyv on 16/5/17.
 */
@Entity
@Table(name = "ma_anonymous_users")
public class AnonymousUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Integer anonymousId;

    @Column(name = "circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

    @Column(name = "msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name = "last_called_date", columnDefinition = "DATE")
    private Date lastCalledDate;

    @Column(name = "last_called_time", columnDefinition = "DATETIME")
    private Timestamp lastCalledTime;

    @Column(name = "last_modified", columnDefinition = "DATETIME")
    private Timestamp lastModified;

    public Integer getAnonymousId() {
        return anonymousId;
    }

    public void setAnonymousId(Integer anonymousId) {
        this.anonymousId = anonymousId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Date getLastCalledDate() {
        return lastCalledDate;
    }

    public void setLastCalledDate(Date lastCalledDate) {
        this.lastCalledDate = lastCalledDate;
    }

    public Timestamp getLastCalledTime() {
        return lastCalledTime;
    }

    public void setLastCalledTime(Timestamp lastCalledTime) {
        this.lastCalledTime = lastCalledTime;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
