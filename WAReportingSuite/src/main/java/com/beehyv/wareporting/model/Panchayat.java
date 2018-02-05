package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="dim_panchayat")
public class Panchayat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BIGINT(20)")
    private Integer panchayatId;

    @Column(name = "panchayat_name", columnDefinition = "VARCHAR(45)")
    private String panchayatName;

    @Column(name = "last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name = "loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name = "code", columnDefinition = "BIGINT(20)")
    private Long code;

    @Column(name = "block_id", columnDefinition = "INT(11)")
    private Integer blockOfPanchayat;

    @Column(name = "district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtOfPanchayat;

    @Column(name = "state_id", columnDefinition = "TINYINT(4)")
    private Integer stateOfPanchayat;

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
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

    public Integer getBlockOfPanchayat() {
        return blockOfPanchayat;
    }

    public void setBlockOfPanchayat(Integer blockOfPanchayat) {
        this.blockOfPanchayat = blockOfPanchayat;
    }

    public Integer getDistrictOfPanchayat() {
        return districtOfPanchayat;
    }

    public void setDistrictOfPanchayat(Integer districtOfPanchayat) {
        this.districtOfPanchayat = districtOfPanchayat;
    }

    public Integer getStateOfPanchayat() {
        return stateOfPanchayat;
    }

    public void setStateOfPanchayat(Integer stateOfPanchayat) {
        this.stateOfPanchayat = stateOfPanchayat;
    }
}
