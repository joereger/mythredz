package com.mythredz.helpers;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.util.Vector;
import java.net.URL;

/**
 * User: Joe Reger Jr
 * Date: Jun 6, 2007
 * Time: 4:18:58 PM
 */
public class Pingomatic {

    public static void ping(String name, String url, String rssUrl){
        Logger logger = Logger.getLogger(Pingomatic.class);
        try {
        
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://rpc.pingomatic.com/"));
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);
            Object[] params = new Object[3];
            params[0] = name;
            params[1] = url;
            params[2] = rssUrl;
            client.executeAsync("weblogUpdates.ping", params, new PingomaticCallback());

        } catch (Exception e) {
            logger.error("", e);
        }

    }



}
