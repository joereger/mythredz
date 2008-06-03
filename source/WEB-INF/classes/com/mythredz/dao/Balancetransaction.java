package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Balancetransaction extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int balancetransactionid;
     private int userid;
     private Date date;
     private boolean issuccessful;
     private double amt;
     private String description;
     private String notes;
     private String transactionid;
     private String correlationid;
     private int paymentmethod;


    public static Balancetransaction get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Balancetransaction");
        try {
            logger.debug("Balancetransaction.get(" + id + ") called.");
            Balancetransaction obj = (Balancetransaction) HibernateUtil.getSession().get(Balancetransaction.class, id);
            if (obj == null) {
                logger.debug("Balancetransaction.get(" + id + ") returning new instance because hibernate returned null.");
                return new Balancetransaction();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Balancetransaction", ex);
            return new Balancetransaction();
        }
    }

    // Constructors

    /** default constructor */
    public Balancetransaction() {
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


    public int getBalancetransactionid() {
        return balancetransactionid;
    }

    public void setBalancetransactionid(int balancetransactionid) {
        this.balancetransactionid = balancetransactionid;
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

    public boolean getIssuccessful() {
        return issuccessful;
    }

    public void setIssuccessful(boolean issuccessful) {
        this.issuccessful = issuccessful;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getCorrelationid() {
        return correlationid;
    }

    public void setCorrelationid(String correlationid) {
        this.correlationid = correlationid;
    }

    public int getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(int paymentmethod) {
        this.paymentmethod = paymentmethod;
    }
}
