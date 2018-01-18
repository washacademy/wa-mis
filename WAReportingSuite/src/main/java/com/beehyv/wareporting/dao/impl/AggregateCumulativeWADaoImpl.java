package com.beehyv.wareporting.dao.impl;

import com.beehyv.wareporting.dao.AbstractDao;
import com.beehyv.wareporting.dao.AggregateCumulativeWADao;
import com.beehyv.wareporting.model.WACumulativeSummary;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("aggregateCumulativeWADao")
public class  AggregateCumulativeWADaoImpl extends AbstractDao<Integer,WACumulativeSummary> implements AggregateCumulativeWADao
        {
            @Override
            public WACumulativeSummary getWACumulativeSummary(Integer locationId, String locationType, Date toDate){


                   Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
                   criteria.add(Restrictions.and(
                           Restrictions.eq("locationId",locationId.longValue()),
                           Restrictions.eq("locationType",locationType),
                           Restrictions.eq("date",toDate)
                   ));
                if(criteria.list().isEmpty()){
                    WACumulativeSummary WACumulativeSummary1 = new WACumulativeSummary(0,(long)0,"NA",(long)0,"",0,0,0,0,0,0);
                    return WACumulativeSummary1;
                }
                
                WACumulativeSummary WACumulativeSummary1 = (WACumulativeSummary)criteria.list().get(0);
                WACumulativeSummary1.setSwachchagrahisRejected(WACumulativeSummary1.getSwachchagrahisRejected() == null?0: WACumulativeSummary1.getSwachchagrahisRejected());
                WACumulativeSummary1.setSwachchagrahisStarted(WACumulativeSummary1.getSwachchagrahisStarted() == null?0: WACumulativeSummary1.getSwachchagrahisStarted());
                WACumulativeSummary1.setSwachchagrahisRegistered(WACumulativeSummary1.getSwachchagrahisRegistered() == null?0: WACumulativeSummary1.getSwachchagrahisRegistered());
                WACumulativeSummary1.setSwachchagrahisCompleted(WACumulativeSummary1.getSwachchagrahisCompleted() == null?0: WACumulativeSummary1.getSwachchagrahisCompleted());
                WACumulativeSummary1.setSwachchagrahisFailed(WACumulativeSummary1.getSwachchagrahisFailed() == null?0: WACumulativeSummary1.getSwachchagrahisFailed());
                WACumulativeSummary1.setSwachchagrahisNotStarted(WACumulativeSummary1.getSwachchagrahisNotStarted() == null?0: WACumulativeSummary1.getSwachchagrahisNotStarted());


                   return WACumulativeSummary1;


            }
        }
