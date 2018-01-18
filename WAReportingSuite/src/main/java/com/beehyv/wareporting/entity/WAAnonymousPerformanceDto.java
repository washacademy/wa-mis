package com.beehyv.wareporting.entity;

public class WAAnonymousPerformanceDto {
    private Integer id;
    private Integer circleId;
    private String circleName;
    private Integer anonUsersStartedCourse;
    private Long anonUsersPursuingCourse;
    private Long anonUsersNotPursuingCourse;
    private Integer anonUsersCompletedCourse;
    private Integer anonUsersFailedCourse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getAnonUsersStartedCourse() {
        return anonUsersStartedCourse;
    }

    public void setAnonUsersStartedCourse(Integer anonUsersStartedCourse) {
        this.anonUsersStartedCourse = anonUsersStartedCourse;
    }

    public Long getAnonUsersPursuingCourse() {
        return anonUsersPursuingCourse;
    }

    public void setAnonUsersPursuingCourse(Long anonUsersPursuingCourse) {
        this.anonUsersPursuingCourse = anonUsersPursuingCourse;
    }

    public Long getAnonUsersNotPursuingCourse() {
        return anonUsersNotPursuingCourse;
    }

    public void setAnonUsersNotPursuingCourse(Long anonUsersNotPursuingCourse) {
        this.anonUsersNotPursuingCourse = anonUsersNotPursuingCourse;
    }

    public Integer getAnonUsersCompletedCourse() {
        return anonUsersCompletedCourse;
    }

    public void setAnonUsersCompletedCourse(Integer anonUsersCompletedCourse) {
        this.anonUsersCompletedCourse = anonUsersCompletedCourse;
    }

    public Integer getAnonUsersFailedCourse() {
        return anonUsersFailedCourse;
    }

    public void setAnonUsersFailedCourse(Integer anonUsersFailedCourse) {
        this.anonUsersFailedCourse = anonUsersFailedCourse;
    }
}
