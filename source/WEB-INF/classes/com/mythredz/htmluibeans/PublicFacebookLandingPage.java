package com.mythredz.htmluibeans;


import com.mythredz.util.Num;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.facebook.FacebookPendingReferrals;
import com.mythredz.htmlui.Pagez;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Jul 13, 2007
 * Time: 5:03:27 PM
 */
public class PublicFacebookLandingPage implements Serializable {

    private String dummy = "";
    private String urltoredirectto = "";

    public PublicFacebookLandingPage(){

    }

    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());

        //NOTE: ANY REDIRECT ON THIS PAGE MUST USE appendFacebookStuff() TO PASS REQUEST VARS TO NEXT SCREEN
        //THIS IS BECAUSE IE SUCKS WIND AND CREATES A NEW SESSION NEXT PAGE... FIREFOX DOES NOT, AS WOULD BE EXPECTED

        //Make sure the app responds with the facebook ui
        Pagez.getUserSession().setIsfacebookui(true);

        logger.debug("into PublicFacebookLandingPage and isfacebookui="+Pagez.getUserSession().getIsfacebookui());

        //Note: Facebook only allows me to append a single var to the end of my url so I have to do some splitting crap to make things work.
        //The basic format I'm using is action-var1-var2-var3 where the vars are specific to each action.  It's crap but it works.


        //Need to set referred by userid in the usersession
        try{
            if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
                String[] split = Pagez.getRequest().getParameter("action").split("-");
                if (split.length>=3){
                    //Set the referredbyuserid value in the session
                    if (split[2]!=null && Num.isinteger(split[2])){
                        Pagez.getUserSession().setReferredbyOnlyUsedForSignup(Integer.parseInt(split[2]));
                    }
                }
            }
        } catch (Exception ex) {logger.error("",ex);}

        //Save referral state to the database if we have a facebookuid
        try{
            if (Pagez.getUserSession().getReferredbyOnlyUsedForSignup()>0){
                if (Pagez.getUserSession().getFacebookUser()!=null && Num.isinteger(Pagez.getUserSession().getFacebookUser().getUid())){
                    FacebookPendingReferrals.addReferredBy(Pagez.getUserSession().getReferredbyOnlyUsedForSignup(), Integer.parseInt(Pagez.getUserSession().getFacebookUser().getUid()));
                }
            }
        } catch (Exception ex){logger.error("",ex);}






        //Post add page
        if (Pagez.getRequest().getParameter("addedapp")!=null && Pagez.getRequest().getParameter("addedapp").equals("1")){
            //Notify admins
            if (Pagez.getUserSession().getFacebookUser()!=null){
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook user "+ Pagez.getUserSession().getFacebookUser().getFirst_name() + " " + Pagez.getUserSession().getFacebookUser().getLast_name() + " just added app!");
                xmpp.send();
            } else {
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook user Unknown just added app!");
                xmpp.send();
            }
            urltoredirectto = appendFacebookStuff("/publicsurveylist.jsp?addedapp=1");
            try{Pagez.sendRedirect(urltoredirectto);return;}catch(Exception ex){logger.error("",ex);}
            return;
        }

        //Redirect to the public survey list
        if (Pagez.getUserSession().getIsfacebookui() && Pagez.getUserSession().getFacebookUser()!=null && Pagez.getUserSession().getFacebookUser().getHas_added_app()){
            urltoredirectto = appendFacebookStuff("/publicsurveylist.jsp");
            try{Pagez.sendRedirect(urltoredirectto);return;}catch(Exception ex){logger.error("",ex);}
            return;
        }

        //User's gonna see the add app page we generate... just debug notification here
        try{
            String referredbyuserid = "";
            String referredtosurveyid = "";
            if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
                String[] split = Pagez.getRequest().getParameter("action").split("-");
                if (split.length>=3){
                    referredbyuserid = split[2];
                    referredtosurveyid = split[1];
                }
            }

            //Notify admins
            logger.debug("Facebook app add page shown to user");
            logger.debug("Pagez.getUserSession().getIsfacebookui():"+Pagez.getUserSession().getIsfacebookui());
            if (Pagez.getUserSession().getFacebookUser()!=null){
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook app add page shown to "+ Pagez.getUserSession().getFacebookUser().getFirst_name() + " " + Pagez.getUserSession().getFacebookUser().getLast_name() + " referred by userid="+referredbyuserid+" to surveyid="+referredtosurveyid);
                xmpp.send();
            } else {
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook app add page shown to Unknown:"+Pagez.getUserSession().getFacebookSessionKey()+" referred by userid="+referredbyuserid+" to surveyid="+referredtosurveyid);
                xmpp.send();
            }
            urltoredirectto = appendFacebookStuff("/facebookappadd.jsp");
            try{Pagez.sendRedirect(urltoredirectto);return;}catch(Exception ex){logger.error("",ex);}
            return;
        }catch(Exception ex){logger.error("",ex);}
    }


    private String appendFacebookStuff(String url){
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (url.indexOf("?")<=-1){
            url = url + "?";
        }
        if (Pagez.getRequest().getParameter("auth_token")!=null){
            url = url + "&auth_token=" + Pagez.getRequest().getParameter("auth_token");
        }
        if (Pagez.getRequest().getParameter("fb_sig_session_key")!=null){
            url = url + "&fb_sig_session_key=" + Pagez.getRequest().getParameter("fb_sig_session_key");
        }
        if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
            url = url + "&action=" + Pagez.getRequest().getParameter("action");
        }
        if (Pagez.getRequest().getParameter("addedapp")!=null && Pagez.getRequest().getParameter("addedapp").equals("1")){
            url = url + "&addedapp=1";
        }
        logger.debug("url = "+url);
        return url;
    }


    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }


    public String getUrltoredirectto() {
        return urltoredirectto;
    }

    public void setUrltoredirectto(String urltoredirectto) {
        this.urltoredirectto=urltoredirectto;
    }
}
