package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.User;
import com.mythredz.dao.Massemail;
import com.mythredz.email.EmailTemplateProcessor;
import com.mythredz.email.MassemailQualifier;


import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */

public class SendMassemails implements Job {


    private static int NUMBERTOSENDTOEACHTIMESCHEDULERRUNS = 200;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() SendMassemails called");
            try{
                List massemails = HibernateUtil.getSession().createQuery("from Massemail where status='"+Massemail.STATUS_PROCESSING+"'").list();
                for (Iterator iterator = massemails.iterator(); iterator.hasNext();) {
                    Massemail massemail = (Massemail) iterator.next();
                    List users = HibernateUtil.getSession().createQuery("from User where userid>'"+massemail.getLastuseridprocessed()+"' order by userid asc").setMaxResults(NUMBERTOSENDTOEACHTIMESCHEDULERRUNS).list();
                    for (Iterator iterator2 = users.iterator(); iterator2.hasNext();) {
                        User user = (User) iterator2.next();
                        massemail.setLastuseridprocessed(user.getUserid());
                        if (MassemailQualifier.isQualifiedForMassemail(massemail, user)){
                            String[] args = getMassemailArgsFromUser(user);
                            String subject = EmailTemplateProcessor.processTemplate(massemail.getSubject(), user, args);
                            EmailTemplateProcessor.sendMail(subject, massemail.getHtmlmessage(), massemail.getTxtmessage(), user, args, user.getEmail(), "");
                        }
                    }
                    int maxuserid = (Integer)HibernateUtil.getSession().createQuery("select max(userid) from User").uniqueResult();
                    if (massemail.getLastuseridprocessed()==maxuserid){
                        massemail.setStatus(Massemail.STATUS_COMPLETE);
                    }
                    try{massemail.save();}catch(Exception ex){logger.error("",ex);}
                }
            } catch (Exception ex){
                logger.debug("Error in top block.");
                logger.error("",ex);
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }

    }

    public static String[] getMassemailArgsFromUser(User user){
        String[] args = new String[25];
        args[0] = String.valueOf(user.getUserid());
        args[1] = "";
        args[2] = "";
        args[3] = user.getFirstname();
        args[4] = user.getLastname();
        return args;
    }

}
