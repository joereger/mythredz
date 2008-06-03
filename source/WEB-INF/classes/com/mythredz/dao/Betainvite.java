package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Betainvite extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int betainviteid;
     private Date datesent;
     private String name;
     private String email;
     private String message;
     private String password;
     private boolean hasloggedin;
     private Date datelastloggedin;
     private int numberoftimesloggedin;



    public static Betainvite get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Betainvite");
        try {
            logger.debug("Betainvite.get(" + id + ") called.");
            Betainvite obj = (Betainvite) HibernateUtil.getSession().get(Betainvite.class, id);
            if (obj == null) {
                logger.debug("Betainvite.get(" + id + ") returning new instance because hibernate returned null.");
                return new Betainvite();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Betainvite", ex);
            return new Betainvite();
        }
    }

    // Constructors

    /** default constructor */
    public Betainvite() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getBetainviteid() {
        return betainviteid;
    }

    public void setBetainviteid(int betainviteid) {
        this.betainviteid = betainviteid;
    }

    public Date getDatesent() {
        return datesent;
    }

    public void setDatesent(Date datesent) {
        this.datesent = datesent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getHasloggedin() {
        return hasloggedin;
    }

    public void setHasloggedin(boolean hasloggedin) {
        this.hasloggedin = hasloggedin;
    }

    public Date getDatelastloggedin() {
        return datelastloggedin;
    }

    public void setDatelastloggedin(Date datelastloggedin) {
        this.datelastloggedin = datelastloggedin;
    }

    public int getNumberoftimesloggedin() {
        return numberoftimesloggedin;
    }

    public void setNumberoftimesloggedin(int numberoftimesloggedin) {
        this.numberoftimesloggedin = numberoftimesloggedin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
