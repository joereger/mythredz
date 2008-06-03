package com.mythredz.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ErrorCode;

import java.util.Calendar;

import com.mythredz.db.Db;
import com.mythredz.util.Str;
import com.mythredz.util.Time;
import com.mythredz.util.Num;
import com.mythredz.startup.ApplicationStartup;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.email.EmailTemplateProcessor;

/**
 * User: Joe Reger Jr
 * Date: Oct 17, 2006
 * Time: 11:34:38 AM
 */
public class Log4jCustomAppender extends AppenderSkeleton {

    public boolean requiresLayout(){
        return true;
    }

    public synchronized void append( LoggingEvent event ){
        StringBuffer errorMessage = new StringBuffer();
        StringBuffer errorMessageAsHtml = new StringBuffer();
        if( this.layout == null ){
            errorHandler.error("No layout for appender " + name , null, ErrorCode.MISSING_LAYOUT );
            return;
        }
        //Get main message
        errorMessage.append(this.layout.format(event));
        CustomHtmlLayout htmlLayout = new CustomHtmlLayout();
        errorMessageAsHtml.append(htmlLayout.format(event));
        //If layout doesn't handle throwables
        if( layout.ignoresThrowable() ){
            String[] messages = event.getThrowableStrRep();
            if( messages != null ){
                for( int j = 0; j < messages.length; ++j ){
                    errorMessage.append(messages[j]);
                    errorMessage.append( '\n' );
                }
            }
        }

        if (shouldRecordThis(errorMessageAsHtml.toString())){
            //Write to database and send via email
            if (ApplicationStartup.getIsappstarted()){
                if (event.getLevel()==Level.ERROR || event.getLevel()==Level.FATAL){
                    try{
                        boolean added = false;
                        //-----------------------------------
                        //-----------------------------------
                        String[][] rstErr= Db.RunSQL("SELECT errorid, timesseen FROM error WHERE error='"+Str.cleanForSQL(errorMessageAsHtml.toString())+"'");
                        //-----------------------------------
                        //-----------------------------------
                        if (rstErr!=null && rstErr.length>0){
                            for(int i=0; i<rstErr.length; i++){
                                if (Num.isinteger(rstErr[i][1])){
                                    //-----------------------------------
                                    //-----------------------------------
                                    int count = Db.RunSQLUpdate("UPDATE error SET timesseen='"+(Integer.parseInt(rstErr[i][1])+1)+"' where errorid='"+rstErr[i][0]+"'");
                                    //-----------------------------------
                                    //-----------------------------------
                                    added=true;
                                }
                            }
                        }
                        if (!added){
                            //-----------------------------------
                            //-----------------------------------
                            int identity = Db.RunSQLInsert("INSERT INTO error(error, level, status, date, timesseen) VALUES('"+Str.cleanForSQL(errorMessageAsHtml.toString())+"', '"+event.getLevel().toInt()+"', '"+com.mythredz.dao.Error.STATUS_NEW+"', '"+ Time.dateformatfordb(Calendar.getInstance())+"', '1')", false);
                            //-----------------------------------
                            //-----------------------------------
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
            //XMPP (Instant Messages)
            if (event.getLevel()==Level.ERROR || event.getLevel()==Level.FATAL){
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_SYSADMINS, Str.truncateString(errorMessage.toString(), 300));
                xmpp.send();
            }
            //Send via email
            if (event.getLevel()==Level.ERROR || event.getLevel()==Level.FATAL){
                //EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "dNeero Error: "+event.getMessage().toString(), errorMessageAsHtml.toString());
            }
        }
    }
    
    public synchronized void close(){

    }

    //Allows me to filter out annoying framework-based errors that can't be fixed otherwise
    public boolean shouldRecordThis(String err){
        if (err.indexOf("Feed action request limit reached")>-1){
            return false;
        }
        return true;
    }



}
