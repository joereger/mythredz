package com.mythredz.email;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.Email;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Enumeration;

import com.mythredz.threadpool.ThreadPool;
import com.mythredz.systemprops.SystemProperty;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Address;
import javax.mail.Header;
import javax.mail.BodyPart;

/**
 * Sends automates email subscription emails
 */
public class EmailSendThreadSimple implements Runnable, Serializable {

    public Email email;
    public static String DEFAULTFROM = "joe@joereger.com";
    private static ThreadPool tp;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public EmailSendThreadSimple() {

    }

    public void run() {

        try{
            logger.debug("Start sending email subject:"+ email.getSubject());
            logger.debug("SystemProperty.PROP_SMTPOUTBOUNDSERVER="+ SystemProperty.getProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER));
            if (email !=null){
                logger.debug("an email was found... sending");
                email.setHostName(SystemProperty.getProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER));
                email.send();
            } else {
                logger.debug("not sending");
                if (email ==null){
                    logger.debug("email is null");
                } else {
                    logger.debug("no idea why it didn't send");
                }
            }
            logger.debug("End sending email subject:"+ email.getSubject());
        } catch (Exception e){
            logger.error("top try/catch",e);
            e.printStackTrace();
        } finally {
            try{
                if (email !=null && email.getMimeMessage()!=null && email.getMimeMessage().getAllRecipients()!=null && email.getMimeMessage().getAllRecipients().length>0){
                    logEmailSend(email);
                }
            } catch (Exception ex){logger.error("try/catch inside finally", ex);ex.printStackTrace();}
        }
    }

    public void startThread(){
        if (tp ==null){
            tp= new ThreadPool(15);
        }
        tp.assign(this);
    }

    private void logEmailSend(Email emailtolog){
        try{
            MimeMessage message = emailtolog.getMimeMessage();
            Address[] recipients = message.getAllRecipients();
            for (int i = 0; i < recipients.length; i++) {
                Address recipient = recipients[i];
                logger.debug("   EMAIL: To: "+recipient.toString());
            }
            Address[] from = message.getFrom();
            for (int i = 0; i < from.length; i++) {
                Address fromaddr = from[i];
                logger.debug("   EMAIL: From: "+fromaddr.toString());
            }
            Address[] replyto = message.getReplyTo();
            for (int i = 0; i < replyto.length; i++) {
                Address replytoaddr = replyto[i];
                logger.debug("   EMAIL: ReplyTo: "+replytoaddr.toString());
            }
            Enumeration enumer = message.getAllHeaders();
            while (enumer.hasMoreElements()) {
                Header header = (Header)enumer.nextElement();
                logger.debug("   EMAIL: Header: "+header.getName()+"="+header.getValue());
            }
            logger.debug("   EMAIL: Sender: "+String.valueOf(message.getSender()));
            logger.debug("   EMAIL: Subject: "+String.valueOf(message.getSubject()));
            logger.debug("   EMAIL: Encoding: "+String.valueOf(message.getEncoding()));
            if (message.getContent() instanceof MimeMultipart){
                MimeMultipart mimemessage = (MimeMultipart)message.getContent();
                for(int i=0; i<mimemessage.getCount(); i++){
                    BodyPart bodypart = mimemessage.getBodyPart(i);
                    logger.debug("   EMAIL: Content: "+String.valueOf(bodypart.getContent().toString()));
                }
            } else {
                logger.debug("   EMAIL: Content: "+String.valueOf(message.getContent().toString()));
            }
        } catch (Exception ex){
            logger.error("error in logEmailSend()", ex);
            ex.printStackTrace();
        }
    }




}
