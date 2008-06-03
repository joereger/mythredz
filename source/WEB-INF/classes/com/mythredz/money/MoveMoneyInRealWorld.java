package com.mythredz.money;

import com.mythredz.dao.User;
import com.mythredz.dao.Balance;
import com.mythredz.dao.Balancetransaction;
import com.mythredz.threadpool.ThreadPool;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.email.EmailSend;
import com.mythredz.email.EmailTemplateProcessor;
import com.mythredz.util.Time;
import com.mythredz.util.ErrorDissect;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Oct 11, 2006
 * Time: 1:41:48 PM
 */
public class MoveMoneyInRealWorld implements Runnable {

    public static double GLOBALMAXCHARGEPERTRANSACTION = 10000;

    private static ThreadPool tp;

    private User user;
    private double amttogiveuser;


    public MoveMoneyInRealWorld(User user, double amttogiveuser){
        this.user = user;
        this.amttogiveuser = amttogiveuser;
    }

    public void run(){
        giveUserThisAmt();
    }

    public void move(){
        if (tp==null){
            tp = new ThreadPool(15);
        }
        tp.assign(this);
    }

    private void giveUserThisAmt(){
        Logger logger = Logger.getLogger(MoveMoneyInRealWorld.class);

        //See if this needs to be broken into multiple transactions
        double amtremainder = 0;
        if (amttogiveuser>0){
            if (amttogiveuser>GLOBALMAXCHARGEPERTRANSACTION){
                amtremainder = amttogiveuser - GLOBALMAXCHARGEPERTRANSACTION;
                amttogiveuser = GLOBALMAXCHARGEPERTRANSACTION;
            }
        } else if (amttogiveuser<0){
            if (((-1)* amttogiveuser)>GLOBALMAXCHARGEPERTRANSACTION){
                amtremainder = amttogiveuser + GLOBALMAXCHARGEPERTRANSACTION;
                amttogiveuser = (-1)*GLOBALMAXCHARGEPERTRANSACTION;
            }
        } else {
            logger.debug("amttogiveuser=0 for userid="+user.getUserid());
            return;
        }

        //Get the payment method class based on the user's settings
        PaymentMethod pm = new PaymentMethodCreditCard(user, amttogiveuser);
        String desc = "Charge.";
        int paymentmethod = 0;
        if (amttogiveuser>.01){
            //Paying user??
        } else if (amttogiveuser<((-1)*.01)){
            //Charging user
            if (user.getChargemethod()==PaymentMethod.PAYMENTMETHODCREDITCARD){
                pm = new PaymentMethodCreditCard(user, amttogiveuser);
                paymentmethod = PaymentMethod.PAYMENTMETHODCREDITCARD;
                desc = "Charged Credit Card.";
            } else if (user.getChargemethod()==PaymentMethod.PAYMENTMETHODPAYPAL){
                pm = new PaymentMethodPayPal(user, amttogiveuser);
                paymentmethod = PaymentMethod.PAYMENTMETHODPAYPAL;
                desc = "Transfer from PayPal account.";
            }
        } else {
            //Set to zero because we're talking about less than a cent
            amttogiveuser = 0;
        }

        if(user!=null && (amttogiveuser<0 || amttogiveuser>0)){

            //Do the transaction
            pm.giveUserThisAmt();

            StringBuffer debug = new StringBuffer();
            try{
                if (pm==null){
                    debug.append("pm==null"+"<br/>\n");
                } else {
                    debug.append("pm!=null"+"<br/>\n");
                    debug.append("pm.getIssuccessful()="+pm.getIssuccessful()+"<br/>\n");
                    debug.append("pm.getCorrelationid()="+pm.getCorrelationid()+"<br/>\n");
                    debug.append("pm.getTransactionid()="+pm.getTransactionid()+"<br/>\n");
                    debug.append("pm.getNotes()="+pm.getNotes()+"<br/>\n");
                }
            } catch (Exception inex){
                EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "Error getting pm debug info", "amttogiveuser="+amttogiveuser+"<br/>\ndate="+ Time.dateformatcompactwithtime(Time.nowInUserTimezone("EST"))+"<br/>\nuserid="+user.getUserid()+"<br/>\nname="+user.getFirstname()+" "+user.getLastname()+"<br/>\nemail="+user.getEmail()+" error=<br/><br/>\n\n"+ ErrorDissect.dissect(inex));
                logger.error("",inex);
            }

            //Only affect the account balance if the real-world transaction was successful
            if (pm.getIssuccessful()){
                debug.append("apparently pm.getIssuccessful()=true and we're inside the Balance update statement"+"<br/>\n");
                Balance balance = new Balance();
                balance.setAmt((-1)*amttogiveuser);
                balance.setDate(new Date());
                balance.setDescription(desc);
                CurrentBalanceCalculator cbc;
                try{
                    debug.append("about to run CurrentBalanceCalculator"+"<br/>\n");
                    cbc = new CurrentBalanceCalculator(user);
                    balance.setCurrentbalance(cbc.getCurrentbalance() - amttogiveuser);
                    balance.setUserid(user.getUserid());
                    try{balance.save();}catch (Exception ex){
                        debug.append("ERROR writing balance to db"+"<br/>\n");
                        SendXMPPMessage xmpp2 = new SendXMPPMessage(SendXMPPMessage.GROUP_SYSADMINS, "WRITE TO DATABASE FAILED!!! Successful Move Money in Real World: amttogiveuser=$"+amttogiveuser+" to/from userid="+user.getUserid()+" "+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+")");
                        xmpp2.send();
                        EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "dNeero balance write failed", "Failed Move Money in Real World: amttogiveuser=$"+amttogiveuser+"<br/>to/from userid="+user.getUserid()+"<br/>"+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+")<br/>"+ErrorDissect.dissect(ex));
                        logger.error("",ex);
                    }
                    debug.append("done calling Balance.save()"+"<br/>\n");
                    //Notify via XMPP
                    SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_CUSTOMERSUPPORT, "Successful Move Money in Real World: amttogiveuser=$"+amttogiveuser+"<br/>\nto/from "+ user.getFirstname() + " " + user.getLastname() + "<br/>\n("+user.getEmail()+")");
                    xmpp.send();
                } catch (Exception ex){
                    debug.append("ERROR writing balance to db (lower catch)"+"<br/>\n");
                    logger.error(ex);
                    //Notify via XMPP
                    SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_CUSTOMERSUPPORT, "Failed Move Money in Real World: amttogiveuser=$"+amttogiveuser+" to/from "+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+") Notes: "+pm.getNotes());
                    xmpp.send();
                    EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "dNeero balance write failed", "Failed Move Money in Real World: amttogiveuser=$"+amttogiveuser+" to/from userid="+user.getUserid()+" "+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+") "+ErrorDissect.dissect(ex));
                }
            } else {
                debug.append("apparently pm.getIssuccessful()=false so the Balance update was never called"+"<br/>\n");
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_CUSTOMERSUPPORT, "Failed Move Money in Real World: amttogiveuser=$"+amttogiveuser+" to/from "+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+") Notes: "+pm.getNotes());
                xmpp.send();
                EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "dNeero !pm.getIssuccessful()", "dNeero !pm.getIssuccessful():<br/>amttogiveuser=$"+amttogiveuser+"<br/>to/from userid="+user.getUserid()+"<br/>"+ user.getFirstname() + " " + user.getLastname() + "<br/>("+user.getEmail()+")<br/>debug<br/>"+debug);
            }

            //Always record the transaction itself, even if it fails... this does not affect the account balance
            try{
                debug.append("about to start recording Balancetransaction"+"<br/>\n");
                Balancetransaction balancetransaction = new Balancetransaction();
                balancetransaction.setUserid(user.getUserid());
                debug.append("have set userid"+"<br/>\n");
                balancetransaction.setAmt(amttogiveuser);
                debug.append("have set amttogiveuser"+"<br/>\n");
                balancetransaction.setDate(new Date());
                debug.append("have set date"+"<br/>\n");
                balancetransaction.setDescription(desc);
                debug.append("have set desc"+"<br/>\n");
                balancetransaction.setIssuccessful(pm.getIssuccessful());
                debug.append("have set issuccessful"+"<br/>\n");
                balancetransaction.setPaymentmethod(paymentmethod);
                debug.append("have set paymentmethod"+"<br/>\n");
                if (pm.getNotes()!=null){
                    debug.append("notes not null"+"<br/>\n");
                    balancetransaction.setNotes(pm.getNotes());
                } else {
                    debug.append("notes null so setting to empty string"+"<br/>\n");
                    balancetransaction.setNotes("");
                }
                debug.append("have set notes"+"<br/>\n");
                if (pm.getCorrelationid()!=null){
                    debug.append("correlationid not null"+"<br/>\n");
                    balancetransaction.setCorrelationid(pm.getCorrelationid());
                } else {
                    debug.append("correlationid null so setting to empty string"+"<br/>\n");
                    balancetransaction.setCorrelationid("");
                }
                debug.append("have set correlationid"+"<br/>\n");
                if (pm.getTransactionid()!=null){
                    debug.append("transactionid not null"+"<br/>\n");
                    balancetransaction.setTransactionid(pm.getTransactionid());
                } else {
                    debug.append("transactionid is null to setting to empty string"+"<br/>\n");
                    balancetransaction.setTransactionid("");
                }
                debug.append("have set transactionid"+"<br/>\n");
                balancetransaction.save();
                debug.append("have completed Balancetransaction.save()"+"<br/>\n");
            }catch (Exception ex){
                EmailTemplateProcessor.sendGenericEmail("joe@joereger.com", "dNeero balancetransaction write failed", "amttogiveuser="+amttogiveuser+"<br/>\ndate="+ Time.dateformatcompactwithtime(Time.nowInUserTimezone("EST"))+"<br/>\nuserid="+user.getUserid()+"<br/>\nname="+user.getFirstname()+" "+user.getLastname()+"<br/>\nemail="+user.getEmail()+"<br/><br/>\n debug:<br/><br/>\n"+debug.toString()+"<br/>\n error:<br/>\n\n"+ ErrorDissect.dissect(ex));
                logger.error("",ex);
            }

            //Now charge the remainder
            if (amtremainder!=0){
                amttogiveuser = amtremainder;
                giveUserThisAmt();
            }
        } else {
            if (user==null){
                logger.error("Null user being passed to MoveMoneyInRealWorld.java");
            }
        }
    }



}
