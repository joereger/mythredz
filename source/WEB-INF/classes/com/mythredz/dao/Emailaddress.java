package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;

public class Emailaddress extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int emailaddressid;
     private int userid;
     private Date date;
     private String address;
     private boolean isvalid;
     private Date datecreated;


    public static Emailaddress get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Emailaddress");
        try {
            logger.debug("Emailaddress.get(" + id + ") called.");
            Emailaddress obj = (Emailaddress) HibernateUtil.getSession().get(Emailaddress.class, id);
            if (obj == null) {
                logger.debug("Emailaddress.get(" + id + ") returning new instance because hibernate returned null.");
                return new Emailaddress();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Emailaddress", ex);
            return new Emailaddress();
        }
    }

    // Constructors

    /** default constructor */
    public Emailaddress() {
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


    public int getEmailaddressid() {
        return emailaddressid;
    }

    public void setEmailaddressid(int emailaddressid) {
        this.emailaddressid=emailaddressid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid=userid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid=isvalid;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated=datecreated;
    }
}
