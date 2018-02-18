package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.business.LocationService;
import com.beehyv.wareporting.business.ReportService;
import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.entity.CircleDto;
import com.beehyv.wareporting.entity.StateObject;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by beehyv on 22/3/17.
 */
@Controller
@RequestMapping(value = {"/wa/location"})
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    /*--------------------------State-----------------------------*/

    @RequestMapping(value = {"/states"}, method = RequestMethod.GET)
    public @ResponseBody List<State> getAllStates() {
        User user = userService.getCurrentUser();
        List<State> states;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            states = locationService.getAllStates();
        }
        else{
            states = new ArrayList<>();
            states.add(locationService.findStateById(user.getStateId()));
        }
        return states;
    }

    /*--------------------------District-----------------------------*/

    @RequestMapping(value = {"/districts/{stateId}"}, method = RequestMethod.GET)
    public @ResponseBody List<District> getDistrictsOfState(@PathVariable("stateId") Integer stateId) {
        User user = userService.getCurrentUser();
        List<District> districts;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            districts = locationService.getSwachchagrahiDistricts(stateId);
        }
        else if(user.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            districts = locationService.getSwachchagrahiDistricts(user.getStateId());
        }
        else{
            districts = new ArrayList<>();
            districts.add(locationService.findDistrictById(user.getDistrictId()));
        }
        return districts;
    }

    @RequestMapping(value = {"/SoD/{districtId}"}, method = RequestMethod.GET)
    public @ResponseBody State getStateOfDistrict(@PathVariable("districtId") Integer districtId) {
        return locationService.getStateOfDistrict(districtId);
    }

    /*--------------------------Block-----------------------------*/

    @RequestMapping(value = {"/blocks/{districtId}"}, method = RequestMethod.GET)
    public @ResponseBody List<Block> getBlocksOfDistrict(@PathVariable("districtId") Integer districtId) {
        User user = userService.getCurrentUser();
        List<Block> blocks;
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            blocks = locationService.getSwachchagrahiBlocks(districtId);
        }
        else if(user.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            if(locationService.findDistrictById(districtId).getStateOfDistrict().equals(user.getStateId())){
                blocks = locationService.getSwachchagrahiBlocks(districtId);
            }
            else{
                blocks = new ArrayList<>();
            }
        }
        else if(user.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel())){
            blocks = locationService.getSwachchagrahiBlocks(user.getDistrictId());
        }
        else{
            blocks = new ArrayList<>();
            blocks.add(locationService.findBlockById(user.getBlockId()));
        }

        return blocks;
    }

    @RequestMapping(value = {"/DoB/{blockId}"}, method = RequestMethod.GET)
    public @ResponseBody District getDistrictOfBlock(@PathVariable("blockId") Integer blockId) {
        return locationService.getDistrictOfBlock(blockId);
    }


    @RequestMapping(value = {"/panchayats/{blockId}"}, method = RequestMethod.GET)
    public @ResponseBody List<Panchayat> getpanchayatsOfBlock(@PathVariable("blockId") Integer blockId) {
        User user = userService.getCurrentUser();
        List<Panchayat> panchayats = new ArrayList<>();
        if(user.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            panchayats = locationService.getSwachchagrahiPanchayats(blockId);
        }
        else if(user.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            if(locationService.findDistrictById(blockId).getStateOfDistrict().equals(user.getStateId())){
                panchayats = locationService.getSwachchagrahiPanchayats(blockId);
            }
        }
        else if(user.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel())){
            panchayats = locationService.getSwachchagrahiPanchayats(blockId);
        }
        else {
            panchayats =locationService.getSwachchagrahiPanchayats(user.getBlockId());
        }


        return panchayats;
    }

    @RequestMapping(value = {"/BoS/{sucenterId}"}, method = RequestMethod.GET)
    public @ResponseBody Block getBlockOfPanchayat(@PathVariable("sucenterId") Integer sucenterId) {
        return locationService.getBlockOfPanchayat(sucenterId);
    }

    /*--------------------------Circle-----------------------------*/

    @RequestMapping(value = {"/circles"}, method = RequestMethod.GET)
    @ResponseBody List<Circle> getCircles() {
        User currentUser = userService.getCurrentUser();
        if(currentUser.getAccessLevel().equalsIgnoreCase("NATIONAL"))
        {
            return locationService.getAllCirles();
        }
        else return reportService.getUserCircles(currentUser);
    }

    /*@RequestMapping(value = {"/circle/{serviceType}"}, method = RequestMethod.GET)
    public @ResponseBody List<CircleDto> getCirclesByServiceType(@PathVariable("serviceType") String serviceType) {
        User user = userService.getCurrentUser();
        List<CircleDto> finalCircleDtoList = new ArrayList<>();
        List<CircleDto> circleDtoList = locationService.getCircleObjectList(user, serviceType);
        Map<Integer, CircleDto> map = new HashMap<>();
        for(CircleDto circleDto:circleDtoList){
            if(map.get(circleDto.getCircleId()) == null || map.get(circleDto.getCircleId()).getServiceStartDate().after(circleDto.getServiceStartDate())){
                map.put(circleDto.getCircleId(), circleDto);
            }
        }
        for(Integer key : map.keySet()){
            finalCircleDtoList.add(map.get(key));
        }
        return finalCircleDtoList;
    }*/



    /*--------------------------Extra-----------------------------*/
}
