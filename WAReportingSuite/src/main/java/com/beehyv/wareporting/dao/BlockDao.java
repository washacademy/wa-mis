package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Block;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    Block findByBlockId(Integer blockId);

    Block findByLocationId(Long stateId);


    List<Block> findByName(String blockName);

    List<Block> getBlocksOfDistrict(Integer districtId);

    Integer getDistrictOfBlock(Block block);
}
