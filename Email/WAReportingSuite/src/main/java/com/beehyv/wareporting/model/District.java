package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name="dim_district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id", columnDefinition = "SMALLINT")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name = "loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name = "code", columnDefinition = "BIGINT(20)")
    private Long code;

    @Column(name = "state_id", columnDefinition = "TINYINT")
    private Integer stateOfDistrict;

    @Column(name = "circle_id", columnDefinition = "TINYINT")
    private Integer circleOfDistrict;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Integer getStateOfDistrict() {
        return stateOfDistrict;
    }

    public void setStateOfDistrict(Integer stateOfDistrict) {
        this.stateOfDistrict = stateOfDistrict;
    }

    public Integer getCircleOfDistrict() {
        return circleOfDistrict;
    }

    public void setCircleOfDistrict(Integer circleOfDistrict) {
        this.circleOfDistrict = circleOfDistrict;
    }
}
