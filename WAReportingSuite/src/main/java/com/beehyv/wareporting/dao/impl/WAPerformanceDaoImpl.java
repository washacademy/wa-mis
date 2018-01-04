package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WAPerformanceDao;
import com.beehyv.wareporting.model.FrontLineWorkers;
import com.beehyv.wareporting.model.User;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.beehyv.wareporting.utils.ServiceFunctions.dateAdder;

/**
 * Created by beehyv on 22/9/17.
 */
@Repository("waPerformanceDao")
public class WAPerformanceDaoImpl extends AbstractDao<Integer, User> implements WAPerformanceDao {

    @Override
    public Long accessedNotOnce(Integer locationId, String locationType, Date fromDate, Date toDate){

//        Criteria criteria = getSession().createCriteria(FrontLineWorkers.class);
//        List<FrontLineWorkers> frontLineWorkersList = new ArrayList<>();
//
//        if(locationType.equalsIgnoreCase("State")){
//            criteria.add(Restrictions.lt("state",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("District")){
//            criteria.add(Restrictions.lt("district",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("Block")){
//            criteria.add(Restrictions.lt("block",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("Panchayat")){
//            criteria.add(Restrictions.lt("panchayat",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//
//        Query query = getSession().createSQLQuery("select f from FrontLineWorkers f where f.flwId in (select m.flwId from WACallDetailMeasure m where m.startTime between "+fromDate+" AND "+toDate+" ) AND f.state= "+locationId+
//        "AND NOT IN (select c.flwId from WACourseFirstCompletion c where c.firstCompletionDate <= "+fromDate);
//

//        Query query = getSession().createSQLQuery("select f from FrontLineWorkers f where f.flwId in (select DISTINCT m.flwId from WACallDetailMeasure m where m.startTime between "+fromDate+" AND "+toDate+" ) AND f.state= "+locationId+
//                "AND f.courseStartDate <"+ fromDate +" AND (firstCompletionDate > "+fromDate+" OR firstCompletionDate is NULL)");
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.state_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.district_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.block_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("panchayat")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.healthsubfacility_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }

        return (long)0;
    }

    @Override
    public Long accessedAtLeastOnce(Integer locationId, String locationType, Date fromDate, Date toDate){
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.state_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.district_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.block_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("panchayat")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'SWACHCHAGRAHI' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.healthsubfacility_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();

        }

        return (long)0;
    }

    @Override
    public Integer getSwachchagrahisFailed(Integer locationId, String locationType, Date fromDate, Date toDate){
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'SWACHCHAGRAHI' and f.state_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.last_modified between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.last_modified < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'SWACHCHAGRAHI' and f.district_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.last_modified between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.last_modified < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'SWACHCHAGRAHI' and f.block_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.last_modified between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.last_modified < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("panchayat")) {
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'SWACHCHAGRAHI' and f.healthsubfacility_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.last_modified between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.last_modified < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();

        }

        return 0;
    }

}
