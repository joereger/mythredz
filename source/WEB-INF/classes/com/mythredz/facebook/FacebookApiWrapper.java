package com.mythredz.facebook;


import com.mythredz.systemprops.SystemProperty;
import com.mythredz.systemprops.BaseUrl;
import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Str;
import com.mythredz.util.Num;
import com.mythredz.htmlui.UserSession;
import com.mythredz.htmlui.Pagez;
import com.facebook.api.FacebookRestClient;
import com.facebook.api.FacebookSignatureUtil;
import com.facebook.api.TemplatizedAction;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.w3c.dom.Document;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.Element;

import java.util.*;
import java.net.URLEncoder;
import java.net.URL;

/**
 * User: Joe Reger Jr
 * Date: Jul 16, 2007
 * Time: 10:41:57 AM
 */
public class FacebookApiWrapper {

    private UserSession userSession = null;
    private String facebookSessionKey = "";
    private boolean issessionok = false;
    


    public FacebookApiWrapper(UserSession userSession){
        Logger logger = Logger.getLogger(this.getClass().getName());
        this.userSession = userSession;
        if (userSession.getFacebookSessionKey()!=null && !userSession.getFacebookSessionKey().trim().equals("")){
            facebookSessionKey = userSession.getFacebookSessionKey().trim();
            try{
                FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
                if (userSession.getUser()!=null && userSession.getUser().getUserid()>0){
                    if (userSession.getUser().getFacebookuserid()>0){
                        if (userSession.getUser().getFacebookuserid()==facebookRestClient.users_getLoggedInUser()){
                            issessionok = true;
                        } else {
                            logger.debug("userSession.getUser().getFacebookuserid()!=facebookRestClient.users_getLoggedInUser()");
                        }
                    } else {
                        logger.debug("userSession.getUser() (userid="+userSession.getUser().getUserid()+") passed to FacebookApiWrapper does not have a saved facebookuserid");
                    }
                } else {
                    if (userSession.getFacebookUser()!=null && userSession.getFacebookUser().getUid()!=null && userSession.getFacebookUser().getUid().length()>0){
                        issessionok = true;
                    } else {
                        logger.debug("don't have a facebookuserid to work with");
                    }
                }
            } catch (Exception ex){
                logger.error("",ex);
            }
        }
    }




    public void postToFeed(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (issessionok){
            try{

                TemplatizedAction action = new TemplatizedAction("", "");
                action.addTitleParam("earnings", "");
                action.addTitleParam("forcharity", fbApiClean(""));

                FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
                facebookRestClient.feed_PublishTemplatizedAction(action);
            } catch (Exception ex){logger.error("",ex);}
        } else {logger.debug("Can't execute because issessionok = false");}
    }

    private String fbApiClean(String in){
        //For some reason FB doesn't allow "message" in a feed story
        return in.replaceAll("message", "m3ssage");
    }

    public void updateProfile(User user){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("Starting to create FBML for profile");
        if (issessionok){
            try{
                StringBuffer fbml = new StringBuffer();
                double totalearnings = 0;
                int count = 0;
                fbml.append("<center>");
                fbml.append("<font style=\"font-size: 14px; color: #cccccc; font-weight: bold;\">");
                fbml.append("Most Recent Surveys I've Taken");
                fbml.append("</font>");
                fbml.append("</center>");
                fbml.append("<br/>");



                CharSequence cs = fbml.subSequence(0, fbml.length());
                FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
                boolean success = facebookRestClient.profile_setFBML(cs, Long.parseLong(String.valueOf(user.getFacebookuserid())));
                if (success){
                    logger.debug("Apparently the setFBML was successful.");
                } else {
                    logger.debug("Apparently the setFBML was not successful.");
                }
            } catch (Exception ex){logger.error("",ex);}
        } else {logger.debug("Can't execute because issessionok = false");}
    }

    public ArrayList<Integer> getFriendUids(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        ArrayList<Integer> friends = new ArrayList<Integer>();
        if (issessionok){
            try{

                if (Pagez.getRequest().getParameter("fb_sig_friends")!=null){
                    ArrayList<Integer> out = new ArrayList<Integer>();
                    String[] splitfriends =  Pagez.getRequest().getParameter("fb_sig_friends").split(",");
                    for (int i=0; i<splitfriends.length; i++) {
                        String splitfriend=splitfriends[i];
                        if (Num.isinteger(splitfriend)){
                            out.add(Integer.parseInt(splitfriend));
                        }
                    }
                    logger.debug("returning friends from fb_sig_friends");
                    return out;
                }

                logger.debug("making an api call to get friends");
                //Set up the facebook rest client
                FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
                //Get a list of uids
                Document w3cDoc = facebookRestClient.friends_get();
                DOMBuilder builder = new DOMBuilder();
                org.jdom.Document jdomDoc = builder.build(w3cDoc);
                logger.debug("Start Facebook API Friends Resp:");
                XMLOutputter outp = new XMLOutputter();
                outp.output(jdomDoc, System.out);
                logger.debug(":End Facebook API Friends Resp");
                Element root = jdomDoc.getRootElement();
                outputChildrenToLogger(root, 0);
                //Extract the uids
                List allChildren = root.getChildren();
                for (Iterator iterator = allChildren.iterator(); iterator.hasNext();) {
                    Element element = (Element) iterator.next();
                    if (element.getName().equals("uid")){
                        if(Num.isinteger(element.getTextTrim())){
                            friends.add(Integer.parseInt(element.getTextTrim()));
                        }
                    }
                }
            } catch (Exception ex){logger.error("",ex);}
        } else {logger.debug("Can't execute because issessionok = false");}
        return friends;
    }


