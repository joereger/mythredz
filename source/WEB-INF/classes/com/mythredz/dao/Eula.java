package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Eula extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int eulaid;
     private String eula;
     private Date date;



    public static Eula get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Eula");
        try {
            logger.debug("Eula.get(" + id + ") called.");
            Eula obj = (Eula) HibernateUtil.getSession().get(Eula.class, id);
            if (obj == null) {
                logger.debug("Eula.get(" + id + ") returning new instance because hibernate returned null.");
                return new Eula();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Eula", ex);
            return new Eula();
        }
    }

    // Constructors

    /** default constructor */
    public Eula() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getEulaid() {
        return eulaid;
    }

    public void setEulaid(int eulaid) {
        this.eulaid = eulaid;
    }

    public String getEula() {
        return eula;
    }

    public void setEula(String eula) {
        this.eula = eula;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
