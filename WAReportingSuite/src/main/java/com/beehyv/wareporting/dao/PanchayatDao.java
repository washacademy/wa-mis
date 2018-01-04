package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.Panchayat;

import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface PanchayatDao {

    public Panchayat findByPanchayatId(Integer PanchayatId);

    Panchayat findByLocationId(Integer stateId);

    public List<Panchayat> findByName(String PanchayatName);

    public List<Panchayat> getPanchayatsOfBlock(Integer blockId);

    public Integer getBlockOfPanchayat(Panchayat Panchayat);
}
