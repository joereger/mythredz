package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.money.CurrentBalanceCalculator;
import com.mythredz.money.MoveMoneyInRealWorld;
import com.mythredz.util.Time;
import com.mythredz.util.ErrorDissect;
import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.email.EmailTemplateProcessor;

import java.util.List;
import java.util.Iterator;
import java.util.Calendar;

/**
 * Reconciles discrepancies between in-system balance and the real world.
 */

public class MoveMoneyAround implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() MoveMoneyAround called");
            StringBuffer debug = new StringBuffer();
            try{
                List users = HibernateUtil.getSession().createQuery("from User").list();
                for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                    User user = (User) iterator.next();
                    logger.debug("===");
                    logger.debug("Start User "+user.getUserid()+" "+user.getFirstname()+" "+user.getLastname());
                    CurrentBalanceCalculator cbc = new CurrentBalanceCalculator(user);

                    if (cbc.getCurrentbalance()<0){
                        MoveMoneyInRealWorld mmirw = new MoveMoneyInRealWorld(user, cbc.getCurrentbalance());
                        mmirw.move();
                    }

                    logger.debug("End User "+user.getUserid()+" "+user.getFirstname()+" "+user.getLastname());
                    logger.debug("===");
                }
            } catch (Exception ex){
                logger.debug("Error in top block.");
                logger.error("",ex);
                SendXMPPMessage xmpp2 = new SendXMPPMessage(SendXMPPMessage.GROUP_SYSADMINS, "Error in MoveMoneyAround.java: "+ex.getMessage());
                xmpp2.send();
                EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "Error in MoveMoneyAround", ErrorDissect.dissect(ex));
            }
            EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "MoveMoneyAround Scheduled Task Report", Time.dateformatcompactwithtime(Time.nowInUserTimezone("EST"))+"<br/><br>\n\n"+debug.toString());
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }

    }

}
