package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;

public class Thred extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int thredid;
     private int userid;
     private String name;
     private Date createdate;
     private boolean istwitterupdateon;
     private String twitterid;
     private String twitterpass;



    public static Thred get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Thread");
        try {
            logger.debug("Thread.get(" + id + ") called.");
            Thred obj = (Thred) HibernateUtil.getSession().get(Thred.class, id);
            if (obj == null) {
                logger.debug("Thread.get(" + id + ") returning new instance because hibernate returned null.");
                return new Thred();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Thread", ex);
            return new Thred();
        }
    }

    // Constructors

    /** default constructor */
    public Thred() {
    }

    public boolean canRead(User user){
        if (user.getUserid()==userid){
            return true;
        }
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getThredid() {
        return thredid;
    }

    public void setThredid(int thredid) {
        this.thredid=thredid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid=userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate=createdate;
    }


    public boolean getIstwitterupdateon() {
        return istwitterupdateon;
    }

    public void setIstwitterupdateon(boolean istwitterupdateon) {
        this.istwitterupdateon=istwitterupdateon;
    }

    public String getTwitterpass() {
        return twitterpass;
    }

    public void setTwitterpass(String twitterpass) {
        this.twitterpass=twitterpass;
    }

    public String getTwitterid() {
        return twitterid;
    }

    public void setTwitterid(String twitterid) {
        this.twitterid=twitterid;
    }
}
