package com.mythredz.htmluibeans;

import com.mythredz.dao.Pageperformance;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.htmlui.Pagez;
import com.mythredz.pageperformance.PagePerformanceUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

/**
 * User: Joe Reger Jr
 * Date: Jun 8, 2006
 * Time: 10:16:03 AM
 */
public class SysadminPagePerformance implements Serializable {


    private List<Pageperformance> pps;
    private List<Pageperformance> ppssinglepageid;
    private String pageid = null;
    private Calendar startDate;
    private Calendar endDate;


    public SysadminPagePerformance() {

    }



    public void initBean(){

        pps = HibernateUtil.getSession().createCriteria(Pageperformance.class)
                                           .addOrder(Order.desc("pageperformanceid"))
                                           .setCacheable(true)
                                           .setMaxResults(600)
                                           .list();


        if (Pagez.getRequest().getParameter("pageid")!=null && !Pagez.getRequest().getParameter("pageid").equals("")){
            pageid = Pagez.getRequest().getParameter("pageid");
        }
        if (pageid!=null){
            ppssinglepageid = HibernateUtil.getSession().createCriteria(Pageperformance.class)
                                            .add(Restrictions.eq("pageid", pageid))
                                            .addOrder(Order.desc("pageperformanceid"))
                                           .setCacheable(true)
                                           .setMaxResults(600)
                                           .list();
        }
    }

    public void find(){
        if (startDate!=null && endDate!=null){
            int sYear = startDate.get(Calendar.YEAR);
            int sMonth = startDate.get(Calendar.MONTH);
            int sDay = startDate.get(Calendar.DAY_OF_MONTH);
            int eYear = endDate.get(Calendar.YEAR);
            int eMonth = endDate.get(Calendar.MONTH);
            int eDay = endDate.get(Calendar.DAY_OF_MONTH);
            pps = HibernateUtil.getSession().createCriteria(Pageperformance.class)
                   .add(Restrictions.ge("year", sYear))
                    .add(Restrictions.ge("month", sMonth))
                    .add(Restrictions.ge("day", sDay)) 
                    .add(Restrictions.le("year", eYear))
                    .add(Restrictions.le("month", eMonth))
                    .add(Restrictions.le("day", eDay))
                    .addOrder(Order.desc("avgtime"))
                   .setCacheable(true)
                   .setMaxResults(600)
                   .list();
        }
    }


    public List<Pageperformance> getPps() {
        return pps;
    }

    public void setPps(List<Pageperformance> pps) {
        this.pps = pps;
    }

    public List<Pageperformance> getPpssinglepageid() {
        return ppssinglepageid;
    }

    public void setPpssinglepageid(List<Pageperformance> ppssinglepageid) {
        this.ppssinglepageid = ppssinglepageid;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
}
