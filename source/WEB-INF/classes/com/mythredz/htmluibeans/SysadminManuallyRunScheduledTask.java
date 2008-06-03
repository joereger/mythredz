package com.mythredz.htmluibeans;

import com.mythredz.scheduledjobs.*;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Sep 20, 2006
 * Time: 8:42:14 AM
 */
public class SysadminManuallyRunScheduledTask implements Serializable {

    public SysadminManuallyRunScheduledTask(){}

    public void initBean(){
        
    }





   




    
    
    public String runMoveMoneyAround(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{MoveMoneyAround task = new MoveMoneyAround();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }



    public String runDeleteOldPersistentlogins(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{DeleteOldPersistentlogins task = new DeleteOldPersistentlogins();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }




    
    public String runSystemStats(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{com.mythredz.scheduledjobs.SystemStats task = new com.mythredz.scheduledjobs.SystemStats();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runSendMassemails(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{com.mythredz.scheduledjobs.SendMassemails task = new com.mythredz.scheduledjobs.SendMassemails();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }









    public String runPagePerformanceRecordAndFlush(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{PagePerformanceRecordAndFlush task = new com.mythredz.scheduledjobs.PagePerformanceRecordAndFlush();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

}
