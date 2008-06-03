package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Supportissuecomm extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int supportissuecommid;
     private int supportissueid;
     private Date datetime;
     private boolean isfromdneeroadmin;
     private String notes;


    public static Supportissuecomm get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Supportissuecomm");
        try {
            logger.debug("Supportissuecomm.get(" + id + ") called.");
            Supportissuecomm obj = (Supportissuecomm) HibernateUtil.getSession().get(Supportissuecomm.class, id);
            if (obj == null) {
                logger.debug("Supportissuecomm.get(" + id + ") returning new instance because hibernate returned null.");
                return new Supportissuecomm();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Supportissuecomm", ex);
            return new Supportissuecomm();
        }
    }

    // Constructors

    /** default constructor */
    public Supportissuecomm() {
    }


    public boolean canRead(User user){
        Supportissue supportissue = Supportissue.get(supportissueid);
        return supportissue.canRead(user);
    }

    public boolean canEdit(User user){
        return canRead(user);
    }



    // Property accessors

    public int getSupportissuecommid() {
        return supportissuecommid;
    }

    public void setSupportissuecommid(int supportissuecommid) {
        this.supportissuecommid = supportissuecommid;
    }

    public int getSupportissueid() {
        return supportissueid;
    }

    public void setSupportissueid(int supportissueid) {
        this.supportissueid = supportissueid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean getIsfromdneeroadmin() {
        return isfromdneeroadmin;
    }

    public void setIsfromdneeroadmin(boolean isfromdneeroadmin) {
        this.isfromdneeroadmin = isfromdneeroadmin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
