package com.mythredz.htmluibeans;

import org.apache.log4j.Logger;


import com.mythredz.dao.Supportissuecomm;
import com.mythredz.dao.Supportissue;
import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.htmlui.UserSession;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;

import com.mythredz.util.GeneralException;
import com.mythredz.util.Num;
import com.mythredz.util.Str;
import com.mythredz.email.EmailTemplateProcessor;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class SysadminSupportIssueDetail implements Serializable {

    private int supportissueid;
    private String notes;
    private ArrayList<Supportissuecomm> supportissuecomms;
    private Supportissue supportissue;
    private String status;
    private User fromuser;




    public SysadminSupportIssueDetail(){
        
    }


    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("beginView called: supportissueid="+supportissueid);
        String tmpSupportissueid = Pagez.getRequest().getParameter("supportissueid");
        if (com.mythredz.util.Num.isinteger(tmpSupportissueid)){
            logger.debug("beginView called: found supportissueid in param="+tmpSupportissueid);
            Supportissue supportissue = Supportissue.get(Integer.parseInt(tmpSupportissueid));
            fromuser = User.get(supportissue.getUserid());
            if (Pagez.getUserSession().getUser()!=null && supportissue.canEdit(Pagez.getUserSession().getUser())){
                this.supportissue = supportissue;
                this.supportissueid = supportissue.getSupportissueid();
                this.status = String.valueOf(supportissue.getStatus());
                supportissuecomms = new ArrayList<Supportissuecomm>();
                for (Iterator<Supportissuecomm> iterator = supportissue.getSupportissuecomms().iterator(); iterator.hasNext();){
                    Supportissuecomm supportissuecomm = iterator.next();
                    supportissuecomms.add(supportissuecomm);
                }
            }

        } else {
            logger.debug("beginView called: NOT found supportissueid in param="+tmpSupportissueid);
        }
    }

    public void newNote() throws ValidationException {
        ValidationException vex = new ValidationException();
        Logger logger = Logger.getLogger(this.getClass().getName());
        //@todo permissions - make sure user is allowed to submit a note to this issue.
        if(supportissueid<=0){
            logger.debug("supportissueid not found: "+supportissueid);
            throw new ValidationException("supportissueid not found");
        } else {
            supportissue = Supportissue.get(supportissueid);
        }

        Supportissuecomm supportissuecomm = new Supportissuecomm();
        supportissuecomm.setSupportissueid(supportissueid);
        supportissuecomm.setDatetime(new Date());
        supportissuecomm.setIsfromdneeroadmin(true);
        supportissuecomm.setNotes(notes);

        supportissue.getSupportissuecomms().add(supportissuecomm);

        try{
            supportissuecomm.save();
        } catch (GeneralException gex){
            vex.addValidationError("Error saving record: "+gex.getErrorsAsSingleString());
            logger.debug("saveAction failed: " + gex.getErrorsAsSingleString());
            throw vex;
        }

        if (Num.isinteger(status)){
            supportissue.setStatus(Integer.parseInt(status));
        } else{
            supportissue.setStatus(0);
        }

        try{
            supportissue.save();
        } catch (GeneralException gex){
            vex.addValidationError("Error saving record: "+gex.getErrorsAsSingleString());
            logger.debug("saveAction failed: " + gex.getErrorsAsSingleString());
            throw vex;
        }

        //Send notification email
        String[] args = new String[3];
        args[0]=supportissue.getSubject();
        args[1]=supportissuecomm.getNotes();
        if (supportissue.getStatus()==Supportissue.STATUS_OPEN){
            args[2]= "Open";
        } else if (supportissue.getStatus()==Supportissue.STATUS_CLOSED){
            args[2]= "Closed";
        } else {
            args[2]= "Working";
        }
        EmailTemplateProcessor.sendMail("dNeero Support Issue: "+ Str.truncateString(supportissue.getSubject(),100), "supportissueresponse", User.get(supportissue.getUserid()), args);

    }

    public int getSupportissueid() {
        return supportissueid;
    }

    public void setSupportissueid(int supportissueid) {
        this.supportissueid = supportissueid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Supportissue getSupportissue() {
        return supportissue;
    }

    public void setSupportissue(Supportissue supportissue) {
        this.supportissue = supportissue;
    }

    public ArrayList<Supportissuecomm> getSupportissuecomms() {
        return supportissuecomms;
    }

    public void setSupportissuecomms(ArrayList<Supportissuecomm> supportissuecomms) {
        this.supportissuecomms = supportissuecomms;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getFromuser() {
        return fromuser;
    }

    public void setFromuser(User fromuser) {
        this.fromuser = fromuser;
    }
}
