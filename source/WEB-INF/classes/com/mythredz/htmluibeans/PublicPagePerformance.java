package com.mythredz.htmluibeans;

import com.mythredz.dao.Pageperformance;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.htmlui.Pagez;

import java.io.Serializable;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * User: Joe Reger Jr
 * Date: Jun 8, 2006
 * Time: 10:16:03 AM
 */
public class PublicPagePerformance implements Serializable {


    private List<Pageperformance> pps;
    private List<Pageperformance> ppssinglepageid;
    private String pageid = null;



    public PublicPagePerformance() {

    }



    public void initBean(){

        pps = HibernateUtil.getSession().createCriteria(Pageperformance.class)
                                           .addOrder(Order.desc("pageperformanceid"))
                                           .setCacheable(true)
                                           .setMaxResults(600)
                                           .list();
        for (Iterator<Pageperformance> iterator = pps.iterator(); iterator.hasNext();) {
            Pageperformance pageperformance = iterator.next();
            if (pageperformance.getPageid().indexOf("sysadmin")>-1){
                iterator.remove();
            }
        }


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
            for (Iterator<Pageperformance> iterator = ppssinglepageid.iterator(); iterator.hasNext();) {
                Pageperformance pageperformance = iterator.next();
                if (pageperformance.getPageid().indexOf("sysadmin")>-1){
                    iterator.remove();
                }
            }
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

    
}
