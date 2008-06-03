package com.mythredz.htmluibeans;

import org.apache.log4j.Logger;
import com.mythredz.htmlui.UserSession;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;

import com.mythredz.util.GeneralException;
import com.mythredz.dao.Creditcard;
import com.mythredz.dao.Userrole;
import com.mythredz.money.PaymentMethod;
import com.mythredz.helpers.UserInputSafe;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class AccountBilling implements Serializable {

    private String ccnum;
    private int cctype;
    private String cvv2;
    private int ccexpmo;
    private int ccexpyear;
    private String postalcode;
    private String ccstate;
    private String street;
    private String cccity;
    private String firstname;
    private String lastname;
    private String ipaddress;
    private String merchantsessionid;

    public AccountBilling(){

    }



    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("load called");
        UserSession userSession = Pagez.getUserSession();
        if (userSession.getUser()!=null){

            if(userSession.getUser().getChargemethodcreditcardid()>0){
                Creditcard cc = Creditcard.get(userSession.getUser().getChargemethodcreditcardid());
                ccnum = cc.getCcnum();
                cctype = cc.getCctype();
                cvv2 = cc.getCvv2();
                ccexpmo = cc.getCcexpmo();
                ccexpyear = cc.getCcexpyear();
                postalcode = cc.getPostalcode();
                ccstate = cc.getState();
                street = cc.getStreet();
                cccity = cc.getCity();
                firstname = cc.getFirstname();
                lastname = cc.getLastname();
                ipaddress = cc.getIpaddress();
                merchantsessionid = cc.getMerchantsessionid();
            }


        }


    }

    public String saveAction() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        UserSession userSession = Pagez.getUserSession();



        if (userSession.getUser()!=null){


            //Start validation
            //@todo better validation
            if (ccnum.equals("")){
                Pagez.getUserSession().setMessage("You've chosen to be paid via credit card so you must provide a credit card number.");
                return "";
            }
            //End validation



            Creditcard cc = new Creditcard();
            if(userSession.getUser().getChargemethodcreditcardid()>0){
                cc = Creditcard.get(userSession.getUser().getChargemethodcreditcardid());
            }
            cc.setCcexpmo(ccexpmo);
            cc.setCcexpyear(ccexpyear);
            cc.setCcnum(ccnum);
            cc.setCctype(cctype);
            cc.setCity(cccity);
            cc.setCvv2(cvv2);
            cc.setFirstname(firstname);
            cc.setIpaddress(Pagez.getRequest().getRemoteAddr());
            cc.setLastname(lastname);
            cc.setMerchantsessionid(Pagez.getRequest().getSession().getId());
            cc.setPostalcode(postalcode);
            cc.setState(ccstate);
            cc.setStreet(street);
            cc.setUserid(userSession.getUser().getUserid());
            try{
                cc.save();
            } catch (GeneralException gex){
                Pagez.getUserSession().setMessage("Error saving record: "+gex.getErrorsAsSingleString());
                logger.debug("saveAction failed: " + gex.getErrorsAsSingleString());
                return null;
            }



            userSession.getUser().setChargemethod(PaymentMethod.PAYMENTMETHODCREDITCARD);
            userSession.getUser().setChargemethodcreditcardid(cc.getCreditcardid());

            try{
                userSession.getUser().save();
            } catch (GeneralException gex){
                Pagez.getUserSession().setMessage("Error saving record: "+gex.getErrorsAsSingleString());
                logger.debug("saveAction failed: " + gex.getErrorsAsSingleString());
                return null;
            }



            userSession.getUser().refresh();
            
            return "";
        } else {
            Pagez.getUserSession().setMessage("UserSession.getUser() is null.  Please log in.");
            return null;
        }
    }


    public TreeMap<String, String> getCreditcardtypes(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put(String.valueOf(Creditcard.CREDITCARDTYPE_VISA), "Visa");
        out.put(String.valueOf(Creditcard.CREDITCARDTYPE_MASTERCARD), "Master Card");
        out.put(String.valueOf(Creditcard.CREDITCARDTYPE_AMEX), "American Express");
        out.put(String.valueOf(Creditcard.CREDITCARDTYPE_DISCOVER), "Discover");
        return out;
    }

    public TreeMap<String, String> getMonthsForCreditcard(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put(String.valueOf(1), "Jan(01)");
        out.put(String.valueOf(2), "Feb(02)");
        out.put(String.valueOf(3), "Mar(03)");
        out.put(String.valueOf(4), "Apr(04)");
        out.put(String.valueOf(5), "May(05)");
        out.put(String.valueOf(6), "Jun(06)");
        out.put(String.valueOf(7), "Jul(07)");
        out.put(String.valueOf(8), "Aug(08)");
        out.put(String.valueOf(9), "Sep(09)");
        out.put(String.valueOf(10), "Oct(10)");
        out.put(String.valueOf(11), "Nov(11)");
        out.put(String.valueOf(12), "Dec(12)");
        return out;
    }

    public TreeMap<String, String> getYearsForCreditcard(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put(String.valueOf(2007), "2007");
        out.put(String.valueOf(2008), "2008");
        out.put(String.valueOf(2009), "2009");
        out.put(String.valueOf(2010), "2010");
        out.put(String.valueOf(2011), "2011");
        out.put(String.valueOf(2012), "2012");
        out.put(String.valueOf(2013), "2013");
        out.put(String.valueOf(2014), "2014");
        out.put(String.valueOf(2015), "2015");
        out.put(String.valueOf(2016), "2016");
        out.put(String.valueOf(2017), "2017");
        return out;
    }

    public void setChargemethods(){

    }




    public String getCcnum() {
        return ccnum;
    }

    public void setCcnum(String ccnum) {
        this.ccnum = ccnum;
    }

    public int getCctype() {
        return cctype;
    }

    public void setCctype(int cctype) {
        this.cctype = cctype;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public int getCcexpmo() {
        return ccexpmo;
    }

    public void setCcexpmo(int ccexpmo) {
        this.ccexpmo = ccexpmo;
    }

    public int getCcexpyear() {
        return ccexpyear;
    }

    public void setCcexpyear(int ccexpyear) {
        this.ccexpyear = ccexpyear;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCcstate() {
        return ccstate;
    }

    public void setCcstate(String ccstate) {
        this.ccstate = ccstate;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCccity() {
        return cccity;
    }

    public void setCccity(String cccity) {
        this.cccity = cccity;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getMerchantsessionid() {
        return merchantsessionid;
    }

    public void setMerchantsessionid(String merchantsessionid) {
        this.merchantsessionid = merchantsessionid;
    }
}
