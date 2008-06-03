package com.mythredz.pageperformance;

import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.Pageperformance;

import java.util.*;

import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Mar 9, 2008
 * Time: 11:42:20 AM
 */
public class PagePerformanceUtil {


    private static Map<String,PagePerformance> pagePerformances = Collections.synchronizedMap(new TreeMap<String,PagePerformance>());

    public static void add(String pageid, String servername, long pageloadtime){
        if (pagePerformances==null){
            pagePerformances = Collections.synchronizedMap(new TreeMap<String,PagePerformance>());
        }
        //Create the key for this object in the local/temp cache... performance-important uniqueness
        Calendar cal = Calendar.getInstance();
        String partofday = getPartOfDay(cal);
        String key = pageid+"-"+servername+"-"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH)+"-"+partofday;
        //See if it exists
        if (pagePerformances.containsKey(key)){
            //Pageid exists already, increment values
            PagePerformance pp = pagePerformances.get(key);
            pp.setTotaltime(pp.getTotaltime()+pageloadtime);
            pp.setTotalpageloads(pp.getTotalpageloads()+1);
            synchronized(pagePerformances){
                pagePerformances.put(key, pp);
            }
        } else {
            //Pageid doesn't exist, create a new one
            PagePerformance pp = new PagePerformance();
            pp.setPageid(pageid);
            pp.setYear(cal.get(Calendar.YEAR));
            pp.setMonth(cal.get(Calendar.MONTH));
            pp.setDay(cal.get(Calendar.DAY_OF_MONTH));
            pp.setPartofday(partofday);
            pp.setServername(InstanceProperties.getInstancename());
            pp.setTotalpageloads(1);
            pp.setTotaltime(pageloadtime);
            synchronized(pagePerformances){
                pagePerformances.put(key, pp);
            }
        }
    }

    public static String getPartOfDay(Calendar cal){
        //Could return simply the hour_of_day... could reduce db records by going to quarter of days
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return String.valueOf(hour);
//        if (hour<=6){
//            return "0-6";
//        } else if (hour>6 && hour<=12){
//            return "7-12";
//        } else if (hour>12 && hour<=18){
//            return "13-18";
//        } else if (hour>18){
//            return "19-24";
//        }
//        return "na";
    }



    public static ArrayList<PagePerformance> getPagePerformances() {
        ArrayList<PagePerformance> out = new ArrayList<PagePerformance>();
        if(pagePerformances!=null){
            synchronized(pagePerformances){
                Iterator keyValuePairs = pagePerformances.entrySet().iterator();
                for (int i = 0; i < pagePerformances.size(); i++){
                    Map.Entry mapentry = (Map.Entry) keyValuePairs.next();
                    String pageid = (String)mapentry.getKey();
                    PagePerformance pp = (PagePerformance)mapentry.getValue();
                    out.add(pp);
                }
            }
        }
        return out;
    }


    public static void recordAndFlush(){
        Logger logger = Logger.getLogger(PagePerformanceUtil.class);
        if (pagePerformances!=null){
            synchronized(pagePerformances){
                Iterator keyValuePairs = pagePerformances.entrySet().iterator();
                for (int i = 0; i < pagePerformances.size(); i++){
                    Map.Entry mapentry = (Map.Entry) keyValuePairs.next();
                    String pageid = (String)mapentry.getKey();
                    PagePerformance pp = (PagePerformance)mapentry.getValue();
                    //See if this one exists
                    List<Pageperformance> pps = HibernateUtil.getSession().createCriteria(Pageperformance.class)
                                           .add(Restrictions.eq("year", pp.getYear()))
                                           .add(Restrictions.eq("month", pp.getMonth()))
                                           .add(Restrictions.eq("day", pp.getDay()))
                                           .add(Restrictions.eq("partofday", pp.getPartofday()))
                                           .add(Restrictions.eq("pageid", pp.getPageid()))
                                           .add(Restrictions.eq("servername", pp.getServername()))
                                           .setCacheable(true)
                                           .list();
                    if (pps!=null && pps.size()>=1){
                        for (Iterator<Pageperformance> iterator = pps.iterator(); iterator.hasNext();) {
                            Pageperformance pageperformance = iterator.next();
                            pageperformance.setTotalloads(pageperformance.getTotalloads() + pp.getTotalpageloads());
                            pageperformance.setTotaltime(pageperformance.getTotaltime() + pp.getTotaltime());
                            pageperformance.setAvgtime(pageperformance.getTotaltime()/pageperformance.getTotalloads());
                            try{pageperformance.save();}catch(Exception ex){logger.error("", ex);}
                        }
                    } else {
                        //None exists so create a new one
                        Pageperformance pageperformance = new Pageperformance();
                        pageperformance.setYear(pp.getYear());
                        pageperformance.setMonth(pp.getMonth());
                        pageperformance.setDay(pp.getDay());
                        pageperformance.setPartofday(pp.getPartofday());
                        pageperformance.setPageid(pp.getPageid());
                        pageperformance.setServername(pp.getServername());
                        pageperformance.setTotalloads(pp.getTotalpageloads());
                        pageperformance.setTotaltime(pp.getTotaltime());
                        pageperformance.setAvgtime(pageperformance.getTotaltime()/pageperformance.getTotalloads());
                        try{pageperformance.save();}catch(Exception ex){logger.error("", ex);}
                    }
                }
            }
        }
        //Reset
        pagePerformances = Collections.synchronizedMap(new TreeMap<String,PagePerformance>());
    }

}
