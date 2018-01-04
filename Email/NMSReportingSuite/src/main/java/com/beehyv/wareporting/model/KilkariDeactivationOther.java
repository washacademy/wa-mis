package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 14/5/17.
 */
@Entity
@Table(name = "kilkari_six_weeks_no_answer")
public class KilkariDeactivationOther {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Integer kilkariId;

    @Column(name = "mcts_id", columnDefinition = "VARCHAR(45)")
    private String mctsId;

    @Column(name = "rch_id", columnDefinition = "VARCHAR(45)")
    private String rchId;

    @Column(name = "msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name = "deactivation_date", columnDefinition = "DATE")
    private Date deactivationDate;

    @Column(name = "age_on_service", columnDefinition = "TINYINT(4)")
    private Integer ageOnService;

    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @Column(name = "state_id", columnDefinition = "TINYINT")
    private Integer stateId;

    @Column(name = "district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name = "block_id", columnDefinition = "INT(11)")
    private Integer blockId;

    @Column(name = "hsubcenter_id", columnDefinition = "INT(11)")
    private Integer hsubcenterId;

    @Column(name = "village_id", columnDefinition = "INT(11)")
    private Integer villageId;

    @Column(name = "deactivation_reason", columnDefinition = "VARCHAR(100)")
    private String deactivationReason;

    public Integer getKilkariId() {
        return kilkariId;
    }

    public void setKilkariId(Integer kilkariId) {
        this.kilkariId = kilkariId;
    }

    public String getMctsId() {
        return mctsId;
    }

    public void setMctsId(String mctsId) {
        this.mctsId = mctsId;
    }

    public String getRchId() {
        return rchId;
    }

    public void setRchId(String rchId) {
        this.rchId = rchId;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public Integer getAgeOnService() {
        return ageOnService;
    }

    public void setAgeOnService(Integer ageOnService) {
        this.ageOnService = ageOnService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getHsubcenterId() {
        return hsubcenterId;
    }

    public void setHsubcenterId(Integer hsubcenterId) {
        this.hsubcenterId = hsubcenterId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }
}
