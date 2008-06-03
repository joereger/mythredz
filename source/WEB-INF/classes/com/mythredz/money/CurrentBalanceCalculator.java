package com.mythredz.money;

import com.mythredz.dao.*;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.hibernate.NumFromUniqueResult;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Oct 11, 2006
 * Time: 1:52:57 PM
 */
public class CurrentBalanceCalculator implements Serializable {

    private double currentbalance = 0;
    private double pendingearnings = 0;
    private User user;

    public CurrentBalanceCalculator(User user){
        this.user = user;
        if (user!=null){
            loadCurrentbalance();
        }
    }

    public void loadCurrentbalance(){
        if (user!=null){
            currentbalance = 0;
            currentbalance = NumFromUniqueResult.getDouble("select sum(amt) from Balance where userid='"+user.getUserid()+"'");
        }
    }




    public double getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(double currentbalance) {
        this.currentbalance = currentbalance;
    }

    public double getPendingearnings() {
        return pendingearnings;
    }

    public void setPendingearnings(double pendingearnings) {
        this.pendingearnings = pendingearnings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


   
}
