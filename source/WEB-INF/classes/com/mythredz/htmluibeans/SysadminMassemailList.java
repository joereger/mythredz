package com.mythredz.htmluibeans;

import com.mythredz.dao.hibernate.HibernateUtil;


import java.io.Serializable;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

/**
 * User: Joe Reger Jr
 * Date: Jun 8, 2006
 * Time: 10:16:03 AM
 */
public class SysadminMassemailList implements Serializable {

    private List massemails;

    public SysadminMassemailList() {



    }



    public void initBean(){
        massemails = HibernateUtil.getSession().createQuery("from Massemail order by massemailid desc").list();
    }

    public List getMassemails() {
        //logger.debug("getListitems");
        //sort("title", true);
        return massemails;
    }

    public void setMassemails(List massemails) {
        //logger.debug("setListitems");
        this.massemails = massemails;
    }

    protected boolean isDefaultAscending(String sortColumn) {
        return true;
    }






}
