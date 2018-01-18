package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.WAAnonymousSummaryService;
import com.beehyv.wareporting.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service("waAnonymousSummaryService")
@Transactional
public class WAAnonymousSummaryServiceImpl implements WAAnonymousSummaryService {

    @Autowired
    private WAAnonymousSummaryDao waAnonymousSummaryDao;

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
    public Long getAccessedCount(Integer circleId, Date fromDate, Date toDate){

        Long count = (long)0;
        count =  waAnonymousSummaryDao.accessedAtLeastOnce(circleId,fromDate,toDate);
        return count;
    }


    @Override
    public Long getNotAccessedcount(Integer circleId, Date fromDate, Date toDate){

        Long count = (long)0;
        count =  waAnonymousSummaryDao.accessedNotOnce(circleId,fromDate,toDate);
        return count;
    }

    @Override
    public Integer getAnonUsersFailed(Integer circleId, Date fromDate, Date toDate){

        Integer count = 0;
        count =  waAnonymousSummaryDao.getAnonUsersFailed(circleId,fromDate,toDate);
        return count;
    }

}
