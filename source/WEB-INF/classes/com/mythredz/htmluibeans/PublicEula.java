package com.mythredz.htmluibeans;

import com.mythredz.eula.EulaHelper;
import com.mythredz.dao.User;
import com.mythredz.dao.Usereula;

import com.mythredz.util.GeneralException;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Nov 10, 2006
 * Time: 2:48:46 PM
 */
public class PublicEula implements Serializable {

    private String eula;

    public PublicEula(){

    }



    public void initBean(){
        eula = EulaHelper.getMostRecentEula().getEula();
    }


    public String getEula() {
        return eula;
    }

    public void setEula(String eula) {
        this.eula = eula;
    }
}
