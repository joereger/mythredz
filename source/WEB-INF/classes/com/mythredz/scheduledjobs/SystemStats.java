package com.mythredz.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.mythredz.systemprops.InstanceProperties;
import com.mythredz.dao.Userpersistentlogin;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.hibernate.NumFromUniqueResult;


import java.util.*;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class SystemStats implements Job {



    //BE SURE TO SYNC CODE HERE WITH MAIN SystemStats in beans 
    private static int totalusers=0;


    private static double systembalance=0;
    private static double systembalancerealworld=0;
    private static double systembalancetotal=0;




    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() SystemStats called");

            totalusers = NumFromUniqueResult.getInt("select count(*) from User");



            systembalance = NumFromUniqueResult.getDouble("select sum(amt) from Balance");
            systembalancerealworld = (-1)*NumFromUniqueResult.getDouble("select sum(amt) from Balancetransaction where issuccessful=true");
            systembalancetotal = systembalancerealworld - systembalance;





     

        //} else {
            //logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        //}
    }




   



    public static double getSystembalance() {
        return systembalance;
    }

    public static void setSystembalance(double systembalance) {
        //SystemStats.systembalance = systembalance;
    }

    public static double getSystembalancerealworld() {
        return systembalancerealworld;
    }

    public static void setSystembalancerealworld(double systembalancerealworld) {
        //SystemStats.systembalancerealworld = systembalancerealworld;
    }

    public static double getSystembalancetotal() {
        return systembalancetotal;
    }

    public static void setSystembalancetotal(double systembalancetotal) {
        //SystemStats.systembalancetotal = systembalancetotal;
    }




    


    public static int getTotalusers() {
        return totalusers;
    }

    public static void setTotalusers(int totalusers) {
        //SystemStats.totalusers=totalusers;
    }
}
