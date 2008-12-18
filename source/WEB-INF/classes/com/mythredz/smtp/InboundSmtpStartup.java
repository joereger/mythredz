package com.mythredz.smtp;

import com.mythredz.systemprops.InstanceProperties;


/**
 * User: joereger
 * Date: Dec 17, 2008
 * Time: 2:37:15 PM
 */
public class InboundSmtpStartup {

    private static WiserDneero wiserDneero;

    public static void makeSureServerIsUp(){
        if (wiserDneero==null){
            if (InstanceProperties.getRunEmailListenerOnThisInstance()){
                wiserDneero= new WiserDneero();
                wiserDneero.setPort(25); // Default is 25
                wiserDneero.setBindAddr(InstanceProperties.getEmailListenerIP());
                wiserDneero.start();
            }
        }
    }

}
