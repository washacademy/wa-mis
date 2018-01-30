package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.WAAggregateReportsService;

import com.beehyv.wareporting.business.WAAnonymousSummaryService;
import com.beehyv.wareporting.business.WAPerformanceService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.*;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.beehyv.wareporting.utils.ServiceFunctions.dateAdder;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("waAggregateReportsService")
@Transactional
public class WAAggregateReportsServiceImpl implements WAAggregateReportsService {

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
    private CircleDao circleDao;

    @Autowired
    private PanchayatDao panchayatDao;

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeWADao aggregateCumulativeWADao;

    @Autowired
    private WAPerformanceService waPerformanceService;

    @Autowired
    private WAAnonymousSummaryService waAnonymousSummaryService;

    @Autowired
    private WAAnonymousUsersCumulativeDao waAnonymousUsersCumulativeDao;

    private List<WACumulativeSummary> getWACumulativeSummary(Integer locationId, String locationType, Date toDate){
        List<WACumulativeSummary> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getAllStates();
            for(State s:states){
                CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                WACumulativeSummary stateCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"State",toDate);
                Integer swachchagrahisRegistered = 0;
                Integer swachchagrahisStarted = 0;
                Integer swachchagrahisNotStarted = 0;
                Integer swachchagrahisCompleted = 0;
                Integer swachchagrahisFailed = 0;
                Integer swachchagrahisRejected = 0;
                for(District d:districts){
                    WACumulativeSummary districtCount = aggregateCumulativeWADao.getWACumulativeSummary(d.getDistrictId(),locationType,toDate);
                    CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(d.getDistrictId(),locationType,toDate));
                    swachchagrahisStarted+=districtCount.getSwachchagrahisStarted();
                    swachchagrahisCompleted+=districtCount.getSwachchagrahisCompleted();
                    swachchagrahisFailed+=districtCount.getSwachchagrahisFailed();
                    swachchagrahisNotStarted+=districtCount.getSwachchagrahisNotStarted();
                    swachchagrahisRejected+=districtCount.getSwachchagrahisRejected();
                    swachchagrahisRegistered+=districtCount.getSwachchagrahisRegistered();
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
                CumulativeSummary.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    WACumulativeSummary districtCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"District",toDate);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for (Block d : blocks) {
                        WACumulativeSummary blockCount = aggregateCumulativeWADao.getWACumulativeSummary(d.getBlockId(),locationType,toDate);
                        CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(d.getBlockId(), locationType,toDate));
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
                    CumulativeSummary.add(noBlockCount);
                }
                else {
                    List<Panchayat> panchayats = panchayatDao.getPanchayatsOfBlock(locationId);
                    WACumulativeSummary blockCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"block",toDate);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for(Panchayat s: panchayats){
                        WACumulativeSummary panchayatCount = aggregateCumulativeWADao.getWACumulativeSummary(s.getPanchayatId(),locationType,toDate);
                        CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(s.getPanchayatId(), locationType,toDate));
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
                    CumulativeSummary.add(noPanchayatCount);
                }
            }
        }

        return CumulativeSummary;
    }

    private List<WAAnonymousUsersSummary> getWAAnonymousCumulativeSummary(Integer circleId,Date toDate){
        List<WAAnonymousUsersSummary> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();

        CumulativeSummary.add(waAnonymousUsersCumulativeDao.getWAAnonymousCumulativeSummary(circleId, toDate));

        return CumulativeSummary;
    }

    @Override
    public AggregateResponseDto getWAPerformanceReport(Date fromDate,Date toDate,Integer circleId,Integer stateId,Integer districtId,Integer blockId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WAPerformanceDto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReportStart = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReportEnd = new ArrayList<>();

        if (stateId == 0) {
            cumulativeSummaryReportStart.addAll(getWACumulativeSummary(0, "State", fromDate));
            cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(0, "State", toDate));
        } else {
            if (districtId == 0) {
                cumulativeSummaryReportStart.addAll(getWACumulativeSummary(stateId, "District", fromDate));
                cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(stateId, "District", toDate));
            } else {
                if (blockId == 0) {
                    cumulativeSummaryReportStart.addAll(getWACumulativeSummary(districtId, "Block", fromDate));
                    cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(districtId, "Block", toDate));

                } else {
                    cumulativeSummaryReportStart.addAll(getWACumulativeSummary(blockId, "Panchayat", fromDate));
                    cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate));

                }
            }
        }

        for (int i = 0; i < cumulativeSummaryReportEnd.size(); i++) {
            for (int j = 0; j < cumulativeSummaryReportStart.size(); j++) {
                if (cumulativeSummaryReportEnd.get(i).getLocationId().equals(cumulativeSummaryReportStart.get(j).getLocationId())) {
                    WACumulativeSummary a = cumulativeSummaryReportEnd.get(i);
                    WACumulativeSummary b = cumulativeSummaryReportStart.get(j);
                    WAPerformanceDto summaryDto1 = new WAPerformanceDto();
                    summaryDto1.setId(a.getId());
                    summaryDto1.setLocationId(a.getLocationId());
                    summaryDto1.setSwachchagrahisCompletedCourse(a.getSwachchagrahisCompleted() - b.getSwachchagrahisCompleted());
                    summaryDto1.setSwachchagrahisStartedCourse(a.getSwachchagrahisStarted() - b.getSwachchagrahisStarted());
                    summaryDto1.setLocationType(a.getLocationType());

                    String locationType = a.getLocationType();
                    if (locationType.equalsIgnoreCase("State")) {
                        summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                    }
                    if (locationType.equalsIgnoreCase("District")) {
                        summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                    }
                    if (locationType.equalsIgnoreCase("Block")) {
                        summaryDto1.setLocationName(blockDao.findByBlockId(a.getLocationId().intValue()).getBlockName());
                    }
                    if (locationType.equalsIgnoreCase("Panchayat")) {
                        summaryDto1.setLocationName(panchayatDao.findByPanchayatId(a.getLocationId().intValue()).getPanchayatName());
                    }
                    if (locationType.equalsIgnoreCase("DifferenceState")) {
                        summaryDto1.setLocationName("No District Count");
                        summaryDto1.setLocationId((long) -1);
                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        summaryDto1.setLocationName("No Block Count");
                        summaryDto1.setLocationId((long) -1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        summaryDto1.setLocationName("No Panchayat Count");
                        summaryDto1.setLocationId((long) -1);

                    }
                    summaryDto1.setSwachchagrahisFailedCourse(waPerformanceService.getSwachchagrahisFailed(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
                    summaryDto1.setSwachchagrahisPursuingCourse(waPerformanceService.getAccessedCount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
                    summaryDto1.setSwachchagrahisNotPursuingCourse(waPerformanceService.getNotAccessedcount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));

                    if (summaryDto1.getSwachchagrahisCompletedCourse() + summaryDto1.getSwachchagrahisFailedCourse() + summaryDto1.getSwachchagrahisStartedCourse() + summaryDto1.getSwachchagrahisPursuingCourse() + summaryDto1.getSwachchagrahisNotPursuingCourse() != 0) {
                        summaryDto.add(summaryDto1);
                    }
                }
            }
        }
        
        aggregateResponseDto.setTableData(summaryDto);
        return aggregateResponseDto;
    }

    @Override
    public AggregateResponseDto getWASubscriberReport(Date fromDate, Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WASubscriberDto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativesummaryReportStart = new ArrayList<>();
        List<WACumulativeSummary> cumulativesummaryReportEnd = new ArrayList<>();

        if (stateId == 0) {
            cumulativesummaryReportStart.addAll(getWACumulativeSummary(0, "State", fromDate));
            cumulativesummaryReportEnd.addAll(getWACumulativeSummary(0, "State", toDate));
        } else {
            if (districtId == 0) {
                cumulativesummaryReportStart.addAll(getWACumulativeSummary(stateId, "District", fromDate));
                cumulativesummaryReportEnd.addAll(getWACumulativeSummary(stateId, "District", toDate));
            } else {
                if (blockId == 0) {
                    cumulativesummaryReportStart.addAll(getWACumulativeSummary(districtId, "Block", fromDate));
                    cumulativesummaryReportEnd.addAll(getWACumulativeSummary(districtId, "Block", toDate));
                } else {
                    cumulativesummaryReportStart.addAll(getWACumulativeSummary(blockId, "Panchayat", fromDate));
                    cumulativesummaryReportEnd.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate));
                }
            }


        }

        for (int i = 0; i < cumulativesummaryReportEnd.size(); i++) {
            boolean notAvailable = true;
            for (int j = 0; j < cumulativesummaryReportStart.size(); j++) {

                if (cumulativesummaryReportEnd.get(i).getLocationId().equals(cumulativesummaryReportStart.get(j).getLocationId())) {
                    WACumulativeSummary a = cumulativesummaryReportEnd.get(i);
                    WACumulativeSummary b = cumulativesummaryReportStart.get(j);
                    WASubscriberDto summaryDto1 = new WASubscriberDto();
                    summaryDto1.setId(a.getId());
                    summaryDto1.setLocationId(a.getLocationId());
                    summaryDto1.setSwachchagrahisCompleted(a.getSwachchagrahisCompleted() - b.getSwachchagrahisCompleted());
                    summaryDto1.setSwachchagrahisFailed(a.getSwachchagrahisFailed() - b.getSwachchagrahisFailed());
                    summaryDto1.setSwachchagrahisRegistered(a.getSwachchagrahisRegistered() - b.getSwachchagrahisRegistered());
                    summaryDto1.setSwachchagrahisNotStarted(a.getSwachchagrahisNotStarted() - b.getSwachchagrahisNotStarted());
                    summaryDto1.setSwachchagrahisStarted(a.getSwachchagrahisStarted() - b.getSwachchagrahisStarted());
                    summaryDto1.setRecordsRejected(a.getSwachchagrahisRejected() - b.getSwachchagrahisRejected());
                    summaryDto1.setLocationType(a.getLocationType());
                    summaryDto1.setRegisteredNotCompletedStart(b.getSwachchagrahisRegistered() - b.getSwachchagrahisCompleted());
                    summaryDto1.setRegisteredNotCompletedEnd(a.getSwachchagrahisRegistered() - a.getSwachchagrahisCompleted());
                    summaryDto1.setRecordsReceived((a.getSwachchagrahisRegistered() + a.getSwachchagrahisRejected()) - (b.getSwachchagrahisRejected() + b.getSwachchagrahisRegistered()));
                    String locationType = a.getLocationType();
                    if (locationType.equalsIgnoreCase("State")) {
                        summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                    }
                    if (locationType.equalsIgnoreCase("District")) {
                        summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                    }
                    if (locationType.equalsIgnoreCase("Block")) {
                        summaryDto1.setLocationName(blockDao.findByBlockId(a.getLocationId().intValue()).getBlockName());
                    }
                    if (locationType.equalsIgnoreCase("Panchayat")) {
                        summaryDto1.setLocationName(panchayatDao.findByPanchayatId(a.getLocationId().intValue()).getPanchayatName());
                    }
                    if (locationType.equalsIgnoreCase("DifferenceState")) {
                        summaryDto1.setLocationName("No District Count");
                        summaryDto1.setLocationId((long) -1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        summaryDto1.setLocationName("No Block Count");
                        summaryDto1.setLocationId((long) -1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        summaryDto1.setLocationName("No Panchayat Count");
                        summaryDto1.setLocationId((long) -1);

                    }
                    notAvailable = false;
                    if (summaryDto1.getSwachchagrahisCompleted() + summaryDto1.getSwachchagrahisStarted() + summaryDto1.getSwachchagrahisFailed() + summaryDto1.getRecordsRejected()
                            + summaryDto1.getSwachchagrahisRegistered() + summaryDto1.getRegisteredNotCompletedEnd()
                            + summaryDto1.getRegisteredNotCompletedStart() + summaryDto1.getRecordsReceived() + summaryDto1.getSwachchagrahisNotStarted() != 0) {
                        summaryDto.add(summaryDto1);
                    }

                }
            }
        }
        
        aggregateResponseDto.setTableData(summaryDto);
        return aggregateResponseDto;
    }

    @Override
    public AggregateResponseDto getWACumulativeSummaryReport(Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<AggregateCumulativeWADto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReport = new ArrayList<>();

        if (stateId == 0) {
            cumulativeSummaryReport.addAll(getWACumulativeSummary(0, "State", toDate));
        } else {
            if (districtId == 0) {
                cumulativeSummaryReport.addAll(getWACumulativeSummary(stateId, "District", toDate));
            } else {
                if (blockId == 0) {
                    cumulativeSummaryReport.addAll(getWACumulativeSummary(districtId, "Block", toDate));
                } else {
                    cumulativeSummaryReport.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate));
                }
            }
        }

        for (WACumulativeSummary a : cumulativeSummaryReport) {
            AggregateCumulativeWADto summaryDto1 = new AggregateCumulativeWADto();
            summaryDto1.setId(a.getId());
            summaryDto1.setLocationId(a.getLocationId());
            summaryDto1.setSwachchagrahisCompleted(a.getSwachchagrahisCompleted());
            summaryDto1.setSwachchagrahisFailed(a.getSwachchagrahisFailed());
            summaryDto1.setSwachchagrahisRegistered(a.getSwachchagrahisRegistered());
            summaryDto1.setSwachchagrahisNotStarted(a.getSwachchagrahisNotStarted());
            summaryDto1.setSwachchagrahisStarted(a.getSwachchagrahisStarted());
            summaryDto1.setLocationType(a.getLocationType());
            summaryDto1.setCompletedPercentage((float) (a.getSwachchagrahisStarted() == 0 ? 0 : (a.getSwachchagrahisCompleted() * 10000 / a.getSwachchagrahisStarted())) / 100);
            summaryDto1.setFailedpercentage((float) (a.getSwachchagrahisStarted() == 0 ? 0 : (a.getSwachchagrahisFailed() * 10000 / a.getSwachchagrahisStarted())) / 100);
            summaryDto1.setNotStartedpercentage((float) (a.getSwachchagrahisRegistered() == 0 ? 0 : (a.getSwachchagrahisNotStarted() * 10000 / a.getSwachchagrahisRegistered())) / 100);
            String locationType = a.getLocationType();
            if (locationType.equalsIgnoreCase("State")) {
                summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
            }
            if (locationType.equalsIgnoreCase("District")) {
                summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
            }
            if (locationType.equalsIgnoreCase("Block")) {
                summaryDto1.setLocationName(blockDao.findByBlockId(a.getLocationId().intValue()).getBlockName());
            }
            if (locationType.equalsIgnoreCase("Panchayat")) {
                summaryDto1.setLocationName(panchayatDao.findByPanchayatId(a.getLocationId().intValue()).getPanchayatName());
            }
            if (locationType.equalsIgnoreCase("DifferenceState")) {
                summaryDto1.setLocationName("No District");
                summaryDto1.setLocationId((long) -1);

            }
            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                summaryDto1.setLocationName("No Block");
                summaryDto1.setLocationId((long) -1);

            }
            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                summaryDto1.setLocationName("No Panchayat");
                summaryDto1.setLocationId((long) -1);

            }

            if (a.getId() != 0) {
                summaryDto.add(summaryDto1);
            }

        }
        
        aggregateResponseDto.setTableData(summaryDto);
        return aggregateResponseDto;
    }

    @Override
    public AggregateResponseDto getWAAnonymousSummaryReport(Date fromDate,Date toDate,Integer circleId,Integer stateId,Integer districtId,Integer blockId) {

        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WAAnonymousPerformanceDto> summaryDto = new ArrayList<>();
        List<WAAnonymousUsersSummary> cumulativeSummaryReportStart = new ArrayList<>();
        List<WAAnonymousUsersSummary> cumulativeSummaryReportEnd = new ArrayList<>();


        cumulativeSummaryReportStart.addAll(getWAAnonymousCumulativeSummary(circleId,fromDate));
        cumulativeSummaryReportEnd.addAll(getWAAnonymousCumulativeSummary(circleId,toDate));

        for (int i = 0; i < cumulativeSummaryReportEnd.size(); i++) {
            for (int j = 0; j < cumulativeSummaryReportStart.size(); j++) {
                if (cumulativeSummaryReportEnd.get(i).getCircleId().equals(cumulativeSummaryReportStart.get(j).getCircleId())) {
                    WAAnonymousUsersSummary a = cumulativeSummaryReportEnd.get(i);
                    WAAnonymousUsersSummary b = cumulativeSummaryReportStart.get(j);
                    WAAnonymousPerformanceDto summaryDto1 = new WAAnonymousPerformanceDto();
                    summaryDto1.setId(a.getId());
                    summaryDto1.setCircleName(a.getCircleName());
                    summaryDto1.setCircleId(a.getCircleId());
                    summaryDto1.setAnonUsersCompletedCourse(a.getAnonymousUsersCompletedCourse() - b.getAnonymousUsersCompletedCourse());
                    summaryDto1.setAnonUsersStartedCourse(a.getAnonymousUsersStartedCourse() - b.getAnonymousUsersStartedCourse());
                    summaryDto1.setAnonUsersFailedCourse(waAnonymousSummaryService.getAnonUsersFailed(summaryDto1.getCircleId(), fromDate, toDate));
                    summaryDto1.setAnonUsersPursuingCourse(waAnonymousSummaryService.getAccessedCount(summaryDto1.getCircleId(), fromDate, toDate));
                    summaryDto1.setAnonUsersNotPursuingCourse(waAnonymousSummaryService.getNotAccessedcount(summaryDto1.getCircleId(), fromDate, toDate));
                    if (summaryDto1.getAnonUsersCompletedCourse() + summaryDto1.getAnonUsersFailedCourse() +
                            summaryDto1.getAnonUsersStartedCourse() + summaryDto1.getAnonUsersPursuingCourse() +
                            summaryDto1.getAnonUsersNotPursuingCourse() != 0) {
                        summaryDto.add(summaryDto1);
                    }
                }
            }
        }
        aggregateResponseDto.setTableData(summaryDto);
        return aggregateResponseDto;
    }
}

