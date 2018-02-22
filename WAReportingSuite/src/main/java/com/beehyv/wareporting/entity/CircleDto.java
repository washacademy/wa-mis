package com.beehyv.wareporting.entity;

import com.beehyv.wareporting.model.Circle;

import java.util.Date;

/**
 * Created by beehyv on 13/6/17.
 */
public class CircleDto {

    private Integer circleId;

    private String circleName;

    private Integer stateId;

    public CircleDto(Circle circle){
        this.circleId = circle.getCircleId();
        this.circleName = circle.getCircleName();
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

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

}
