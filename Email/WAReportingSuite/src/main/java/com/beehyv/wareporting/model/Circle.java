package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Entity
@Table(name = "dim_circle")
public class Circle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="circle_id", columnDefinition = "TINYINT(4)")
    private Integer circleId;

    @Column(name="circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

    @Column(name = "last_modified",columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name = "circle_full_name", columnDefinition = "VARCHAR(255)")
    private String circleFullName;

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getCircleFullName() {
        return circleFullName;
    }

    public void setCircleFullName(String circleFullName) {
        this.circleFullName = circleFullName;
    }
}
