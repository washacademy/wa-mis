package com.beehyv.wareporting.model;

import javax.persistence.*;

/**
 * Created by beehyv on 25/5/17.
 */

@Entity
@Table(name = "state_circle_rel")
public class StateCircle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "TINYINT")
    private Integer id;

    @Column(name = "state_id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name = "circle_id", columnDefinition = "TINYINT")
    private Integer circleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }
}
