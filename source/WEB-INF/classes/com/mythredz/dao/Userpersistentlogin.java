package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Userpersistentlogin extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int userpersistentloginid;
     private int userid;
     private String randomstring;
     private Date lastusedtologin;



    public static Userpersistentlogin get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Userpersistentlogin");
        try {
            logger.debug("Userpersistentlogin.get(" + id + ") called.");
            Userpersistentlogin obj = (Userpersistentlogin) HibernateUtil.getSession().get(Userpersistentlogin.class, id);
            if (obj == null) {
                logger.debug("Userpersistentlogin.get(" + id + ") returning new instance because hibernate returned null.");
                return new Userpersistentlogin();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Userpersistentlogin", ex);
            return new Userpersistentlogin();
        }
    }

    // Constructors

    /** default constructor */
    public Userpersistentlogin() {
    }

    public boolean canRead(User user){
        if (user.getUserid()==userid){
            return true;
        }
        return false;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getUserpersistentloginid() {
        return userpersistentloginid;
    }

    public void setUserpersistentloginid(int userpersistentloginid) {
        this.userpersistentloginid = userpersistentloginid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getRandomstring() {
        return randomstring;
    }

    public void setRandomstring(String randomstring) {
        this.randomstring = randomstring;
    }

    public Date getLastusedtologin() {
        return lastusedtologin;
    }

    public void setLastusedtologin(Date lastusedtologin) {
        this.lastusedtologin = lastusedtologin;
    }
}
