package com.mythredz.facebook;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Aug 21, 2007
 * Time: 9:16:39 PM
 */
public class FacebookSurveyThatsBeenTaken implements Serializable {


    private ArrayList<FacebookSurveyTaker> facebookSurveyTakers;

    public FacebookSurveyThatsBeenTaken(){

    }

    public void addFacebookSurveyTaker(FacebookSurveyTaker facebookSurveyTaker){
        Logger logger = Logger.getLogger(this.getClass().getName());
        boolean isalreadyhere = false;
        if (facebookSurveyTakers!=null){
            for (Iterator<FacebookSurveyTaker> iterator = facebookSurveyTakers.iterator(); iterator.hasNext();) {
                FacebookSurveyTaker surveyTaker = iterator.next();
                if (surveyTaker.getUserid() == facebookSurveyTaker.getUserid()){
                    isalreadyhere = true;
                }
            }
        }

    }




    public ArrayList<FacebookSurveyTaker> getFacebookSurveyTakers() {
        return facebookSurveyTakers;
    }

    public void setFacebookSurveyTakers(ArrayList<FacebookSurveyTaker> facebookSurveyTakers) {
        this.facebookSurveyTakers = facebookSurveyTakers;
    }
}
