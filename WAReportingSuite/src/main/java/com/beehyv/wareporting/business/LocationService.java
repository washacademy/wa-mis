package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.CircleDto;
import com.beehyv.wareporting.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface LocationService {

    /*----------------------General-----------------------*/

    List<State> getAllStates();

    /*----------------------State-------------------------*/

    State findStateById(Integer stateId);

    State findStateByName(String stateName);

    List<District> getSwachchagrahiDistricts(Integer stateId);

    /*----------------------District-------------------------*/

    District findDistrictById(Integer districtId);

    District findDistrictByName(String districtName);

    List<Block> getSwachchagrahiBlocks(Integer districtId);

    State getStateOfDistrict(Integer districtId);


    /*----------------------Block-------------------------*/

    Block findBlockById(Integer blockId);

    Block findBlockByName(String blockName);

    District getDistrictOfBlock(Integer blockId);

    List<Panchayat> getSwachchagrahiPanchayats(Integer blockId);

    /*----------------------Panchayat-------------------------*/

    Panchayat findPanchayatById(Integer panchayatId);

    Panchayat findPanchayatByName(String panchayatName);

    Block getBlockOfPanchayat(Integer panchayatId);

     /*----------------------Circle-------------------------*/
    Circle findCircleById(Integer circleId);

    List<Circle> getAllCirles();

    List<State> getStatesOfCircle(Circle circle);

    User SetLocations(User user);
}
