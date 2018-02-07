package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WA_anonymous_users_summary")
public class WAAnonymousUsersSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name = "circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

    @Column(name = "circle_id", columnDefinition = "INT(11)")
    private Integer circleId;

    @Column(name = "anonymous_users_started_course", columnDefinition = "INT(11)")
    private Integer anonymousUsersStartedCourse;
//
//    @Column(name = "anonymous_users_pursuing_course", columnDefinition = "INT(11)")
//    private Integer anonymousUsersPursuingCourse;
//
//    @Column(name = "anonymous_users_not_pursuing_course", columnDefinition = "INT(11)")
//    private Integer anonymousUsersNotPursuingCourse;

    @Column(name = "anonymous_users_completed_successfully", columnDefinition = "INT(11)")
    private Integer anonymousUsersCompletedCourse;

//    @Column(name = "anonymous_users_failed_course", columnDefinition = "INT(11)")
//    private Integer anonymousUsersFailedCourse;

    @Column(name = "date", columnDefinition = "DATETIME")
    private Date date;

    public WAAnonymousUsersSummary(){

    }

    public WAAnonymousUsersSummary(Integer id, String circleName, Integer circleId, Integer anonymousUsersStartedCourse, Integer anonymousUsersPursuingCourse, Integer anonymousUsersNotPursuingCourse, Integer anonymousUsersCompletedCourse, Integer anonymousUsersFailedCourse,Date date){
        this.id = id;
        this.circleName = circleName;
        this.circleId = circleId;
        this.anonymousUsersStartedCourse = anonymousUsersStartedCourse;
//        this.anonymousUsersPursuingCourse = anonymousUsersPursuingCourse;
//        this.anonymousUsersNotPursuingCourse = anonymousUsersNotPursuingCourse;
        this.anonymousUsersCompletedCourse = anonymousUsersCompletedCourse;
//        this.anonymousUsersFailedCourse = anonymousUsersFailedCourse;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Integer getAnonymousUsersStartedCourse() {
        return anonymousUsersStartedCourse;
    }

    public void setAnonymousUsersStartedCourse(Integer anonymousUsersStartedCourse) {
        this.anonymousUsersStartedCourse = anonymousUsersStartedCourse;
    }
//
//    public Integer getAnonymousUsersPursuingCourse() {
//        return anonymousUsersPursuingCourse;
//    }
//
//    public void setAnonymousUsersPursuingCourse(Integer anonymousUsersPursuingCourse) {
//        this.anonymousUsersPursuingCourse = anonymousUsersPursuingCourse;
//    }
//
//    public Integer getAnonymousUsersNotPursuingCourse() {
//        return anonymousUsersNotPursuingCourse;
//    }
//
//    public void setAnonymousUsersNotPursuingCourse(Integer anonymousUsersNotPursuingCourse) {
//        this.anonymousUsersNotPursuingCourse = anonymousUsersNotPursuingCourse;
//    }

    public Integer getAnonymousUsersCompletedCourse() {
        return anonymousUsersCompletedCourse;
    }

    public void setAnonymousUsersCompletedCourse(Integer anonymousUsersCompletedCourse) {
        this.anonymousUsersCompletedCourse = anonymousUsersCompletedCourse;
    }

//    public Integer getAnonymousUsersFailedCourse() {
//        return anonymousUsersFailedCourse;
//    }
//
//    public void setAnonymousUsersFailedCourse(Integer anonymousUsersFailedCourse) {
//        this.anonymousUsersFailedCourse = anonymousUsersFailedCourse;
//    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
