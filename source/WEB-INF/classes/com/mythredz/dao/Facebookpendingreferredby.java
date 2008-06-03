package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Facebookpendingreferredby extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
    private int facebookpendingreferredbyid;
    private Date date;
    private int referredbyuserid;
    private int facebookuid;


    public static Facebookpendingreferredby get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Facebookpendingreferredby");
        try {
            logger.debug("Facebookpendingreferredby.get(" + id + ") called.");
            Facebookpendingreferredby obj = (Facebookpendingreferredby) HibernateUtil.getSession().get(Facebookpendingreferredby.class, id);
            if (obj == null) {
                logger.debug("Facebookpendingreferredby.get(" + id + ") returning new instance because hibernate returned null.");
                return new Facebookpendingreferredby();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Facebookpendingreferredby", ex);
            return new Facebookpendingreferredby();
        }
    }

    // Constructors

    /** default constructor */
    public Facebookpendingreferredby() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getFacebookpendingreferredbyid() {
        return facebookpendingreferredbyid;
    }

    public void setFacebookpendingreferredbyid(int facebookpendingreferredbyid) {
        this.facebookpendingreferredbyid=facebookpendingreferredbyid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public int getReferredbyuserid() {
        return referredbyuserid;
    }

    public void setReferredbyuserid(int referredbyuserid) {
        this.referredbyuserid=referredbyuserid;
    }

    public int getFacebookuid() {
        return facebookuid;
    }

    public void setFacebookuid(int facebookuid) {
        this.facebookuid=facebookuid;
    }
}
