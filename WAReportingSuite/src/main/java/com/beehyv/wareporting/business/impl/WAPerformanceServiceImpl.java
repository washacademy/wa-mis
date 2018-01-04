package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.WAPerformanceService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.model.Block;
import com.beehyv.wareporting.model.District;
import com.beehyv.wareporting.model.Panchayat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 25/9/17.
 */
@Service("waPerformanceServiceImpl")
@Transactional
public class WAPerformanceServiceImpl implements WAPerformanceService{

    @Autowired
    private WAPerformanceDao waPerformanceDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PanchayatDao panchayatDao;


    @Override
    public Long getAccessedCount(Integer locationId, String locationType, Date fromDate, Date toDate){

        Long count = (long)0;
        Long addedCount = (long)0;
        Long differenceCount = (long)0;

        if(locationType.equalsIgnoreCase("DifferenceState")){
            count =  waPerformanceDao.accessedAtLeastOnce(-locationId,"State",fromDate,toDate);
            List<District> districts = districtDao.getDistrictsOfState(-locationId);
            for(District d:districts){
                addedCount += waPerformanceDao.accessedAtLeastOnce(d.getDistrictId(),"District",fromDate,toDate);
            }
            differenceCount = count - addedCount;
            return differenceCount;
        }
        else{
            if(locationType.equalsIgnoreCase("DifferenceDistrict")){

                count =  waPerformanceDao.accessedAtLeastOnce(-locationId,"District",fromDate,toDate);
                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
                for (Block d : blocks) {
                    addedCount += waPerformanceDao.accessedAtLeastOnce(d.getBlockId(),"Block",fromDate,toDate);
                    }
                differenceCount = count - addedCount;
                return differenceCount;
                }
            else{
                if(locationType.equalsIgnoreCase("DifferenceBlock")) {

                    count =  waPerformanceDao.accessedAtLeastOnce(-locationId,"Block",fromDate,toDate);
                    List<Panchayat> panchayats = panchayatDao.getPanchayatsOfBlock(-locationId);
                    for(Panchayat s: panchayats){
                        addedCount += waPerformanceDao.accessedAtLeastOnce(s.getPanchayatId(),"Panchayat",fromDate,toDate);
                        }
                    differenceCount = count - addedCount;
                    return differenceCount;
                }
                else {
                    count =  waPerformanceDao.accessedAtLeastOnce(locationId,locationType,fromDate,toDate);
                    return count;
                }

            }
        }

    }

    @Override
    public Long getNotAccessedcount(Integer locationId, String locationType, Date fromDate, Date toDate){

        Long count = (long)0;
        Long addedCount = (long)0;
        Long differenceCount = (long)0;


        if(locationType.equalsIgnoreCase("DifferenceState")){
            count =  waPerformanceDao.accessedNotOnce(-locationId,"State",fromDate,toDate);
            List<District> districts = districtDao.getDistrictsOfState(-locationId);
            for(District d:districts){
                addedCount += waPerformanceDao.accessedNotOnce(d.getDistrictId(),"District",fromDate,toDate);
            }
            differenceCount = count - addedCount;
            return differenceCount;
        }
        else{
            if(locationType.equalsIgnoreCase("DifferenceDistrict")){

                count =  waPerformanceDao.accessedNotOnce(-locationId,"District",fromDate,toDate);
                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
                for (Block d : blocks) {
                    addedCount += waPerformanceDao.accessedNotOnce(d.getBlockId(),"Block",fromDate,toDate);
                }
                differenceCount = count - addedCount;
                return differenceCount;
            }
            else{
                if(locationType.equalsIgnoreCase("DifferenceBlock")) {

                    count =  waPerformanceDao.accessedNotOnce(-locationId,"Block",fromDate,toDate);
                    List<Panchayat> panchayats = panchayatDao.getPanchayatsOfBlock(-locationId);
                    for(Panchayat s: panchayats){
                        addedCount += waPerformanceDao.accessedNotOnce(s.getPanchayatId(),"Panchayat",fromDate,toDate);
                    }
                    differenceCount = count - addedCount;
                    return differenceCount;
                }
                else{
                    count =  waPerformanceDao.accessedNotOnce(locationId,locationType,fromDate,toDate);
                    return count;
                }
            }
        }
    }

    @Override
    public Integer getSwachchagrahisFailed(Integer locationId, String locationType, Date fromDate, Date toDate){

        Integer count = 0;
        Integer addedCount = 0;
        Integer differenceCount = 0;


        if(locationType.equalsIgnoreCase("DifferenceState")){
            count =  waPerformanceDao.getSwachchagrahisFailed(-locationId,"State",fromDate,toDate);
            List<District> districts = districtDao.getDistrictsOfState(-locationId);
            for(District d:districts){
                addedCount += waPerformanceDao.getSwachchagrahisFailed(d.getDistrictId(),"District",fromDate,toDate);
            }
            differenceCount = count - addedCount;
            return differenceCount;
        }
        else{
            if(locationType.equalsIgnoreCase("DifferenceDistrict")){

                count =  waPerformanceDao.getSwachchagrahisFailed(-locationId,"District",fromDate,toDate);
                List<Block> blocks = blockDao.getBlocksOfDistrict(-locationId);
                for (Block d : blocks) {
                    addedCount += waPerformanceDao.getSwachchagrahisFailed(d.getBlockId(),"Block",fromDate,toDate);
                }
                differenceCount = count - addedCount;
                return differenceCount;
            }
            else{
                if(locationType.equalsIgnoreCase("DifferenceBlock")) {

                    count =  waPerformanceDao.getSwachchagrahisFailed(-locationId,"Block",fromDate,toDate);
                    List<Panchayat> panchayats = panchayatDao.getPanchayatsOfBlock(-locationId);
                    for(Panchayat s: panchayats){
                        addedCount += waPerformanceDao.getSwachchagrahisFailed(s.getPanchayatId(),"Panchayat",fromDate,toDate);
                    }
                    differenceCount = count - addedCount;
                    return differenceCount;
                }
                else{
                    count =  waPerformanceDao.getSwachchagrahisFailed(locationId,locationType,fromDate,toDate);
                    return count;
                }
            }
        }
    }

}
