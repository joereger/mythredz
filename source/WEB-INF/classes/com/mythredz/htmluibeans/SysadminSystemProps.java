package com.mythredz.htmluibeans;

import org.apache.log4j.Logger;

import com.mythredz.systemprops.SystemProperty;
import com.mythredz.systemprops.BaseUrl;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;

import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Oct 6, 2006
 * Time: 3:35:02 AM
 */
public class SysadminSystemProps implements Serializable {


    public String baseurl;
    public String sendxmpp;
    public String smtpoutboundserver;
    public String iseverythingpasswordprotected;
    public String paypalapiusername;
    public String paypalapipassword;
    public String paypalsignature;
    public String paypalenvironment;
    public String paypalenabled;
    public String issslon;
    public String isbeta;
    public String facebook_app_name;
    public String facebook_api_key;
    public String facebook_api_secret;

    public SysadminSystemProps(){

    }



    public void initBean(){
        baseurl = SystemProperty.getProp(SystemProperty.PROP_BASEURL);
        sendxmpp = SystemProperty.getProp(SystemProperty.PROP_SENDXMPP);
        smtpoutboundserver = SystemProperty.getProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER);
        iseverythingpasswordprotected = SystemProperty.getProp(SystemProperty.PROP_ISEVERYTHINGPASSWORDPROTECTED);
        paypalapiusername = SystemProperty.getProp(SystemProperty.PROP_PAYPALAPIUSERNAME);
        paypalapipassword = SystemProperty.getProp(SystemProperty.PROP_PAYPALAPIPASSWORD);
        paypalsignature = SystemProperty.getProp(SystemProperty.PROP_PAYPALSIGNATURE);
        paypalenvironment = SystemProperty.getProp(SystemProperty.PROP_PAYPALENVIRONMENT);
        paypalenabled = SystemProperty.getProp(SystemProperty.PROP_PAYPALENABLED);
        issslon = SystemProperty.getProp(SystemProperty.PROP_ISSSLON);
        isbeta = SystemProperty.getProp(SystemProperty.PROP_ISBETA);
        facebook_app_name = SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_APP_NAME);
        facebook_api_key = SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY);
        facebook_api_secret = SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET);
    }

    public void saveProps() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            SystemProperty.setProp(SystemProperty.PROP_BASEURL, baseurl);
            SystemProperty.setProp(SystemProperty.PROP_SENDXMPP, sendxmpp);
            SystemProperty.setProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER, smtpoutboundserver);
            SystemProperty.setProp(SystemProperty.PROP_ISEVERYTHINGPASSWORDPROTECTED, iseverythingpasswordprotected);
            SystemProperty.setProp(SystemProperty.PROP_PAYPALAPIUSERNAME, paypalapiusername);
            SystemProperty.setProp(SystemProperty.PROP_PAYPALAPIPASSWORD, paypalapipassword);
            SystemProperty.setProp(SystemProperty.PROP_PAYPALSIGNATURE, paypalsignature);
            SystemProperty.setProp(SystemProperty.PROP_PAYPALENVIRONMENT, paypalenvironment);
            SystemProperty.setProp(SystemProperty.PROP_PAYPALENABLED, paypalenabled);
            SystemProperty.setProp(SystemProperty.PROP_ISSSLON, issslon);
            SystemProperty.setProp(SystemProperty.PROP_ISBETA, isbeta);
            SystemProperty.setProp(SystemProperty.PROP_FACEBOOK_APP_NAME, facebook_app_name);
            SystemProperty.setProp(SystemProperty.PROP_FACEBOOK_API_KEY, facebook_api_key);
            SystemProperty.setProp(SystemProperty.PROP_FACEBOOK_API_SECRET, facebook_api_secret);
            BaseUrl.refresh();
        } catch (Exception ex){
            logger.error("",ex);
        }
    }


    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getSendxmpp() {
        return sendxmpp;
    }

    public void setSendxmpp(String sendxmpp) {
        this.sendxmpp = sendxmpp;
    }


    public String getSmtpoutboundserver() {
        return smtpoutboundserver;
    }

    public void setSmtpoutboundserver(String smtpoutboundserver) {
        this.smtpoutboundserver = smtpoutboundserver;
    }


    public String getIseverythingpasswordprotected() {
        return iseverythingpasswordprotected;
    }

    public void setIseverythingpasswordprotected(String iseverythingpasswordprotected) {
        this.iseverythingpasswordprotected = iseverythingpasswordprotected;
    }


    public String getPaypalapiusername() {
        return paypalapiusername;
    }

    public void setPaypalapiusername(String paypalapiusername) {
        this.paypalapiusername = paypalapiusername;
    }

    public String getPaypalapipassword() {
        return paypalapipassword;
    }

    public void setPaypalapipassword(String paypalapipassword) {
        this.paypalapipassword = paypalapipassword;
    }

    public String getPaypalsignature() {
        return paypalsignature;
    }

    public void setPaypalsignature(String paypalsignature) {
        this.paypalsignature = paypalsignature;
    }

    public String getPaypalenvironment() {
        return paypalenvironment;
    }

    public void setPaypalenvironment(String paypalenvironment) {
        this.paypalenvironment = paypalenvironment;
    }

    public String getPaypalenabled() {
        return paypalenabled;
    }

    public void setPaypalenabled(String paypalenabled) {
        this.paypalenabled = paypalenabled;
    }

    public String getIssslon() {
        return issslon;
    }

    public void setIssslon(String issslon) {
        this.issslon = issslon;
    }


    public String getIsbeta() {
        return isbeta;
    }

    public void setIsbeta(String isbeta) {
        this.isbeta = isbeta;
    }

    public String getFacebook_app_name() {
        return facebook_app_name;
    }

    public void setFacebook_app_name(String facebook_app_name) {
        this.facebook_app_name = facebook_app_name;
    }

    public String getFacebook_api_key() {
        return facebook_api_key;
    }

    public void setFacebook_api_key(String facebook_api_key) {
        this.facebook_api_key = facebook_api_key;
    }

    public String getFacebook_api_secret() {
        return facebook_api_secret;
    }

    public void setFacebook_api_secret(String facebook_api_secret) {
        this.facebook_api_secret = facebook_api_secret;
    }
}
