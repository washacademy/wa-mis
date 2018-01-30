package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WAAnonymousSummaryDao;
import com.beehyv.wareporting.model.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;

@Repository("waAnonymousSummaryDao")
public class WAAnonymousSummaryDaoImpl extends AbstractDao<Integer, User> implements WAAnonymousSummaryDao {

    @Override
    public Long accessedNotOnce(Integer circleId, Date fromDate, Date toDate){
        Query query = getSession().createSQLQuery("select count(*) from swachchagrahi f  " +
                "where f.swc_id not in  (select distinct m.swc_id  from WA_course_completion m  where m.has_passed = 1 and m.creation_date < :fromDate)  " +
                "and f.swc_id not in  (select distinct m1.swc_id from WA_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                "and f.swc_id in (select distinct m2.swc_id from WA_call_detail_measure m2 where m2.start_time < :fromDate) " +
                "and f.swc_designation = 'SWACHCHAGRAHI' and f.course_status = 'ANONYMOUS' and f.job_status = 'ACTIVE' and f.circle_id = :circleId");
        query.setParameter("fromDate",fromDate);
        query.setParameter("toDate",toDate);
        query.setParameter("circleId",circleId);
        return ((BigInteger) query.uniqueResult()).longValue();
    }

    @Override
    public Long accessedAtLeastOnce(Integer circleId, Date fromDate, Date toDate){
            Query query = getSession().createSQLQuery("select count(*) from swachchagrahi f  " +
                    "where f.swc_id not in  (select distinct m.swc_id  from WA_course_completion m  where m.has_passed = 1 and m.creation_date < :fromDate)  " +
                    "and f.swc_id in  (select distinct m1.swc_id from WA_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.swc_id in (select distinct m2.swc_id from WA_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.swc_designation = 'SWACHCHAGRAHI' and f.course_status = 'ANONYMOUS' and f.job_status = 'ACTIVE' and f.circle_id = :circleId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("circleId",circleId);
            return ((BigInteger) query.uniqueResult()).longValue();
    }

    @Override
    public Integer getAnonUsersFailed(Integer circleId, Date fromDate, Date toDate){
            Query query = getSession().createSQLQuery("select count(distinct f.swc_id) from swachchagrahi f where f.swc_designation = 'SWACHCHAGRAHI' " +
                    "and f.course_status = 'ANONYMOUS' and f.circle_id = :circleId AND "+
                    "f.swc_id in (select distinct m.swc_id from WA_course_completion m where m.has_passed = 0 and (m.last_modified between :fromDate AND :toDate)) and " +
                    "f.swc_id not in (select distinct m1.swc_id from WA_course_completion m1 where m1.has_passed = 1 and m1.last_modified < :toDate)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("circleId",circleId);
            return ((BigInteger) query.uniqueResult()).intValue();
    }

}
