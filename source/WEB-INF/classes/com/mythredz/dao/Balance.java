package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Balance extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int balanceid;
     private int userid;
     private Date date;
     private double amt;
     private double currentbalance;
     private String description;


    public static Balance get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Balance");
        try {
            logger.debug("Balance.get(" + id + ") called.");
            Balance obj = (Balance) HibernateUtil.getSession().get(Balance.class, id);
            if (obj == null) {
                logger.debug("Balance.get(" + id + ") returning new instance because hibernate returned null.");
                return new Balance();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Balance", ex);
            return new Balance();
        }
    }

    // Constructors

    /** default constructor */
    public Balance() {
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


    public int getBalanceid() {
        return balanceid;
    }

    public void setBalanceid(int balanceid) {
        this.balanceid = balanceid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(double currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
