package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.LocationService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.CircleDto;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    private StateDao stateDao;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private PanchayatDao panchayatDao;

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private StateCircleDao stateCircleDao;

    @Autowired
    private RoleDao roleDao;

//    public void createNewLocation(Location location) {
//        locationDao.saveLocation(location);
//    }
//
//    public void updateExistingLocation(Location location) {
//        Location entity = locationDao.findByLocationId(location.getLocationId());
//        if(entity != null) {
//            entity.setLocation(location.getLocation());
//            entity.setReferenceId(location.getReferenceId());
//        }
//    }
//
//    public void deleteExistingLocation(Location location) {
//        locationDao.deleteLocation(location);
//    }
    /*----------------------General----------------------*/

    @Override
    public List<State> getAllStates() {
        return stateDao.getAllStates();
    }

    /*----------------------State-------------------------*/

    @Override
    public State findStateById(Integer stateId) {
        return stateDao.findByStateId(stateId);
    }

    @Override
    public State findStateByName(String stateName) {
        return stateDao.findByName(stateName).get(0);
    }

    @Override
    public List<State> getStatesByServiceType(String serviceType) {
        List<StateService> list = stateServiceDao.getStatesByServiceType(serviceType);
        List<State> stateList = new ArrayList<>();
        for(StateService stateService : list){
            stateList.add(stateDao.findByStateId(stateService.getStateId()));
        }
        return stateList;
    }

    @Override
    public Date getServiceStartdateForState(Integer stateId,String serviceType) {
        Date startDate=stateServiceDao.getServiceStartDateForState(stateId,serviceType);
        return startDate;
    }

    @Override
    public List<District> getSwachchagrahiDistricts(Integer stateId) {
        return districtDao.getDistrictsOfState(stateDao.findByStateId(stateId).getStateId());
    }

    /*----------------------District-------------------------*/

    @Override
    public District findDistrictById(Integer districtId) throws NullPointerException{
        return districtDao.findByDistrictId(districtId);
    }

    @Override
    public District findDistrictByName(String districtName) {
        return districtDao.findByName(districtName).get(0);
    }

    @Override
    public List<Block> getSwachchagrahiBlocks(Integer districtId) {
        return blockDao.getBlocksOfDistrict(districtDao.findByDistrictId(districtId).getDistrictId());
    }

    @Override
    public State getStateOfDistrict(Integer districtId) {
        return stateDao.findByStateId(districtDao.findByDistrictId(districtId).getStateOfDistrict());
    }

   
    /*----------------------Block-------------------------*/

    @Override
    public Panchayat findPanchayatById(Integer panchayatId) {
        return panchayatDao.findByPanchayatId(panchayatId);
    }

    @Override
    public Panchayat findPanchayatByName(String panchayatName) {
        return panchayatDao.findByName(panchayatName).get(0);
    }

    @Override
    public Block getBlockOfPanchayat(Integer panchayatId) {
//        return districtDao.findByDistrictId(blockDao.findByblockId(blockId).getDistrictOfBlock());
        return blockDao.findByblockId(panchayatDao.findByPanchayatId(panchayatId).getBlockOfpanchayat());
    }

    @Override
    public List<Panchayat> getSwachchagrahiPanchayats(Integer blockId) {
        return panchayatDao.getPanchayatsOfBlock(blockDao.findByblockId(blockId).getBlockId());
    }

    @Override
    public Block findBlockById(Integer blockId) {
        return blockDao.findByblockId(blockId);
    }

    @Override
    public Block findBlockByName(String blockName) {
        return blockDao.findByName(blockName).get(0);
    }

    @Override
    public District getDistrictOfBlock(Integer blockId) {
        return districtDao.findByDistrictId(blockDao.findByblockId(blockId).getDistrictOfBlock());
    }
    @Override
    public Circle findCircleById(Integer circleId) {
        return circleDao.getByCircleId(circleId);
    }

    @Override
    public List<Circle> getAllCirles() {
        return circleDao.getAllCircles();
    }

    @Override
    public List<State> getStatesOfCircle(Circle circle) {
        List<StateCircle> stateCircleList = stateCircleDao.getRelByCircleId(circle.getCircleId());
        List<State> states = new ArrayList<>();
        for(StateCircle stateCircle: stateCircleList){
            states.add(stateDao.findByStateId(stateCircle.getStateId()));
        }
        return states;
    }

    @Override
    public List<CircleDto> getCircleObjectList(User user, String serviceType){
        List<StateCircle> list = new ArrayList<>();
        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(null);
        }
        else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(user.getStateId());
        }else{
            StateCircle stateCircle = new StateCircle();
            stateCircle.setCircleId(districtDao.findByDistrictId(user.getDistrictId()).getCircleOfDistrict());
            stateCircle.setStateId(user.getStateId());
            list.add(stateCircle);
        }

        List<CircleDto> circleList = new ArrayList<>();

        for(StateCircle stateCircle : list){
            CircleDto circleDto = new CircleDto(circleDao.getByCircleId(stateCircle.getCircleId()));
            circleDto.setStateId(stateCircle.getStateId());
            circleDto.setServiceStartDate(stateServiceDao.getServiceStartDateForState(stateCircle.getStateId(), serviceType));
            circleDto.setServiceType(serviceType);
            if(circleDto.getServiceStartDate() != null)
                circleList.add(circleDto);
        }
        return circleList;
    }

//    @Override
//    public List<CircleDto> getCircleObjectList(List<Circle> circleList) {
//        List<CircleDto> circleDtoList = new ArrayList<>();
//        /*for(Circle circle : circleList){
//            CircleDto circleObject = new CircleDto(circle);
//            List<StateCircle> list = stateCircleDao.getRelByCircleId(circle.getCircleId());
//            for(StateCircle sc : list){
//                stateServiceDao.
//            }
//
//
//            circleDtoList.add(circleObject);
//        }*/
//        return circleDtoList;
//    }

    @Override
    public User SetLocations(User user){
        user.setStateName(user.getStateId()==null ? "" : stateDao.findByStateId(user.getStateId()).getStateName());
        user.setDistrictName(user.getDistrictId()==null? "" : districtDao.findByDistrictId(user.getDistrictId()).getDistrictName());
        user.setBlockName(user.getBlockId()==null ? "" :  blockDao.findByblockId(user.getBlockId()).getBlockName());
        user.setRoleName(user.getRoleId()==null ? "" : roleDao.findByRoleId(user.getRoleId()).getRoleDescription());
        return user;
    }
}
