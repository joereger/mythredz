package com.mythredz.smtp;

import org.subethamail.wiser.WiserMessage;
import org.apache.log4j.Logger;

import javax.mail.internet.MimeMessage;

/**
 * User: joereger
 * Date: Dec 17, 2008
 * Time: 2:33:55 PM
 */
public class ProcessIncomingMessage {

    public static void process(WiserMessageDneero wiserMessage){
        Logger logger = Logger.getLogger(ProcessIncomingMessage.class);
        try{
            String envelopeSender = wiserMessage.getEnvelopeSender();
            String envelopeReceiver = wiserMessage.getEnvelopeReceiver();
            logger.debug("envelopeSender="+envelopeSender);
            logger.debug("envelopeReceiver="+envelopeReceiver);
            MimeMessage message = wiserMessage.getMimeMessage();

            EmailApi emailApi = new EmailApi(message, envelopeSender, envelopeReceiver);
        } catch (Exception ex){
            logger.error("", ex);
        }

    }


}
