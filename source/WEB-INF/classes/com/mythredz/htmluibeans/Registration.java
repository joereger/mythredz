package com.mythredz.htmluibeans;

import org.apache.log4j.Logger;
import org.apache.commons.validator.EmailValidator;
import org.hibernate.criterion.Restrictions;
import com.mythredz.dao.*;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.*;
import com.mythredz.util.jcaptcha.CaptchaServiceSingleton;
import com.mythredz.htmlui.UserSession;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;
import com.mythredz.session.PersistentLogin;
import com.mythredz.email.EmailActivationSend;
import com.mythredz.money.PaymentMethod;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.eula.EulaHelper;

import javax.servlet.http.Cookie;
import java.util.*;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class Registration implements Serializable {

    //Form props
    private String email;
    private String password;
    private String passwordverify;
    private String firstname;
    private String lastname;
    private String nickname;
    private String j_captcha_response;
    private String captchaId;
    private String eula;
    private boolean displaytempresponsesavedmessage;


    //private String temp;

    //Other props
    private int userid;

    public Registration(){

    }


    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        displaytempresponsesavedmessage = false;
        eula = EulaHelper.getMostRecentEula().getEula();
        
    }

    public void registerAction() throws ValidationException {
        ValidationException vex = new ValidationException();
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("registerAction called:  email="+email+" password="+password+" firstname="+firstname+" lastname="+lastname);

        //Validation
        boolean haveErrors = false;

        if (password==null || password.equals("") || password.length()<6){
            vex.addValidationError("Password must be at least six characters long.");
            haveErrors = true;
        }

        if (!password.equals(passwordverify)){
            vex.addValidationError("Password and Verify Password must match.");
            haveErrors = true;
        }

        EmailValidator ev = EmailValidator.getInstance();
        if (!ev.isValid(email)){
            vex.addValidationError("Not a valid email address.");
            haveErrors = true;
        }

        if (firstname==null || firstname.equals("")){
            vex.addValidationError("First Name can't be blank.");
            haveErrors = true;
        }

        if (lastname==null || lastname.equals("")){
            vex.addValidationError("Last Name can't be blank.");
            haveErrors = true;
        }

        //@todo need to check for lcase(firstname), lcase(lastname), email in the database... people are changing caps on name and creating another account.


        if (eula==null || !eula.trim().equals(EulaHelper.getMostRecentEula().getEula().trim())){
            //@todo Registration EULA validation
            //logger.debug("eula="+eula);
            //logger.debug("EulaHelper.getMostRecentEula().getEula()="+EulaHelper.getMostRecentEula().getEula());
            //vex.addValidationError("The end user license can't be edited.");
            //eula = EulaHelper.getMostRecentEula().getEula();
            //haveErrors = true;
        }

//        boolean isCaptchaCorrect = false;
//        try {
//            isCaptchaCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, j_captcha_response);
//        } catch (CaptchaServiceException e) {
//             //should not happen, may be thrown if the id is not valid
//        }
//        if (!isCaptchaCorrect){
//            vex.addValidationError("You failed to correctly type the letters into the box.");
//            haveErrors = true;
//        }

        List<User> users = HibernateUtil.getSession().createQuery("from User where email='"+ Str.cleanForSQL(email)+"'").list();
        if (users.size()>0){
            vex.addValidationError("That email address is already in use.");
            haveErrors = true;
        }

        List<User> users2 = HibernateUtil.getSession().createQuery("from User where nickname='"+ Str.cleanForSQL(nickname)+"'").list();
        if (users2.size()>0){
            vex.addValidationError("That nickname is already in use.");
            haveErrors = true;
        }

        if (haveErrors){
            throw vex;
        }

        //Create the user
        //@todo Use http://www.jasypt.org/ to encrypt password
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setNickname(nickname);
        user.setIsactivatedbyemail(false);
        user.setEmailactivationkey(RandomString.randomAlphanumeric(5));
        user.setEmailactivationlastsent(new Date());
        user.setCreatedate(new Date());
        user.setChargemethod(PaymentMethod.PAYMENTMETHODCREDITCARD);
        user.setChargemethodcreditcardid(0);
        user.setIsenabled(true);
        user.setFacebookappremoveddate(new Date());
        user.setIsfacebookappremoved(false);
        user.setNextexercisetime(new Date());
        user.setUpcomingexercises("1");
        user.setExerciseeveryxminutes(20);
        user.setExercisechooserid(0);
        user.setExerciselistid(1);
        user.setCurrentexercisenum(1);
        user.setIsemailnightlyon(true);
        user.setCss("");
        try{
            user.save();
            userid = user.getUserid();
        } catch (GeneralException gex){
            logger.debug("registerAction failed: " + gex.getErrorsAsSingleString());
            throw new ValidationException("An internal server error occurred.  Apologies for the trouble.  Please try again.");
        }

        //Eula version check
        Usereula usereula = new Usereula();
        usereula.setDate(new Date());
        usereula.setEulaid(EulaHelper.getMostRecentEula().getEulaid());
        usereula.setUserid(user.getUserid());
        usereula.setIp(Pagez.getRequest().getRemoteAddr());
        try{
            usereula.save();
        } catch (GeneralException gex){
            logger.debug("registerAction failed: " + gex.getErrorsAsSingleString());
            throw new ValidationException("An internal server error occurred.  Apologies for the trouble.  Please try again.");
        }
        user.getUsereulas().add(usereula);


        //Send the activation email
        EmailActivationSend.sendActivationEmail(user);

        //Notify customer care group
        SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_CUSTOMERSUPPORT, "New dNeero User: "+ user.getFirstname() + " " + user.getLastname() + "("+user.getEmail()+")");
        xmpp.send();

        //Log user in
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setIsloggedin(true);
        userSession.setIsLoggedInToBeta(Pagez.getUserSession().getIsLoggedInToBeta());
        userSession.setIseulaok(true);
        userSession.setIsfacebookui(Pagez.getUserSession().getIsfacebookui());
        userSession.setFacebookSessionKey(Pagez.getUserSession().getFacebookSessionKey());
        //Set persistent login cookie
        Cookie[] cookies = PersistentLogin.getPersistentCookies(user.getUserid(), Pagez.getRequest());
        //Add a cookies to the response
        for (int j = 0; j < cookies.length; j++) {
            Pagez.getResponse().addCookie(cookies[j]);
        }
        //Put userSession object into cache
        Pagez.setUserSessionAndUpdateCache(userSession);

    }


    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordverify() {
        return passwordverify;
    }

    public void setPasswordverify(String passwordverify) {
        this.passwordverify = passwordverify;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getJ_captcha_response() {
        return j_captcha_response;
    }

    public void setJ_captcha_response(String j_captcha_response) {
        this.j_captcha_response = j_captcha_response;
    }


    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId=captchaId;
    }

    public String getEula() {
        return eula;
    }

    public void setEula(String eula) {
        this.eula = eula;
    }


    public boolean getDisplaytempresponsesavedmessage() {
        return displaytempresponsesavedmessage;
    }

    public void setDisplaytempresponsesavedmessage(boolean displaytempresponsesavedmessage) {
        this.displaytempresponsesavedmessage = displaytempresponsesavedmessage;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname=nickname;
    }
}
