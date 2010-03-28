package com.mythredz.htmlui;

import com.mythredz.dao.User;
import com.mythredz.dao.Userrole;
import com.mythredz.facebook.FacebookUser;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.http.RequestToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: May 31, 2006
 * Time: 12:51:06 PM
 */
public class UserSession implements Serializable {

    //Note: Only use primitives to simplify clustering.
    //Example: userid as int and then getUser() calls on the clustered/cached hibernate layer.

    private int userid;
    private boolean isloggedin = false;
    private int currentSurveyid;
    private boolean isAllowedToResetPasswordBecauseHasValidatedByEmail = false;
    private int referredbyOnlyUsedForSignup = 0;
    private boolean isSysadmin = false;
    private boolean isLoggedInToBeta = false;
    private boolean iseulaok = true;
    private String emailinvitesubject = "";
    private String emailinvitemessage = "";
    private ArrayList<String> emailinviteaddresses = new ArrayList<String>();
    private FacebookUser facebookUser = null;
    private String facebookSessionKey = "";
    private ArrayList<FacebookUser> facebookFriends = null;
    private boolean isfacebookui = false;
    private String message = "";
    private Calendar createdate = Calendar.getInstance();
    private Twitter twitter;
    private RequestToken twitterRequestToken;

    public UserSession(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("New UserSession created.");
        try{
            createdate = Calendar.getInstance();
            logger.debug("trying to set isfacebookui in UserSession");
            //Set the userSession var isfacebookui if we see anything that remotely resembles facebook activity
            if (Pagez.getRequest().getParameter("auth_token")!=null){
                logger.debug("setting isfacebook=true because of auth_token request param");
                isfacebookui=true;
            }
            if (Pagez.getRequest().getParameter("fb_sig_session_key")!=null){
                logger.debug("setting isfacebook=true because of fb_sig_session_key request param");
                isfacebookui=true;
            }
            if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
                logger.debug("setting isfacebook=true because of showsurvey request param");
                isfacebookui=true;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        logger.debug("isfacebookui="+isfacebookui);
    }

    public User getUser() {
        if (userid>0){
            return User.get(userid);
        } else {
            return null;
        }
    }

    public void leaveFacebookui(){
        isfacebookui = false;
        facebookSessionKey = "";
        isloggedin = false;
        userid = 0;
    }


    public void setUser(User user) {
        if (user!=null){
            userid = user.getUserid();
            isSysadmin = false;
            for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                Userrole userrole = iterator.next();
                if (userrole.getRoleid()== Userrole.SYSADMIN){
                    isSysadmin = true;
                }
            }
        } else {
            userid = 0;
        }
    }

    public boolean getIsloggedin() {
        return isloggedin;
    }

    public void setIsloggedin(boolean isloggedin) {
        this.isloggedin = isloggedin;
    }

    public boolean getIsAllowedToResetPasswordBecauseHasValidatedByEmail() {
        return isAllowedToResetPasswordBecauseHasValidatedByEmail;
    }

    public void setAllowedToResetPasswordBecauseHasValidatedByEmail(boolean allowedToResetPasswordBecauseHasValidatedByEmail) {
        isAllowedToResetPasswordBecauseHasValidatedByEmail = allowedToResetPasswordBecauseHasValidatedByEmail;
    }

    public int getReferredbyOnlyUsedForSignup() {
        return referredbyOnlyUsedForSignup;
    }

    public void setReferredbyOnlyUsedForSignup(int referredbyOnlyUsedForSignup) {
        this.referredbyOnlyUsedForSignup = referredbyOnlyUsedForSignup;
    }

    public boolean getIsSysadmin() {
        return isSysadmin;
    }

    public void setSysadmin(boolean sysadmin) {
        isSysadmin = sysadmin;
    }

    public boolean getIsLoggedInToBeta() {
        return isLoggedInToBeta;
    }

    public void setIsLoggedInToBeta(boolean isLoggedInToBeta) {
        this.isLoggedInToBeta = isLoggedInToBeta;
    }



    public boolean getIseulaok() {
        return iseulaok;
    }

    public void setIseulaok(boolean iseulaok) {
        this.iseulaok = iseulaok;
    }


    public ArrayList<String> getEmailinviteaddresses() {
        return emailinviteaddresses;
    }

    public void setEmailinviteaddresses(ArrayList<String> emailinviteaddresses) {
        this.emailinviteaddresses = emailinviteaddresses;
    }



    public String getEmailinvitesubject() {
        return emailinvitesubject;
    }

    public void setEmailinvitesubject(String emailinvitesubject) {
        this.emailinvitesubject = emailinvitesubject;
    }

    public String getEmailinvitemessage() {
        return emailinvitemessage;
    }

    public void setEmailinvitemessage(String emailinvitemessage) {
        this.emailinvitemessage = emailinvitemessage;
    }



    public String getFacebookSessionKey() {
        return facebookSessionKey;
    }

    public void setFacebookSessionKey(String facebookSessionKey) {
        this.facebookSessionKey = facebookSessionKey;
    }


    public boolean getIsfacebookui() {
        //Logger logger = Logger.getLogger(this.getClass().getName());
        //logger.debug("getIsfacebookui()="+isfacebookui);
        return isfacebookui;
    }

    public void setIsfacebookui(boolean isfacebookui) {
        this.isfacebookui = isfacebookui;
    }

    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (this.message==null || this.message.equals("")){
            this.message = message;
        } else {
            this.message = this.message + "<br/>" + message;
        }
        if (message==null || message.equals("")){
            this.message = "";
        }
    }

    public ArrayList<FacebookUser> getFacebookFriends() {
        return facebookFriends;
    }

    public void setFacebookFriends(ArrayList<FacebookUser> facebookFriends) {
        this.facebookFriends=facebookFriends;
    }



    public Calendar getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Calendar createdate) {
        this.createdate=createdate;
    }


    public void setLoggedInToBeta(boolean loggedInToBeta) {
        isLoggedInToBeta = loggedInToBeta;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public RequestToken getTwitterRequestToken() {
        return twitterRequestToken;
    }

    public void setTwitterRequestToken(RequestToken twitterRequestToken) {
        this.twitterRequestToken = twitterRequestToken;
    }
}
