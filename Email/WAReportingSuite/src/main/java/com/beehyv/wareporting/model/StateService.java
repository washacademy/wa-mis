package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 6/6/17.
 */

@Entity
@Table(name="state_service_rel")
public class StateService {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "TINYINT")
    private Integer id;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name = "start_Date",columnDefinition = "DATE")
    private Date serviceStartDate;

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

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }
}
