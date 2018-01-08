package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Block;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);
}
