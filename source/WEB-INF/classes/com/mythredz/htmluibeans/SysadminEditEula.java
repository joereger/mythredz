package com.mythredz.htmluibeans;

import com.mythredz.eula.EulaHelper;
import com.mythredz.dao.Eula;
import com.mythredz.util.GeneralException;

import com.mythredz.util.Time;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;
import com.mysql.jdbc.TimeUtil;

import java.util.Date;
import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Nov 10, 2006
 * Time: 3:02:56 PM
 */
public class SysadminEditEula implements Serializable {

    private String eula;
    private int eulaid;
    private String date;

    public SysadminEditEula(){

    }



    public void initBean(){
        eula = EulaHelper.getMostRecentEula().getEula();
        eulaid = EulaHelper.getMostRecentEula().getEulaid();
        date = Time.dateformatcompactwithtime(Time.getCalFromDate(EulaHelper.getMostRecentEula().getDate()));
    }

    public void edit() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (!eula.equals(EulaHelper.getMostRecentEula().getEula())){
            Eula eulaObj = new Eula();
            eulaObj.setDate(new Date());
            eulaObj.setEula(eula);
            try{
                eulaObj.save();
            } catch (GeneralException gex){
                logger.error(gex);
                logger.debug("agree failed: " + gex.getErrorsAsSingleString());
                Pagez.getUserSession().setMessage("Error... please try again.");
                throw new ValidationException("Error with db.");
            }
            EulaHelper.refreshMostRecentEula();
        }
    }


    public String getEula() {
        return eula;
    }

    public void setEula(String eula) {
        this.eula = eula;
    }


    public int getEulaid() {
        return eulaid;
    }

    public void setEulaid(int eulaid) {
        this.eulaid = eulaid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
