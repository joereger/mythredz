package com.mythredz.htmluibeans;


import com.mythredz.systemprops.BaseUrl;
import com.mythredz.htmlui.Pagez;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Aug 28, 2007
 * Time: 10:54:28 AM
 */
public class PublicFacebookexitui implements Serializable {

    private String dummy="";

    public PublicFacebookexitui(){

    }

    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Clear the isfacebookui flag
        Pagez.getUserSession().leaveFacebookui();
        try{Pagez.sendRedirect(BaseUrl.get(false));}catch(Exception ex){logger.error("",ex);}
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}
