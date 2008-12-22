package com.mythredz.pingfm;

import com.mythredz.threadpool.ThreadPool;
import com.mythredz.util.Str;
import com.mythredz.twitter.TwitterUpdate;
import org.apache.log4j.Logger;
import org.indrio.pingfm.PingFmService;
import org.indrio.pingfm.beans.Message;
import org.indrio.pingfm.impl.PingFmServiceImpl;
import twitter4j.Twitter;
import twitter4j.Status;

/**
 * User: Joe Reger Jr
 * Date: May 24, 2007
 * Time: 12:25:50 PM
 */
public class PingfmUpdate implements Runnable {


    private static ThreadPool tp;
    private String pingfmapikey = "";
    private String updatetext = "";

    private static String DEVELOPERKEY = "3f3335f305ae302e336904fc47e92809";



    public PingfmUpdate(String pingfmapikey, String updatetext){
        this.pingfmapikey = pingfmapikey;
        this.updatetext = updatetext;
    }


    public void run(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("run() called.");

        try{
        
            PingFmService pingFmService = new PingFmServiceImpl(DEVELOPERKEY, pingfmapikey);
            Message message = new Message(updatetext, updatetext);
            pingFmService.postMessage(message, "default", true);


        } catch (Exception ex){
            logger.error("", ex);
        }

        logger.debug("done processing.");
    }


    public void update(){
        if (tp ==null){
            tp= new ThreadPool(15);
        }
        tp.assign(this);
    }



}
