package com.mythredz.facebook;

import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.Facebookpendingreferredby;

/**
 * User: Joe Reger Jr
 * Date: Oct 17, 2007
 * Time: 1:40:09 PM
 */
public class FacebookPendingReferrals {

    public static void addReferredBy(int referredbyuserid, int facebookuid){
        Logger logger = Logger.getLogger(FacebookPendingReferrals.class);
        if (referredbyuserid>0 && facebookuid>0){
            List<Facebookpendingreferredby> fbpr = HibernateUtil.getSession().createCriteria(Facebookpendingreferredby.class)
                                               .add(Restrictions.eq("facebookuid", facebookuid))
                                               .setCacheable(true)
                                               .list();
            if (fbpr!=null && fbpr.size()>0){
                //There is already a referral for this facebookuid... don't do anything
                logger.debug("no Facebookpendingreferredby found, returning");
                return;
            } else {
                //There is not a referral for this facebookuid
                Facebookpendingreferredby newfbpr = new Facebookpendingreferredby();
                newfbpr.setDate(new Date());
                newfbpr.setFacebookuid(facebookuid);
                newfbpr.setReferredbyuserid(referredbyuserid);
                try{newfbpr.save();}catch(Exception ex){logger.error("",ex);}
            }
        }
    }

    public static int getReferredbyUserid(int facebookuid){
        Logger logger = Logger.getLogger(FacebookPendingReferrals.class);
        List<Facebookpendingreferredby> fbpr = HibernateUtil.getSession().createCriteria(Facebookpendingreferredby.class)
                                           .add(Restrictions.eq("facebookuid", facebookuid))
                                           .setCacheable(true)
                                           .list();
        for (Iterator<Facebookpendingreferredby> iterator=fbpr.iterator(); iterator.hasNext();) {
            Facebookpendingreferredby facebookpendingreferredby=iterator.next();
            return facebookpendingreferredby.getReferredbyuserid();
        }
        //Return 0 if nothing found
        return 0;
    }


}
