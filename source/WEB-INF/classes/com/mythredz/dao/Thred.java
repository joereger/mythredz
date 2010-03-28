package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;
import org.apache.log4j.Logger;

import java.util.Date;

public class Thred extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int thredid;
     private int userid;
     private String name;
     private Date createdate;
     private boolean istwitterupdateon;
     private String twitterusername;
     private String twitteraccesstoken="";
     private String twitteraccesstokensecret="";
     private boolean ispingfmupdateon;
     private String pingfmapikey;




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

    public String getTwitterusername() {
        return twitterusername;
    }

    public void setTwitterusername(String twitterusername) {
        this.twitterusername = twitterusername;
    }

    public String getTwitteraccesstoken() {
        return twitteraccesstoken;
    }

    public void setTwitteraccesstoken(String twitteraccesstoken) {
        this.twitteraccesstoken = twitteraccesstoken;
    }

    public String getTwitteraccesstokensecret() {
        return twitteraccesstokensecret;
    }

    public void setTwitteraccesstokensecret(String twitteraccesstokensecret) {
        this.twitteraccesstokensecret = twitteraccesstokensecret;
    }

    public boolean getIspingfmupdateon() {
        return ispingfmupdateon;
    }

    public void setIspingfmupdateon(boolean ispingfmupdateon) {
        this.ispingfmupdateon=ispingfmupdateon;
    }


    public String getPingfmapikey() {
        return pingfmapikey;
    }

    public void setPingfmapikey(String pingfmapikey) {
        this.pingfmapikey=pingfmapikey;
    }
}
