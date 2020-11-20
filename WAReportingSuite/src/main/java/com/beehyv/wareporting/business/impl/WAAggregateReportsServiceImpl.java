package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.WAAggregateReportsService;

import com.beehyv.wareporting.business.WAAnonymousSummaryService;
import com.beehyv.wareporting.business.WAPerformanceService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.*;
import com.beehyv.wareporting.model.*;
import com.beehyv.wareporting.utils.Constants;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibm.icu.text.NumberFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

import static com.beehyv.wareporting.utils.ServiceFunctions.dateAdder;
import static java.lang.Double.parseDouble;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("waAggregateReportsService")
@Transactional
public class WAAggregateReportsServiceImpl implements WAAggregateReportsService {

    private Logger logger = LoggerFactory.getLogger(WAAggregateReportsServiceImpl.class);

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

    private List<WACumulativeSummary> getWACumulativeSummary(Integer locationId, String locationType, Date toDate, Integer courseId){
        List<WACumulativeSummary> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getAllStates();
            for(State s:states){
                CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(s.getStateId(),locationType,toDate,courseId));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                WACumulativeSummary stateCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"State",toDate,courseId);
                Integer swachchagrahisRegistered = 0;
                Integer swachchagrahisStarted = 0;
                Integer swachchagrahisNotStarted = 0;
                Integer swachchagrahisCompleted = 0;
                Integer swachchagrahisFailed = 0;
                Integer swachchagrahisRejected = 0;
                for(District d:districts){
                    WACumulativeSummary districtCount = aggregateCumulativeWADao.getWACumulativeSummary(d.getDistrictId(),locationType,toDate,courseId);
                    CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(d.getDistrictId(),locationType,toDate,courseId));
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
                    WACumulativeSummary districtCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"District",toDate, courseId);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for (Block d : blocks) {
                        WACumulativeSummary blockCount = aggregateCumulativeWADao.getWACumulativeSummary(d.getBlockId(),locationType,toDate, courseId);
                        CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(d.getBlockId(), locationType,toDate, courseId));
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
                    WACumulativeSummary blockCounts = aggregateCumulativeWADao.getWACumulativeSummary(locationId,"block",toDate, courseId);
                    Integer swachchagrahisRegistered = 0;
                    Integer swachchagrahisStarted = 0;
                    Integer swachchagrahisNotStarted = 0;
                    Integer swachchagrahisCompleted = 0;
                    Integer swachchagrahisFailed = 0;
                    Integer swachchagrahisRejected = 0;
                    for(Panchayat s: panchayats){
                        WACumulativeSummary panchayatCount = aggregateCumulativeWADao.getWACumulativeSummary(s.getPanchayatId(),locationType,toDate, courseId);
                        CumulativeSummary.add(aggregateCumulativeWADao.getWACumulativeSummary(s.getPanchayatId(), locationType,toDate, courseId));
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

    private List<WAAnonymousUsersSummary> getWAAnonymousCumulativeSummary(Integer circleId,Date toDate, Integer courseId){
        List<WAAnonymousUsersSummary> CumulativeSummary = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(circleId == 0) {
            List<Circle> circles = circleDao.getAllCircles();
            for (Circle circle : circles){
                CumulativeSummary.add(waAnonymousUsersCumulativeDao.getWAAnonymousCumulativeSummary(circle.getCircleId(), toDate, courseId));
            }
            return CumulativeSummary;
        }
        CumulativeSummary.add(waAnonymousUsersCumulativeDao.getWAAnonymousCumulativeSummary(circleId, toDate, courseId));

        return CumulativeSummary;
    }

    @Override
    public AggregateResponseDto getWAPerformanceReport(Date fromDate,Date toDate,Integer circleId,Integer stateId,Integer districtId,Integer blockId, Integer courseId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WAPerformanceDto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReportStart = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReportEnd = new ArrayList<>();

        if (stateId == 0) {
            cumulativeSummaryReportStart.addAll(getWACumulativeSummary(0, "State", fromDate, courseId));
            cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(0, "State", toDate, courseId));
        } else {
            if (districtId == 0) {
                cumulativeSummaryReportStart.addAll(getWACumulativeSummary(stateId, "District", fromDate, courseId));
                cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(stateId, "District", toDate, courseId));
            } else {
                if (blockId == 0) {
                    cumulativeSummaryReportStart.addAll(getWACumulativeSummary(districtId, "Block", fromDate, courseId));
                    cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(districtId, "Block", toDate, courseId));

                } else {
                    cumulativeSummaryReportStart.addAll(getWACumulativeSummary(blockId, "Panchayat", fromDate, courseId));
                    cumulativeSummaryReportEnd.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate, courseId));

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
                    summaryDto1.setSwachchagrahisFailedCourse(waPerformanceService.getSwachchagrahisFailed(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate, courseId));
                    summaryDto1.setSwachchagrahisPursuingCourse(waPerformanceService.getAccessedCount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate, courseId));
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
    public AggregateResponseDto getWASubscriberReport(Date fromDate, Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId, Integer courseId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WASubscriberDto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativesummaryReportStart = new ArrayList<>();
        List<WACumulativeSummary> cumulativesummaryReportEnd = new ArrayList<>();

        if (stateId == 0) {
            cumulativesummaryReportStart.addAll(getWACumulativeSummary(0, "State", fromDate, courseId));
            cumulativesummaryReportEnd.addAll(getWACumulativeSummary(0, "State", toDate, courseId));
        } else {
            if (districtId == 0) {
                cumulativesummaryReportStart.addAll(getWACumulativeSummary(stateId, "District", fromDate, courseId));
                cumulativesummaryReportEnd.addAll(getWACumulativeSummary(stateId, "District", toDate, courseId));
            } else {
                if (blockId == 0) {
                    cumulativesummaryReportStart.addAll(getWACumulativeSummary(districtId, "Block", fromDate, courseId));
                    cumulativesummaryReportEnd.addAll(getWACumulativeSummary(districtId, "Block", toDate, courseId));
                } else {
                    cumulativesummaryReportStart.addAll(getWACumulativeSummary(blockId, "Panchayat", fromDate, courseId));
                    cumulativesummaryReportEnd.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate, courseId));
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
    public AggregateResponseDto getWACumulativeSummaryReport(Date toDate, Integer circleId, Integer stateId, Integer districtId, Integer blockId, Integer courseId) {
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<AggregateCumulativeWADto> summaryDto = new ArrayList<>();
        List<WACumulativeSummary> cumulativeSummaryReport = new ArrayList<>();

        if (stateId == 0) {
            cumulativeSummaryReport.addAll(getWACumulativeSummary(0, "State", toDate, courseId));
        } else {
            if (districtId == 0) {
                cumulativeSummaryReport.addAll(getWACumulativeSummary(stateId, "District", toDate, courseId));
            } else {
                if (blockId == 0) {
                    cumulativeSummaryReport.addAll(getWACumulativeSummary(districtId, "Block", toDate, courseId));
                } else {
                    cumulativeSummaryReport.addAll(getWACumulativeSummary(blockId, "Panchayat", toDate, courseId));
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
            summaryDto1.setFailedPercentage((float) (a.getSwachchagrahisStarted() == 0 ? 0 : (a.getSwachchagrahisFailed() * 10000 / a.getSwachchagrahisStarted())) / 100);
            summaryDto1.setNotStartedPercentage((float) (a.getSwachchagrahisRegistered() == 0 ? 0 : (a.getSwachchagrahisNotStarted() * 10000 / a.getSwachchagrahisRegistered())) / 100);
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
    public AggregateResponseDto getWAAnonymousSummaryReport(Date fromDate,Date toDate,Integer circleId, Integer courseId) {

        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();
        List<WAAnonymousPerformanceDto> summaryDto = new ArrayList<>();
        List<WAAnonymousUsersSummary> cumulativeSummaryReportStart = new ArrayList<>();
        List<WAAnonymousUsersSummary> cumulativeSummaryReportEnd = new ArrayList<>();


        cumulativeSummaryReportStart.addAll(getWAAnonymousCumulativeSummary(circleId,fromDate, courseId));
        cumulativeSummaryReportEnd.addAll(getWAAnonymousCumulativeSummary(circleId,toDate, courseId));

        for (int i = 0; i < cumulativeSummaryReportEnd.size(); i++) {
            for (int j = 0; j < cumulativeSummaryReportStart.size(); j++) {
                if (cumulativeSummaryReportEnd.get(i).getCircleId().equals(cumulativeSummaryReportStart.get(j).getCircleId())) {
                    WAAnonymousUsersSummary a = cumulativeSummaryReportEnd.get(i);
                    WAAnonymousUsersSummary b = cumulativeSummaryReportStart.get(j);

                    Date firstStartDate = b.getDate();
                    Date firstEndDate = a.getDate();

                    WAAnonymousPerformanceDto summaryDto1 = new WAAnonymousPerformanceDto();
                    summaryDto1.setId(a.getId());
                    summaryDto1.setCircleName(a.getCircleName());
                    summaryDto1.setCircleId(a.getCircleId());

                    if (compareDates(firstStartDate, fromDate) >0 && compareDates(firstEndDate, toDate) > 0){
                        summaryDto1.setAnonUsersStartedCourse(0);
                        summaryDto1.setAnonUsersCompletedCourse(0);
                    }
                    else if(compareDates(firstStartDate, fromDate) >0){
                        summaryDto1.setAnonUsersStartedCourse(a.getAnonymousUsersStartedCourse());
                        summaryDto1.setAnonUsersCompletedCourse(a.getAnonymousUsersCompletedCourse());
                    }
                    else {
                        summaryDto1.setAnonUsersStartedCourse(a.getAnonymousUsersStartedCourse() - b.getAnonymousUsersStartedCourse());
                        summaryDto1.setAnonUsersCompletedCourse(a.getAnonymousUsersCompletedCourse() - b.getAnonymousUsersCompletedCourse());
                    }
                    summaryDto1.setAnonUsersFailedCourse(waAnonymousSummaryService.getAnonUsersFailed(summaryDto1.getCircleId(), fromDate, toDate, courseId));
                    summaryDto1.setAnonUsersPursuingCourse(waAnonymousSummaryService.getAccessedCount(summaryDto1.getCircleId(), fromDate, toDate, courseId));
                    summaryDto1.setAnonUsersNotPursuingCourse(waAnonymousSummaryService.getNotAccessedcount(summaryDto1.getCircleId(), fromDate, toDate, courseId));
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

    @Override
    public void createSpecificAggreagateExcel(XSSFWorkbook workbook, AggregateExcelDto gridData) {


        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.protectSheet("123");

        XSSFCellStyle backgroundStyle = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle1 = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle2 = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle3 = workbook.createCellStyle();

        backgroundStyle1.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle1.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle1.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle1.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle1.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle1.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle1.setTopBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle1.setWrapText(true);
        backgroundStyle1.setLocked(false);

        backgroundStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(243, 243, 243)));
        backgroundStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle2.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle2.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle2.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle2.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle2.setTopBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle2.setWrapText(true);
        backgroundStyle2.setLocked(false);

        backgroundStyle3.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle3.setFillForegroundColor(new XSSFColor(new java.awt.Color(233, 233, 233)));
        backgroundStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle3.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle3.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle3.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle3.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle3.setTopBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setWrapText(true);
        backgroundStyle3.setLocked(false);

        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setTopBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setWrapText(true);


        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        backgroundStyle.setFont(font);

        Font font1 = workbook.createFont();
        font1.setFontName(HSSFFont.FONT_ARIAL);
        backgroundStyle1.setFont(font1);
        backgroundStyle2.setFont(font1);

        Font font2 = workbook.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setFont(font2);//set it to bold
        style.setWrapText(true);
        backgroundStyle3.setFont(font2);

    /** PLEASE DONT CHANGE COLUMN WIDTH IT IS ADJUSTED FOR IMAGE HEADERS **/
        spreadsheet.autoSizeColumn(0);
        for (int i = 1; i < 5; i++) {
            spreadsheet.setColumnWidth(i, 6000);
        }
        for (int i = 5; i < 12; i++) {
            spreadsheet.setColumnWidth(i, 4620);
        }


        XSSFRow row;
//        for (int i =0;i<4;i++){
//            row = spreadsheet.createRow(i);
//            row.setHeight((short) 1480);
//        }
        int rowid = 8;

        row = spreadsheet.createRow(rowid++);
        row.setHeight((short) 1400);
        int colid = 0;
        int tabrow = 0;
        for (String header : gridData.getColumnHeaders()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(header);
            cell1.setCellStyle(backgroundStyle);
        }

        for (ArrayList<String> rowData : gridData.getReportData()) {
            row = spreadsheet.createRow(rowid++);
            colid = 0;
            Cell SNrow = row.createCell(colid++);
            SNrow.setCellValue(tabrow + 1);
            if (tabrow % 2 == 0) {
                SNrow.setCellStyle(backgroundStyle1);
            } else {
                SNrow.setCellStyle(backgroundStyle2);
            }

            for (int i =1;i<rowData.size(); i++) {
                Cell cell1 = row.createCell(colid++);
                try {
                    cell1.setCellValue(rowData.get(i));
                } catch (NumberFormatException e) {
                    e.printStackTrace(); //prints error
                    logger.error("Error while parsing double ", e);
                }


                if (tabrow % 2 == 0) {

                    cell1.setCellStyle(backgroundStyle1);

                } else {

                    cell1.setCellStyle(backgroundStyle2);

                }

            }
            tabrow++;
        }
        rowid = 9 + gridData.getReportData().size() ;
        row = spreadsheet.createRow(rowid);
        colid = 0;

        for (String footer : gridData.getColunmFooters()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(footer);
            cell1.setCellStyle(backgroundStyle3);
        }





//        if (gridData.getReportName().equalsIgnoreCase("MA Subscriber")) {
//           List rejectedAshas = maSubscriberDao.getRejectedAshas();
//        }



//            if(gridData.getReportName().equalsIgnoreCase("MA Subscriber") ||
//                    gridData.getReportName().equalsIgnoreCase("MA Performance")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Call")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Usage")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Aggregate Beneficiaries")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Beneficiary Completion")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Message Listenership")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Cumulative Summary")||
//                    gridData.getReportName().equalsIgnoreCase("MA Cumulative Summary")){

//            }

        createHeadersForAggreagateExcels(workbook, gridData);
    }

    private void createHeadersForAggreagateExcels(XSSFWorkbook workbook, AggregateExcelDto gridData) {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);
        String reportName = gridData.getReportName();
        String circle = gridData.getCircleFullName();



        String encodingPrefix = "base64,";
        String pngImageURL = Constants.header_Pradan_agg_base64;
        int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array

        final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);

        final CreationHelper helper = workbook.getCreationHelper();
        final Drawing drawing = spreadsheet.createDrawingPatriarch();

        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);

        anchor.setCol1(0);
        anchor.setRow1(0);
        anchor.setRow2(4);
        anchor.setCol2(8);
        drawing.createPicture(anchor, pictureIndex);

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 7));

        rowid = rowid + 3;
        XSSFRow row = spreadsheet.createRow(rowid++);
        XSSFCellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(5);
        Cell cell4 = row.createCell(6);

        CellRangeAddress range1 = new CellRangeAddress(4, 5, 0, 0);
        cleanBeforeMergeOnValidCells(spreadsheet, range1, style);
        spreadsheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(4, 5, 1, 4);
        cleanBeforeMergeOnValidCells(spreadsheet, range2, style);
        spreadsheet.addMergedRegion(range2);
        CellRangeAddress range3 = new CellRangeAddress(4, 5, 5, 5);
        cleanBeforeMergeOnValidCells(spreadsheet, range3, style);
        spreadsheet.addMergedRegion(range3);
        CellRangeAddress range4 = new CellRangeAddress(4, 5, 6, 7);
        cleanBeforeMergeOnValidCells(spreadsheet, range4, style);
        spreadsheet.addMergedRegion(range4);
        XSSFRow row1 = spreadsheet.createRow(++rowid);
        Cell cell5 = row1.createCell(0);
        Cell cell6 = row1.createCell(1);
        Cell cell7 = row1.createCell(3);
        Cell cell8 = row1.createCell(4);
        Cell cell9 = row1.createCell(6);
        Cell cell10 = row1.createCell(7);
        cell1.setCellValue("Report:");
        cell2.setCellValue(gridData.getReportName());

        cell3.setCellValue("Period:");
        cell4.setCellValue(gridData.getTimePeriod());

        if (reportName.equals("Anonymous Users Summary Report")){

            cell5.setCellValue("Circle:");
            cell6.setCellValue(circle);
        }
        else {
            cell5.setCellValue("State:");
            cell6.setCellValue(gridData.getStateName());

            cell7.setCellValue("District:");
            cell8.setCellValue(gridData.getDistrictName());

            cell9.setCellValue("Block:");
            cell10.setCellValue(gridData.getBlockName());
        }

        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);

        CellRangeAddress range5 = new CellRangeAddress(6, 6, 1, 2);
        cleanBeforeMergeOnValidCells(spreadsheet, range5, style);
        spreadsheet.addMergedRegion(range5);
        CellRangeAddress range6 = new CellRangeAddress(6, 6, 4, 5);
        cleanBeforeMergeOnValidCells(spreadsheet, range6, style);
        spreadsheet.addMergedRegion(range6);
        CellRangeAddress range7 = new CellRangeAddress(6, 6, 7, 7);
        cleanBeforeMergeOnValidCells(spreadsheet, range7, style);
        spreadsheet.addMergedRegion(range7);

        XSSFRow row3 = spreadsheet.createRow(7);
        Cell cellA = row3.createCell(4);
        cellA.setCellValue("Date Filed");
        cellA.setCellStyle(style);
        Cell cellB = row3.createCell(5);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int DateValue = calendar.get(Calendar.DATE);
        int DateYear = (calendar.get(Calendar.YEAR));
        String DateString;
        if (DateValue < 10) {
            DateString = "0" + String.valueOf(DateValue);
        } else {
            DateString = String.valueOf(DateValue);
        }
        String MonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        String YearString = String.valueOf(DateYear);

        cellB.setCellValue(DateString + " " + MonthString + " " + YearString);
        CellRangeAddress dateRange = new CellRangeAddress(7, 7, 5, 7);
        cleanBeforeMergeOnValidCells(spreadsheet, dateRange, style);
        spreadsheet.addMergedRegion(dateRange);

        Cell cell11 = row3.createCell(0);
        Cell cell12 = row3.createCell(1);
        cell11.setCellValue("Course:");
        cell12.setCellValue(gridData.getCourse());
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        CellRangeAddress courseRange = new CellRangeAddress(7, 7, 1, 3);
        cleanBeforeMergeOnValidCells(spreadsheet, courseRange, style);
        spreadsheet.addMergedRegion(courseRange);



    }

    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet, CellRangeAddress region, XSSFCellStyle cellStyle) {
        for (int rowNum = region.getFirstRow(); rowNum <= region.getLastRow(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            for (int colNum = region.getFirstColumn(); colNum <= region.getLastColumn(); colNum++) {
                XSSFCell currentCell = row.getCell(colNum);
                if (currentCell == null) {
                    currentCell = row.createCell(colNum);

                }

                currentCell.setCellStyle(cellStyle);

            }
        }

    }


    public static  int compareDates(Date d1, Date d2){
        if (d1 == null || d2 == null){
            return  1;
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(d1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        d1 = cal.getTime();

        cal.setTime(d2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        d2 = cal.getTime();

        return d1.compareTo(d2);

    }
}

