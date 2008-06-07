package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.mythredz.dao.hibernate.NumFromUniqueResult;
import com.mythredz.emaillistener.SmtpListener;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class StartEmailListener implements Job {


    private static SmtpListener mySmtpListener;



    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() StartEmailListener called");
            startListener();
        //} else {
            //logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        //}
    }

    public static void startListener(){
        Logger logger = Logger.getLogger(StartEmailListener.class);
        try{
            logger.debug("MyThredz: Starting smtp listener.");
            if (mySmtpListener!=null){
                logger.debug("MyThredz: Shutting down smtp listener for a clean startup.");
                mySmtpListener.shutDownSmtpListener();
                mySmtpListener = null;
            }
            mySmtpListener = new SmtpListener();
        } catch (Exception e){
            logger.error("", e);
            //System.out.println("MyThredz: Unable to start smtp listener:"+e.getMessage());
            //result = "Unable to start SMTPListener.  Check event log for details.";
            logger.debug("Unable to start SMTPListener.  Check event log for details.");
        }
    }

    public static void shutdownListener(){
        Logger logger = Logger.getLogger(StartEmailListener.class);
        logger.debug("MyThredz: Shutting down smtp listener.");
        if (mySmtpListener!=null){
            logger.debug("MyThredz: Listener not null so will shut down.");
            mySmtpListener.shutDownSmtpListener();
            mySmtpListener = null;
        }
    }




}
