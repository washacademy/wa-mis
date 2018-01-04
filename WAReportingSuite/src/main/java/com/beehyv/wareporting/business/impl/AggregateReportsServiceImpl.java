package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.AggregateReportsService;

import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("aggregateReportsService")
@Transactional
public class AggregateReportsServiceImpl implements AggregateReportsService {

    @Autowired
    private UserDao userDao;

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

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeWADao aggregateCumulativeWADao;

    @Override
    public List<WACumulativeSummary> getCumulativeSummaryWAReport(Integer locationId, String locationType, Date toDate){
        List<WACumulativeSummary> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("M");
            for(State s:states){
                CumulativeSummery.add(aggregateCumulativeWADao.getWACumulativeSummery(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                WACumulativeSummary stateCounts = aggregateCumulativeWADao.getWACumulativeSummery(locationId,"State",toDate);
                Integer swachchagrahisRegistered = 0;
                Integer swachchagrahisStarted = 0;
                Integer swachchagrahisNotStarted = 0;
                Integer swachchagrahisCompleted = 0;
                Integer swachchagrahisFailed = 0;
                Integer swachchagrahisRejected = 0;
                for(District d:districts){
                    WACumulativeSummary distrcitCount = aggregateCumulativeWADao.getWACumulativeSummery(d.getDistrictId(),locationType,toDate);
                    CumulativeSummery.add(aggregateCumulativeWADao.getWACumulativeSummery(d.getDistrictId(),locationType,toDate));
                    swachchagrahisStarted+=distrcitCount.getSwachchagrahisStarted();
                    swachchagrahisCompleted+=distrcitCount.getSwachchagrahisCompleted();
                    swachchagrahisFailed+=distrcitCount.getSwachchagrahisFailed();
                    swachchagrahisNotStarted+=distrcitCount.getSwachchagrahisNotStarted();
                    swachchagrahisRejected+=distrcitCount.getSwachchagrahisRejected();
                    swachchagrahisRegistered+=distrcitCount.getSwachchagrahisRegistered();
                }
                WACumulativeSummary noDistrictCount = new WACumulativeSummary();
                noDistrictCount.setSwachchagrahisRejected(stateCounts.getSwachchagrahisRejected()-swachchagrahisRejected);
                noDistrictCount.setSwachchagrahisNotStarted(stateCounts.getSwachchagrahisNotStarted()-swachchagrahisNotStarted);
                noDistrictCount.setSwachchagrahisRegistered(stateCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered);
                noDistrictCount.setSwachchagrahisFailed(stateCounts.getSwachchagrahisFailed()-swachchagrahisFailed);
                noDistrictCount.setSwachchagrahisCompleted(stateCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted);
                noDistrictCount.setSwachchagrahisStarted(stateCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId(stateCounts.getSwachchagrahisRejected()-swachchagrahisRejected+stateCounts.getSwachchagrahisNotStarted()
                        -swachchagrahisNotStarted+stateCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered+stateCounts.getSwachchagrahisFailed()
                        -swachchagrahisFailed+stateCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted+stateCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                noDistrictCount.setLocationId((long)-locationId);
                CumulativeSummery.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    WACumulativeSummary districtCounts = aggregateCumulativeWADao.getWACumulativeSummery(locationId,"District",toDate);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for (Block d : blocks) {
                        WACumulativeSummary blockCount = aggregateCumulativeWADao.getWACumulativeSummery(d.getBlockId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeWADao.getWACumulativeSummery(d.getBlockId(), locationType,toDate));
                        swachchagrahisStarted+=blockCount.getSwachchagrahisStarted();
                        swachchagrahisCompleted+=blockCount.getSwachchagrahisCompleted();
                        swachchagrahisFailed+=blockCount.getSwachchagrahisFailed();
                        swachchagrahisNotStarted+=blockCount.getSwachchagrahisNotStarted();
                        swachchagrahisRejected+=blockCount.getSwachchagrahisRejected();
                        swachchagrahisRegistered+=blockCount.getSwachchagrahisRegistered();
                    }
                    WACumulativeSummary noBlockCount = new WACumulativeSummary();
                    noBlockCount.setSwachchagrahisRejected(districtCounts.getSwachchagrahisRejected()-swachchagrahisRejected);
                    noBlockCount.setSwachchagrahisNotStarted(districtCounts.getSwachchagrahisNotStarted()-swachchagrahisNotStarted);
                    noBlockCount.setSwachchagrahisRegistered(districtCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered);
                    noBlockCount.setSwachchagrahisFailed(districtCounts.getSwachchagrahisFailed()-swachchagrahisFailed);
                    noBlockCount.setSwachchagrahisCompleted(districtCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted);
                    noBlockCount.setSwachchagrahisStarted(districtCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId(districtCounts.getSwachchagrahisRejected()-swachchagrahisRejected+districtCounts.getSwachchagrahisNotStarted()
                            -swachchagrahisNotStarted+districtCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered+districtCounts.getSwachchagrahisFailed()
                            -swachchagrahisFailed+districtCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted+districtCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                    noBlockCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(noBlockCount);
                }
                else {
                    List<Panchayat> panchayats = panchayatDao.getPanchayatsOfBlock(locationId);
                    WACumulativeSummary blockCounts = aggregateCumulativeWADao.getWACumulativeSummery(locationId,"block",toDate);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for(Panchayat s: panchayats){
                        WACumulativeSummary panchayatCount = aggregateCumulativeWADao.getWACumulativeSummery(s.getPanchayatId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeWADao.getWACumulativeSummery(s.getPanchayatId(), locationType,toDate));
                        swachchagrahisStarted+=panchayatCount.getSwachchagrahisStarted();
                        swachchagrahisCompleted+=panchayatCount.getSwachchagrahisCompleted();
                        swachchagrahisFailed+=panchayatCount.getSwachchagrahisFailed();
                        swachchagrahisNotStarted+=panchayatCount.getSwachchagrahisNotStarted();
                        swachchagrahisRejected+=panchayatCount.getSwachchagrahisRejected();
                        swachchagrahisRegistered+=panchayatCount.getSwachchagrahisRegistered();
                    }
                    WACumulativeSummary noPanchayatCount = new WACumulativeSummary();
                    noPanchayatCount.setSwachchagrahisRejected(blockCounts.getSwachchagrahisRejected()-swachchagrahisRejected);
                    noPanchayatCount.setSwachchagrahisNotStarted(blockCounts.getSwachchagrahisNotStarted()-swachchagrahisNotStarted);
                    noPanchayatCount.setSwachchagrahisRegistered(blockCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered);
                    noPanchayatCount.setSwachchagrahisFailed(blockCounts.getSwachchagrahisFailed()-swachchagrahisFailed);
                    noPanchayatCount.setSwachchagrahisCompleted(blockCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted);
                    noPanchayatCount.setSwachchagrahisStarted(blockCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                    noPanchayatCount.setLocationType("DifferenceBlock");
                    noPanchayatCount.setId(blockCounts.getSwachchagrahisRejected()-swachchagrahisRejected+blockCounts.getSwachchagrahisNotStarted()-swachchagrahisNotStarted+blockCounts.getSwachchagrahisRegistered()-swachchagrahisRegistered+blockCounts.getSwachchagrahisFailed()-swachchagrahisFailed+blockCounts.getSwachchagrahisCompleted()-swachchagrahisCompleted+blockCounts.getSwachchagrahisStarted()-swachchagrahisStarted);
                    noPanchayatCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(noPanchayatCount);
                }
            }
        }

        return CumulativeSummery;
    };

}

