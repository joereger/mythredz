package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Pageperformance extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
    private int pageperformanceid;
    private int year;
    private int month;
    private int day;
    private String partofday;
    private String pageid;
    private String servername;
    private double totalloads;
    private double totaltime;
    private double avgtime;


    public static Pageperformance get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Pageperformance");
        try {
            logger.debug("Pageperformance.get(" + id + ") called.");
            Pageperformance obj = (Pageperformance) HibernateUtil.getSession().get(Pageperformance.class, id);
            if (obj == null) {
                logger.debug("Pageperformance.get(" + id + ") returning new instance because hibernate returned null.");
                return new Pageperformance();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Pageperformance", ex);
            return new Pageperformance();
        }
    }

    // Constructors

    /** default constructor */
    public Pageperformance() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getPageperformanceid() {
        return pageperformanceid;
    }

    public void setPageperformanceid(int pageperformanceid) {
        this.pageperformanceid = pageperformanceid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPartofday() {
        return partofday;
    }

    public void setPartofday(String partofday) {
        this.partofday = partofday;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public double getTotalloads() {
        return totalloads;
    }

    public void setTotalloads(double totalloads) {
        this.totalloads = totalloads;
    }

    public double getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(double totaltime) {
        this.totaltime = totaltime;
    }

    public double getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(double avgtime) {
        this.avgtime = avgtime;
    }
}
