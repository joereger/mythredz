package com.mythredz.htmluibeans;




import org.apache.log4j.Logger;



import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Iterator;
import java.io.Serializable;

import com.mythredz.htmlui.Pagez;


/**
 * User: Joe Reger Jr
 * Date: Nov 28, 2006
 * Time: 3:57:30 PM
 */
public class SysadminSessions implements Serializable {

    private String sessionsashtml;

    public SysadminSessions(){

    }



    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            StringBuffer out = new StringBuffer();
            //ServletExternalContextImpl servExtContext = (ServletExternalContextImpl)extContext;
            sessionsashtml = "";

//            Session[] sessions = Pagez.getRequest().;
//
//
//            for (int i = 0; i < sessions.length; i++) {
//                Session session = sessions[i];
//                out.append("<br><br>");
//                out.append("<b>");
//                out.append(session.getId());
//                out.append("<b>");
//                out.append("<br>");
//                HttpSession httpSession = (HttpSession)session.getSession();
//                while (httpSession.getAttributeNames().hasMoreElements()) {
//                    String name = (String) httpSession.getAttributeNames().nextElement();
//                    out.append("name="+httpSession.getAttribute(name).toString());
//                }
//            }
        } catch (Exception ex){
            sessionsashtml = ex.getMessage();
            logger.debug(ex);
        }
    }

    public String getSessionsashtml() {
        return sessionsashtml;
    }

    public void setSessionsashtml(String sessionsashtml) {
        this.sessionsashtml = sessionsashtml;
    }
}
