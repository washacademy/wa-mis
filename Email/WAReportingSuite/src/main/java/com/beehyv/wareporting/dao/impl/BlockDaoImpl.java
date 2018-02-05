package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.BlockDao;
import com.beehyv.wareporting.model.Block;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("blockDao")
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {

    @Override
    public Block findByBlockId(Integer blockId) {
        return getByKey(blockId);
    }
}
