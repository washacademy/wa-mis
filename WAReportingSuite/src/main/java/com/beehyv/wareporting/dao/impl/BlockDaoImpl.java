package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.BlockDao;
import com.beehyv.wareporting.model.Block;
import com.beehyv.wareporting.model.District;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("blockDao")
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {
    @Override
    public Block findByBlockId(Integer blockId) {
        return getByKey(blockId);
    }

    @Override
    public Block findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (Block) criteria.list().get(0);
    }

    @Override
    public List<Block> findByName(String blockName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockName", blockName).ignoreCase());
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getBlocksOfDistrict(Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtOfBlock", districtId));
        return (List<Block>) criteria.list();
    }

    @Override
    public Integer getDistrictOfBlock(Integer blockId) {
        return findByBlockId(blockId).getDistrictOfBlock();
    }
}
