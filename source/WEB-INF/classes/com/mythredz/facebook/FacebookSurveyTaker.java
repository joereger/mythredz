package com.mythredz.facebook;



import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Aug 21, 2007
 * Time: 9:44:01 PM
 */
public class FacebookSurveyTaker implements Serializable {

    private FacebookUser facebookUser;
    private int userid;
    private int responseid;

    public FacebookSurveyTaker(){

    }


    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getResponseid() {
        return responseid;
    }

    public void setResponseid(int responseid) {
        this.responseid = responseid;
    }
}
