package com.mythredz.emaillistener;


import org.apache.log4j.Logger;

import java.net.*;

import com.mythredz.systemprops.InstanceProperties;

/**
 * This class creates a listener with SMTPListenerConnHandler which
 * gives it a raw message by calling gotMailMessage().  This class
 * then passes that raw message on to the api.
 */

public class SmtpListener implements Runnable  {
    ServerSocket slisten;
    Thread thread;
    boolean isPortSuccessfullyBound = false;
    public static boolean keepMeRunning = true;

    public SmtpListener() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunEmailListenerOnThisInstance()){
            logger.debug("MyThredz: New smtplistener being started.");
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
    }

    public void shutDownSmtpListener(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("MyThredz: SmtpListener.shutDownSmtpListener() called");
        try{
            keepMeRunning = false;
            slisten.close();
            thread = null;
        } catch (Exception ex){
            logger.error("", ex);
        }
    }


    // Callback from SmtpListenerConnHandler
    public synchronized void gotMailMessage(javax.mail.internet.MimeMessage mimeMessage) {
        EmailApi emailApi = new EmailApi(mimeMessage);
    }

    //Status check called from MasterThread
    public String statusCheck(){
        String status="";
        if (InstanceProperties.getRunEmailListenerOnThisInstance()){
            if (isPortSuccessfullyBound){
                status = "Listening for email.";
            } else {
                status = "Couldn't bind to port 25.";
            }
        } else {
            status = "Not turned on in InstanceProperties.";
        }
        return status;
    }

    public boolean isRunningAsItShouldBe(){
        if (InstanceProperties.getRunEmailListenerOnThisInstance()){
            if (isPortSuccessfullyBound){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void run()  {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("Starting SMTP listener");
        try {
            slisten = new ServerSocket(25, 50, InetAddress.getByName(InstanceProperties.getEmailListenerIP()));
            isPortSuccessfullyBound = true;
            keepMeRunning = true;
            Socket sconn = null;
            SmtpListenerConnHandler smtpHandler = null;
            while(keepMeRunning) {
                sconn = slisten.accept();
                smtpHandler = new SmtpListenerConnHandler(sconn, this);
            }
            logger.debug("MyThredz: Freeing up SMTP Listener socket port in SmtpListener.java");
            sconn.close();
            sconn = null;
            smtpHandler = null;
            slisten.close();
            thread = null;
        } catch (java.net.BindException e)  {
            //System.out.println("REGER: SMTP Listener did not bind to port 25: " + e.getMessage());
            //e.printStackTrace();
            logger.error("", e);
            logger.error("SMTP Listener did not bind to port 25.  Incoming email API will not function properly.");
        } catch (java.net.SocketException sockex){
            //System.out.println("REGER: SMTP Listener socket exception: " + sockex.getMessage());
            sockex.printStackTrace();
            logger.error("", sockex);
        } catch (Exception e)  {
            //System.out.println("REGER: SMTP Listener general exception: " + e.getMessage());
            e.printStackTrace();
            logger.error("", e);
            //reger.core.EmailSend.sendMail(reger.Vars.EMAILDEFAULTTO, reger.Vars.EMAILDEFAULTTO, "Weblogs SMTP Listener Failed to Bind to Port 25", "This is generally caused by another service running on port 25 or by a conflict with Tomcat on startup.  It can generally be fixed by restarting Tomcat.");
        }
        logger.debug("MyThredz: SMTP Listener thread at end of run() method.");
    }

    public static void main (String args[]) {
	    new SmtpListener();
    }
}

