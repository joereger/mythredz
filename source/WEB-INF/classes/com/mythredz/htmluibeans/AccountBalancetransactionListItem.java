package com.mythredz.htmluibeans;

import java.util.Date;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Oct 13, 2006
 * Time: 9:05:26 AM
 */
public class AccountBalancetransactionListItem implements Serializable {

     private int balancetransactionid;
     private int userid;
     private Date date;
     private boolean issuccessful;
     private String amt;
     private String description;
     private String notes;

    public AccountBalancetransactionListItem(){}

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

    public boolean isIssuccessful() {
        return issuccessful;
    }

    public void setIssuccessful(boolean issuccessful) {
        this.issuccessful = issuccessful;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
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
}
