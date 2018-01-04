package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="dim_panchayat")
public class Panchayat {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "SMALLINT")
    private Integer panchayatId;

    @Column(name="panchayat_name")
    private String panchayatName;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="block_id", columnDefinition = "INT")
    private Integer blockOfpanchayat;

    @Column(name="district_id", columnDefinition = "SMALLINT")
    private Integer districtOfpanchayat;

    @Column(name="state_id", columnDefinition = "TINYINT")
    private Integer stateOfpanchayat;

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

    public Integer getBlockOfpanchayat() {
        return blockOfpanchayat;
    }

    public void setBlockOfpanchayat(Integer blockOfpanchayat) {
        this.blockOfpanchayat = blockOfpanchayat;
    }

    public Integer getDistrictOfpanchayat() {
        return districtOfpanchayat;
    }

    public void setDistrictOfpanchayat(Integer districtOfpanchayat) {
        this.districtOfpanchayat = districtOfpanchayat;
    }

    public Integer getStateOfpanchayat() {
        return stateOfpanchayat;
    }

    public void setStateOfpanchayat(Integer stateOfpanchayat) {
        this.stateOfpanchayat = stateOfpanchayat;
    }
}
