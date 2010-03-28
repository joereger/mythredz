package com.mythredz.twitter;


import com.mythredz.threadpool.ThreadPool;
import com.mythredz.util.Str;
import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

/**
 * User: Joe Reger Jr
 * Date: May 24, 2007
 * Time: 12:25:50 PM
 */
public class TwitterUpdate implements Runnable {


    private static ThreadPool tp;
    private String twitteraccesstoken = "";
    private String twitteraccesstokensecret = "";
    private String updatetext = "";



    public TwitterUpdate(String twitteraccesstoken,  String twitteraccesstokensecret, String updatetext){
        this.twitteraccesstoken = twitteraccesstoken;
        this.twitteraccesstokensecret = twitteraccesstokensecret;
        this.updatetext = updatetext;
    }


    public void run(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("run() called.");

        //Set From... currently it says from Twitter4J which is lame
        //http://groups.google.com/group/twitter4j/browse_thread/thread/fc148459cf0fcda8

        try{
            TwitterFactory twitterFactory = new TwitterFactory();
            Twitter twitter = twitterFactory.getInstance();
            AccessToken accessToken = new AccessToken(twitteraccesstoken, twitteraccesstokensecret);
            twitter.setOAuthAccessToken(accessToken);

            Status status=twitter.updateStatus(Str.truncateString(updatetext, 140));
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
