package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.systemprops.SystemProperty;
import com.mythredz.dao.Userpersistentlogin;
import com.mythredz.dao.User;
import com.mythredz.dao.Emailaddress;
import com.mythredz.dao.Thred;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Time;
import com.mythredz.session.PersistentLogin;
import com.mythredz.email.EmailSend;
import com.mythredz.email.EmailSendSimple;
import com.mythredz.email.EmailTemplateProcessorSimple;
import com.mythredz.smtp.EmailaddressUtil;

import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class SendEmailReminderToUpdateStatus implements Job {



    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() SendEmailReminderToUpdateStatus called");

            List<User> users = HibernateUtil.getSession().createQuery("from User").list();
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                if(user.getIsenabled()){
                    if (user.getIsemailnightlyon()){
                        sendForOneUser(user);
                    }
                }
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }


    }

    public static void sendForOneUser(User user){
        Logger logger = Logger.getLogger(SendEmailReminderToUpdateStatus.class);
        try{
            //Create the temporary address for this email
            Emailaddress emailaddress = EmailaddressUtil.generateEmailaddress(user, new Date(), "daily");

            String dateStr = Time.dateformatDayOfWeekAndDate(Time.getCalFromDate(emailaddress.getDate()));
            String subject = "MyThredz: What happened on "+dateStr+"?";
            String fromaddress = emailaddress.getAddress()+"@"+ SystemProperty.getProp(SystemProperty.PROP_EMAILPOSTFIX);

            //Send
            String[] args = new String[25];
            args[0] = getBodyForUser(user, emailaddress);
            EmailTemplateProcessorSimple.sendMail(subject, "generic", user, args, user.getEmail(), fromaddress);
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public static String getBodyForUser(User user, Emailaddress emailaddress) throws Exception{
            StringBuffer body = new StringBuffer();
            String dateStr = Time.dateformatDayOfWeekAndDate(Time.getCalFromDate(emailaddress.getDate()));
            body.append("This email allows you to quickly update your thredz via email."+"\n");
            body.append("Instructions: hit reply and type what happened between the Start ===> and <=== End for each thred."+"\n");
            body.append("This email creates updates dated "+dateStr+"."+"\n");
            body.append("\n");
            List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", user.getUserid()))
                .setCacheable(true)
                .list();
            for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
                Thred thred=iterator.next();
                body.append("\n");
                body.append(thred.getName()+": "+"\n");
                body.append("Start("+thred.getThredid()+")===> Replace this with what happened. <===End("+thred.getThredid()+")"+"\n");
                body.append("\n");
            }
            body.append("\n");
            body.append("Notes/Help:"+"\n");
            body.append("Ignore the thredz you don't want to update."+"\n");
            body.append("It works best if you keep the email in text format, not html."+"\n");
            body.append("Reply to this email multiple times... all updates will use the date "+dateStr+"."+"\n");
            body.append("Don't edit the numbers in parentheses... they help us tell your thredz apart."+"\n");
            body.append("You can use this email for up to 30 days."+"\n");
            body.append("Turn these emails on/off in your MyThredz account settings."+"\n");
            body.append("\n");
            body.append("http://www.mythredz.com/"+"\n");
            return body.toString();
    }

}
