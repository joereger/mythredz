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

/**
 * User: Joe Reger Jr
 * Date: May 24, 2007
 * Time: 12:25:50 PM
 */
public class TwitterUpdate implements Runnable {


    private static ThreadPool tp;
    private String message = "";



    public TwitterUpdate(String message){
        this.message = message;
    }


    public void run(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("run() called.");

        HttpClient client = new HttpClient();

        // pass our credentials to HttpClient, they will only be used for
        // authenticating to servers with realm "realm" on the host
        // "www.verisign.com", to authenticate against
        // an arbitrary realm or host change the appropriate argument to null.
        client.getState().setCredentials(
            new AuthScope(null, 80, null),
            new UsernamePasswordCredentials(TwitterCredentials.USERNAME, TwitterCredentials.PASSWORD)
        );

        // create a GET method that reads a file over HTTPS, we're assuming
        // that this file requires basic authentication using the realm above.
        PostMethod post = new PostMethod("http://twitter.com/statuses/update.xml");

        // Tell the GET method to automatically handle authentication. The
        // method will use any appropriate credentials to handle basic
        // authentication requests.  Setting this value to false will cause
        // any request for authentication to return with a status of 401.
        // It will then be up to the client to handle the authentication.
        post.setDoAuthentication(true);

        //Needs to be less than 140 chars
        post.addParameter("status", Str.truncateString(message, 140));

        try {
            int requestStatus = client.executeMethod( post );
            logger.debug(requestStatus + ": " + post.getResponseBodyAsString());
        } catch (Exception ex){
            logger.error("",ex);
        } finally {
            post.releaseConnection();
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
