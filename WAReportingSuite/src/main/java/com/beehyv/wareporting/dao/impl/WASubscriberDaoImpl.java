package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.WASubscriberDao;
import com.beehyv.wareporting.model.WACumulativeSummary;
import com.beehyv.wareporting.model.WAAggregateDaily;
import com.beehyv.wareporting.model.User;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.hibernate.criterion.Restrictions;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 20/9/17.
 */
@Repository("waSubscriberDao")
public class WASubscriberDaoImpl extends AbstractDao<Integer, User>  implements WASubscriberDao {
    @Override
    public List<Object> getWASubscriberCountDaily(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(WAAggregateDaily.class);

        return null;
    }

    @Override
    public List<WACumulativeSummary> getWASubscriberCountSummary(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(WACumulativeSummary.class);

        if(locationId == 0){
            criteria.add(Restrictions.and(
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate)),
                    Restrictions.eq("locationType",locationType)
            ));
            return (List<WACumulativeSummary>) criteria.list();
        }
        else{
            criteria.add(Restrictions.and(
                    Restrictions.eq("locationId",locationId.longValue()),
                    Restrictions.eq("locationType",locationType),
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate))
            ));
            return (List<WACumulativeSummary>) criteria.list();
        }
    }

//    @Override
//    public List<WACumulativeSummary> getWASubscriberCountSummaryEnd(String locationType, Integer locationId, Date fromDate, Date toDate){
//
//        Criteria criteria = getSession().createCriteria(WACumulativeSummary.class);
//
//        if(locationId == 0){
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("date",toDate),
//                    Restrictions.eq("locationType",locationType)
//            ));
//            return (List<WACumulativeSummary>) criteria.list();
//        }
//        else{
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("locationId",locationId.longValue()),
//                    Restrictions.eq("locationType",locationType),
//                    Restrictions.eq("date",toDate)
//            ));
//            return (List<WACumulativeSummary>) criteria.list();
//        }
//
//    }
}
