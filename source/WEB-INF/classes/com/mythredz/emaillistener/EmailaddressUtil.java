package com.mythredz.emaillistener;

import com.mythredz.dao.Emailaddress;
import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.RandomString;
import com.mythredz.util.Num;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

/**
 * User: joereger
 * Date: Aug 9, 2008
 * Time: 11:40:30 AM
 */
public class EmailaddressUtil {

    public static String TYPE_DAILYEMAIL = "daily";

    //mythredz-daily-453-sdfsd87987


    public static Emailaddress generateEmailaddress(User user, Date date, String type){
        Logger logger = Logger.getLogger(EmailaddressUtil.class);
        String random = RandomString.randomAlphanumericAllUpperCaseNoOsOrZeros(10);
        String address = "mythredz"+"-"+type+"-"+user.getUserid()+"-"+random.toLowerCase();
        Emailaddress emailaddress = new Emailaddress();
        emailaddress.setAddress(address);
        emailaddress.setDate(date);
        emailaddress.setDatecreated(new Date());
        emailaddress.setIsvalid(true);
        emailaddress.setUserid(user.getUserid());
        try{emailaddress.save();}catch(Exception ex){logger.error("", ex);}
        return emailaddress;
    }

    public static Emailaddress getEmailaddress(String emailaddress){
        Logger logger = Logger.getLogger(EmailaddressUtil.class);
        try{
            if (emailaddress!=null && emailaddress.length()>0){
//                String[] split = emailaddress.split("-");
//                if (split!=null && split.length>=4){
//                    String useridStr = split[2];
//                    if (Num.isinteger(useridStr)){
//                        int userid = Integer.parseInt(useridStr);
//                        String random = split[3];
//                        if (random!=null && random.length()>0){
//
//                        }
//                    }
//                }
                //Try to find it
                List<Emailaddress> emailaddresses = HibernateUtil.getSession().createCriteria(Emailaddress.class)
                                                   .add(Restrictions.eq("address", emailaddress.toLowerCase()))
                                                   .setMaxResults(1)
                                                   .setCacheable(true)
                                                   .list();
                if (emailaddresses!=null && emailaddresses.size()>0 && emailaddresses.get(0)!=null){
                    return emailaddresses.get(0);
                }
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        return null;
    }

}
