package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.dao.Userpersistentlogin;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Time;
import com.mythredz.session.PersistentLogin;

import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class DeleteOldEmailaddresses implements Job {



    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() DeleteOldEmailaddresses called");
            int daysago = 30;
            int hoursago = daysago * 24;
            Calendar xcal = Time.xHoursAgoStart(Calendar.getInstance(), hoursago);
            HibernateUtil.getSession().createQuery("delete Emailaddress s where s.datecreated<'"+Time.dateformatfordb(xcal)+"'").executeUpdate();
        } else {
            logger.debug("InstanceProperties.DeleteOldEmailaddresses() is FALSE for this instance so this task is not being executed.");
        }


    }

}
