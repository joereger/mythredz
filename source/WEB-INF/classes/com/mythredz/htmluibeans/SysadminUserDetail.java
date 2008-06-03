package com.mythredz.htmluibeans;


import com.mythredz.util.Str;
import com.mythredz.util.Num;
import com.mythredz.dao.*;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.email.EmailActivationSend;
import com.mythredz.email.LostPasswordSend;
import com.mythredz.money.MoveMoneyInAccountBalance;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.ValidationException;
import com.mythredz.helpers.DeleteUser;

import java.util.*;
import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class SysadminUserDetail implements Serializable {

    private int userid;
    private String firstname;
    private String lastname;
    private String email;
    private String facebookuid="";
    private boolean issysadmin = false;
    private String activitypin;
    private double amt;
    private String reason;
    private int fundstype=1;
    private List balances;
    private List transactions;
    private boolean isenabled = true;
    private User user;
    private boolean onlyshowsuccessfultransactions = false;
    private boolean onlyshownegativeamountbalance = false;
    private double resellerpercent;


    public SysadminUserDetail(){

    }



    public void initBean(){
        User user = null;
        if (com.mythredz.util.Num.isinteger(Pagez.getRequest().getParameter("userid"))){
            user = User.get(Integer.parseInt(Pagez.getRequest().getParameter("userid")));
        }
        if (user!=null && user.getUserid()>0){
            this.userid = user.getUserid();
            this.user = user;
            firstname = user.getFirstname();
            lastname = user.getLastname();
            email = user.getEmail();
            isenabled = user.getIsenabled();
            issysadmin = false;
            facebookuid = String.valueOf(user.getFacebookuserid());
            for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                Userrole userrole = iterator.next();
                if (userrole.getRoleid()== Userrole.SYSADMIN){
                    issysadmin = true;
                }
            }


            //Load balance info
            String negamtSql = "";
            if (onlyshownegativeamountbalance){
                negamtSql = " and amt<0 ";
            }
            List bals = HibernateUtil.getSession().createQuery("from Balance where userid='"+userid+"' "+negamtSql+" order by balanceid desc").setMaxResults(200).list();
            balances = new ArrayList<AccountBalanceListItem>();
            for (Iterator iterator = bals.iterator(); iterator.hasNext();) {
                Balance balance = (Balance) iterator.next();
                AccountBalanceListItem abli = new AccountBalanceListItem();
                abli.setAmt("$"+ Str.formatForMoney(balance.getAmt()));
                abli.setBalanceid(balance.getBalanceid());
                abli.setCurrentbalance("$"+ Str.formatForMoney(balance.getCurrentbalance()));
                abli.setDate(balance.getDate());
                abli.setDescription(balance.getDescription());
                abli.setUserid(balance.getUserid());
                StringBuffer fundstype = new StringBuffer();

                abli.setFundstype("Normal");
                balances.add(abli);
            }

            //Load transaction info
            String issuccessfulSql = "";
            if (onlyshowsuccessfultransactions){
                issuccessfulSql = " and issuccessful=true ";
            }
            List trans = HibernateUtil.getSession().createQuery("from Balancetransaction where userid='"+userid+"' "+issuccessfulSql+" order by balancetransactionid desc").setMaxResults(50).list();
            transactions = new ArrayList<AccountBalancetransactionListItem>();
            for (Iterator iterator = trans.iterator(); iterator.hasNext();) {
                Balancetransaction transaction = (Balancetransaction) iterator.next();
                AccountBalancetransactionListItem abli = new AccountBalancetransactionListItem();
                abli.setAmt("$"+ Str.formatForMoney(transaction.getAmt()));
                abli.setBalancetransactionid(transaction.getBalancetransactionid());
                abli.setDate(transaction.getDate());
                abli.setDescription(transaction.getDescription());
                abli.setNotes(transaction.getNotes());
                abli.setUserid(transaction.getUserid());
                abli.setIssuccessful(transaction.getIssuccessful());
                transactions.add(abli);
            }




        }
    }

    public void save() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("save() called");
        logger.debug("userid="+userid);
        logger.debug("firstname="+firstname);
        logger.debug("lastname="+lastname);
        logger.debug("email="+email);
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            if (Num.isinteger(facebookuid)){
                user.setFacebookuserid(Integer.parseInt(facebookuid));
            }
            try{user.save();}catch (Exception ex){logger.error("",ex);}
        }

    }


    public String sendresetpasswordemail() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            LostPasswordSend.sendLostPasswordEmail(user);
        }
        Pagez.getUserSession().setMessage("Password reset email sent");
        return "sysadminuserdetail";
    }

    public String reactivatebyemail() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            EmailActivationSend.sendActivationEmail(user);
        }
        Pagez.getUserSession().setMessage("Reactivation email sent");
        return "sysadminuserdetail";
    }

    public String toggleisenabled() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        User user = User.get(userid);
        if (user.getIsenabled()){
            //Disable
            user.setIsenabled(false);
            isenabled = false;
            Pagez.getUserSession().setMessage("Account disabled.");
        } else {
            // Enable
            user.setIsenabled(true);
            isenabled = true;
            Pagez.getUserSession().setMessage("Account enabled.");
        }
        try{user.save();}catch (Exception ex){logger.error("",ex);}
        return "sysadminuserdetail";
    }

    public String togglesysadminprivs() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("togglesysadminprivs()");
        if (activitypin.equals("yes, i want to do this")){
            activitypin = "";
            User user = User.get(userid);
            if (user!=null && user.getUserid()>0){
                issysadmin = false;
                for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                    Userrole userrole = iterator.next();
                    if (userrole.getRoleid()== Userrole.SYSADMIN){
                        issysadmin = true;
                    }
                }
                if (issysadmin){
                    logger.debug("is a sysadmin");
                    //@todo revoke sysadmin privs doesn't work
                    //int userroleidtodelete=0;
                    for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                        Userrole userrole = iterator.next();
                        logger.debug("found roleid="+userrole.getRoleid());
                        if (userrole.getRoleid()==Userrole.SYSADMIN){
                            logger.debug("removing it from iterator");
                            iterator.remove();
                        }
                    }
                    try{user.save();} catch (Exception ex){logger.error("",ex);}
                    issysadmin = false;
                    Pagez.getUserSession().setMessage("User is no longer a sysadmin");
                } else {
                    Userrole role = new Userrole();
                    role.setUserid(user.getUserid());
                    role.setRoleid(Userrole.SYSADMIN);
                    user.getUserroles().add(role);
                    try{role.save();} catch (Exception ex){logger.error("",ex);}
                    issysadmin = true;
                    Pagez.getUserSession().setMessage("User is now a sysadmin");
                }
                initBean();
            }
        } else {
            Pagez.getUserSession().setMessage("Activity Pin Not Correct.");
        }
        return "sysadminuserdetail";
    }

    public void deleteuser() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("deleteuser()");
        if (activitypin.equals("yes, i want to do this")){
            activitypin = "";
            User user = User.get(userid);
            DeleteUser.delete(user);
        } else {
            Pagez.getUserSession().setMessage("Activity Pin Not Correct.");
        }

    }



    public String giveusermoney() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){

            //Move it
            MoveMoneyInAccountBalance.pay(user, amt, "Manual transaction: "+reason);
        }
        initBean();
        Pagez.getUserSession().setMessage("$" + Str.formatForMoney(amt) + " given to user account balance.");
        return "sysadminuserdetail";
    }

    public String takeusermoney() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            //Move it
            MoveMoneyInAccountBalance.charge(user, amt, "Manual transaction: "+reason);
        }
        initBean();
        Pagez.getUserSession().setMessage("$"+ Str.formatForMoney(amt)+" taken from user account balance");
        return "sysadminuserdetail";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public String getActivitypin() {
        return activitypin;
    }

    public void setActivitypin(String activitypin) {
        this.activitypin = activitypin;
    }

    public boolean getIssysadmin() {
        return issysadmin;
    }

    public void setIssysadmin(boolean issysadmin) {
        this.issysadmin = issysadmin;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }


    public List getBalances() {
        return balances;
    }

    public void setBalances(List balances) {
        this.balances = balances;
    }

    public List getTransactions() {
        return transactions;
    }

    public void setTransactions(List transactions) {
        this.transactions = transactions;
    }


    public boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(boolean isenabled) {
        this.isenabled = isenabled;
    }





    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }







    public boolean getOnlyshowsuccessfultransactions() {
        return onlyshowsuccessfultransactions;
    }

    public void setOnlyshowsuccessfultransactions(boolean onlyshowsuccessfultransactions) {
        this.onlyshowsuccessfultransactions=onlyshowsuccessfultransactions;
    }

    public boolean getOnlyshownegativeamountbalance() {
        return onlyshownegativeamountbalance;
    }

    public void setOnlyshownegativeamountbalance(boolean onlyshownegativeamountbalance) {
        this.onlyshownegativeamountbalance=onlyshownegativeamountbalance;
    }

    public String getFacebookuid() {
        return facebookuid;
    }

    public void setFacebookuid(String facebookuid) {
        this.facebookuid=facebookuid;
    }

    public double getResellerpercent() {
        return resellerpercent;
    }

    public void setResellerpercent(double resellerpercent) {
        this.resellerpercent = resellerpercent;
    }

    public int getFundstype() {
        return fundstype;
    }

    public void setFundstype(int fundstype) {
        this.fundstype = fundstype;
    }
}
