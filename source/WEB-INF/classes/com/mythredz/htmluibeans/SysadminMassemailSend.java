package com.mythredz.htmluibeans;

import com.mythredz.dao.*;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class SysadminMassemailSend implements Serializable {

    private Massemail massemail;
    private String password;




    public void initBean(){
        if (com.mythredz.util.Num.isinteger(Pagez.getRequest().getParameter("massemailid"))){
            massemail = Massemail.get(Integer.parseInt(Pagez.getRequest().getParameter("massemailid")));
        }

    }

    public void send() throws ValidationException {
        ValidationException vex = new ValidationException();
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (password.equals("pupper") && massemail.getStatus()==Massemail.STATUS_NEW){
            massemail.setDate(new Date());
            massemail.setStatus(Massemail.STATUS_PROCESSING);
            try{massemail.save();}catch(Exception ex){logger.error("",ex);}
        } else {
            throw new ValidationException("Password fails!");
        }
    }


    public Massemail getMassemail() {
        return massemail;
    }

    public void setMassemail(Massemail massemail) {
        this.massemail = massemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
