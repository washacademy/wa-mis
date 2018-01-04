package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Block;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);

    Block findByLocationId(Long stateId);


    public List<Block> findByName(String blockName);

    public List<Block> getBlocksOfDistrict(Integer districtId);

    public Integer getDistrictOfBlock(Block block);
}