    public ArrayList<FacebookUser> getFriends(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("begin getFriends() facebookSessionKey="+facebookSessionKey);
        ArrayList<FacebookUser> friends = new ArrayList<FacebookUser>();
        if (issessionok){
            try{
                //Set up the facebook rest client
                FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
                //Get the list of uids
                ArrayList<Integer> uids = getFriendUids();

                if (uids!=null && uids.size()>0){
                    logger.debug("getFriends() uids not null facebookSessionKey="+facebookSessionKey);
                    //Create fql based on the list of uids
                    StringBuffer fqlWhere = new StringBuffer();
                    fqlWhere.append("(uid IN (");
                    for (Iterator iterator = uids.iterator(); iterator.hasNext();) {
                        Integer uid = (Integer) iterator.next();
                        fqlWhere.append(uid);
                        if (iterator.hasNext()){
                            fqlWhere.append(", ");
                        }
                    }
                    fqlWhere.append("))");
                    //Go back and get all the important info
                    String fql = "SELECT "+FacebookUser.sqlListOfCols+" FROM user WHERE "+fqlWhere;
                    Document w3cDoc2 = facebookRestClient.fql_query(fql.subSequence(0,fql.length()));
                    DOMBuilder builder2 = new DOMBuilder();
                    org.jdom.Document jdomDoc2 = builder2.build(w3cDoc2);
                    logger.debug("Start Facebook FQL Resp: "+fql);
                    XMLOutputter outp2 = new XMLOutputter();
                    outp2.output(jdomDoc2, System.out);
                    logger.debug(":End Facebook FQL Resp");
                    Element root2 = jdomDoc2.getRootElement();
                    //Iterate each child
                    List fbusers = root2.getChildren();
                    for (Iterator iterator = fbusers.iterator(); iterator.hasNext();) {
                        Element fbuser = (Element) iterator.next();
                        if (fbuser.getName().equals("user")){
                            FacebookUser facebookUser = new FacebookUser(fbuser);
                            if (facebookUser.getUid().length()>0){
                                friends.add(facebookUser);
                            }
                        }
                    }
                }
            } catch (Exception ex){logger.error("",ex); ex.printStackTrace();}
        } else {logger.debug("Can't execute because issessionok = false");}
        logger.debug("end getFriends() facebookSessionKey="+facebookSessionKey);
        return friends;
    }





    private FacebookUser getFacebookUserByUid(ArrayList<FacebookUser> facebookUsers, String uid){
        for (Iterator<FacebookUser> iterator = facebookUsers.iterator(); iterator.hasNext();) {
            FacebookUser facebookUser = iterator.next();
            if (facebookUser.getUid().equals(uid)){
                return facebookUser;
            }
        }
        return null;
    }




    

    public String inviteFriendsTodNeero(ArrayList<Long> uids){
        Logger logger = Logger.getLogger(this.getClass().getName());
        FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), facebookSessionKey);
        String type = "dNeero";
        CharSequence typeChars = type.subSequence(0, type.length());
        StringBuffer content = new StringBuffer();
        content.append("You've been invited to the social survey app called dNeero that allows you to earn real money taking surveys and sharing your answers with your friends.");
        content.append("<fb:req-choice url=\"http://apps.facebook.com/"+SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_APP_NAME)+"\" label=\"Check it Out\" />");
        CharSequence contentChars = content.subSequence(0, content.length());
        URL imgUrl = null;
        try{
            imgUrl = new URL("http", SystemProperty.getProp(SystemProperty.PROP_BASEURL), "/images/dneero-logo-100x100.png");
        } catch (Exception ex){
            logger.error("",ex);
        }
        try{
            URL url = facebookRestClient.notifications_sendRequest(uids, typeChars, contentChars, imgUrl, true);
            if (url!=null){
                logger.debug("FacebookAPI returned: " + url.toString());
                return url.toString();
            }

        } catch (Exception ex){
            logger.error("",ex);
        }
        return "";
    }





    public static Element getChild(Element el, String name){
        List allChildren = el.getChildren();
        for (Iterator iterator = allChildren.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.getName().equals(name)){
                return element;
            }
        }
        return null;
    }

    public static void outputChildrenToLogger(Element el, int level){
        Logger logger = Logger.getLogger(FacebookApiWrapper.class);
        level = level + 1;
        String indent = "";
        for(int i=0; i<level; i++){
            indent = indent + "-";
        }
        List allChildren = el.getChildren();
        for (Iterator iterator = allChildren.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            //logger.debug(indent + " " + element.getName());
            outputChildrenToLogger(element, level);
        }
    }



    public boolean getIssessionok() {
        return issessionok;
    }

    public String getFacebookSessionKey() {
        return facebookSessionKey;
    }


}
