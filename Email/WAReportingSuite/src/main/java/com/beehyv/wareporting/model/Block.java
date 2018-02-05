package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by beehyv on 4/5/17.
 */
@Entity
@Table(name = "dim_block")
public class Block {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name="block_name", columnDefinition = "VARCHAR(45)")
    private String blockName;

    @Column(name="last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    @Column(name="loc_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="code", columnDefinition = "BIGINT(20)")
    private Long code;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtOfBlock;

    @Column(name="state_id", columnDefinition = "TINYINT(4)")
    private Integer stateOfBlock;

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
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

    public Integer getDistrictOfBlock() {
        return districtOfBlock;
    }

    public void setDistrictOfBlock(Integer districtOfBlock) {
        this.districtOfBlock = districtOfBlock;
    }

    public Integer getStateOfBlock() {
        return stateOfBlock;
    }

    public void setStateOfBlock(Integer stateOfBlock) {
        this.stateOfBlock = stateOfBlock;
    }
}
