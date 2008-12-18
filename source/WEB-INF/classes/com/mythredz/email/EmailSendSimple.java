package com.mythredz.email;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.Email;
import org.apache.log4j.Logger;

public class EmailSendSimple {


    public static void sendMail(Email email){
        Logger logger = Logger.getLogger(EmailSendSimple.class);
        try {
            //Kick off a thread to send the email
            EmailSendThreadSimple eThr = new EmailSendThreadSimple();
            eThr.email = email;
            eThr.startThread();
        }catch (Exception e) {
            logger.error("Error starting email thread.", e);
        }
    }

}
