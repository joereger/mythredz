package com.mythredz.twitter;


import org.apache.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.GoogleTalkConnection;
import org.jivesoftware.smack.XMPPException;
import com.mythredz.threadpool.ThreadPool;
import com.mythredz.systemprops.SystemProperty;
import com.mythredz.util.Time;
import com.mythredz.util.Str;

import java.util.Calendar;

import twitter4j.Twitter;
import twitter4j.Status;

/**
 * User: Joe Reger Jr
 * Date: May 24, 2007
 * Time: 12:25:50 PM
 */
public class TwitterUpdate implements Runnable {


    private static ThreadPool tp;
    private String twitterid = "";
    private String twitterpass = "";
    private String updatetext = "";



    public TwitterUpdate(String twitterid,  String twitterpass, String updatetext){
        this.twitterid = twitterid;
        this.twitterpass = twitterpass;
        this.updatetext = updatetext;
    }


    public void run(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("run() called.");

        //Set From... currently it says from Twitter4J which is lame
        //http://groups.google.com/group/twitter4j/browse_thread/thread/fc148459cf0fcda8

        try{
            Twitter twitter=new Twitter(twitterid, twitterpass);
            twitter.setSource("mythredz");
            Status status=twitter.update(Str.truncateString(updatetext, 140));
            logger.debug("Twitter status updated to: "+status);
        } catch (Exception ex){
            logger.error("", ex);
        }

        logger.debug("done processing.");
    }


    public void update(){
        if (tp==null){
            tp = new ThreadPool(15);
        }
        tp.assign(this);
    }



}
