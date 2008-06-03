package com.mythredz.htmluibeans;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.User;
import com.mythredz.dao.Betainvite;
import com.mythredz.util.RandomString;
import com.mythredz.email.EmailTemplateProcessor;

/**
 * User: Joe Reger Jr
 * Date: Jun 8, 2006
 * Time: 10:16:03 AM
 */
public class SysadminBetainvite implements Serializable {

    private List betainvites;
    private String name="";
    private String email="";
    private String message="";
    private String password="";



    public SysadminBetainvite() {

    }


    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("load()");
        betainvites = HibernateUtil.getSession().createQuery("from Betainvite").list();
        message="";
        name="";
        email="";
        password = RandomString.randomAlphabetic(4);
    }

    public String newInvite(){
        Logger logger = Logger.getLogger(this.getClass().getName());

        Betainvite betainvite = new Betainvite();
        betainvite.setHasloggedin(false);
        betainvite.setDatelastloggedin(new Date());
        betainvite.setDatesent(new Date());
        betainvite.setEmail(email);
        betainvite.setMessage(message);
        betainvite.setName(name);
        betainvite.setNumberoftimesloggedin(0);
        betainvite.setPassword(password);
        try{betainvite.save();}catch(Exception ex){logger.error("",ex);}

        String[] args = new String[3];
        args[0] = password;
        args[1] = message;
        args[2] = name;
        EmailTemplateProcessor.sendMail("You're Invited to the dNeero Closed Beta!", "betainvite", null, args, email, "joe@joereger.com");

        initBean();
        return "sysadminbetainvite";
    }

    public List getBetainvites() {
        //logger.debug("getListitems");
        //sort("betainviteid", false);
        return betainvites;
    }

    public void setBetainvites(List betainvites) {
        //logger.debug("setListitems");
        this.betainvites = betainvites;
    }

    protected boolean isDefaultAscending(String sortColumn) {
        return true;
    }

    protected void sort(final String column, final boolean ascending) {
        //logger.debug("sort called");
        Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                Betainvite user1 = (Betainvite)o1;
                Betainvite user2 = (Betainvite)o2;

                if (column == null) {
                    return 0;
                }
                if (column.equals("betainviteid")){
                    return ascending ? user2.getBetainviteid()-user1.getBetainviteid() : user1.getBetainviteid()-user2.getBetainviteid() ;
                } else {
                    return 0;
                }
            }
        };

        //sort and also set our model with the new sort, since using DataTable with
        //ListDataModel on front end
        if (betainvites != null && !betainvites.isEmpty()) {
            //logger.debug("sorting betainvites and initializing ListDataModel");
            Collections.sort(betainvites, comparator);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
