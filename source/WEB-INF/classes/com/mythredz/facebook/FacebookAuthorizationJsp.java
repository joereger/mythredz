package com.mythredz.facebook;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import com.mythredz.util.Num;
import com.mythredz.util.Time;
import com.mythredz.util.DateDiff;
import com.mythredz.systemprops.SystemProperty;
import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.xmpp.SendXMPPMessage;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.UserSession;
import com.mythredz.cache.providers.CacheFactory;
import com.facebook.api.FacebookRestClient;
import com.facebook.api.FacebookException;

import java.util.List;
import java.util.Iterator;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jul 11, 2007
 * Time: 10:26:53 PM
 */
public class FacebookAuthorizationJsp {

    public static int MAXSESSIONAGEINSECONDS = 3600; //One hour = 3600

    public static void doAuth(){

        //NOTE: NO REDIRETCS IN THIS PAGE!!!

        Logger logger = Logger.getLogger(FacebookAuthorizationJsp.class);
        logger.debug("starting FacebookAuthorization and isfacebookui="+ Pagez.getUserSession().getIsfacebookui());
        try{
            //Set referred by userid
            try{
                if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
                    String[] split = Pagez.getRequest().getParameter("action").split("-");
                    if (split.length>=3){
                        //Set the referredbyuserid value in the session
                        if (split[2]!=null && Num.isinteger(split[2])){
                            Pagez.getUserSession().setReferredbyOnlyUsedForSignup(Integer.parseInt(split[2]));
                        }
                    }
                }
            } catch (Exception ex) {logger.error("",ex);}

            //Set the userSession var isfacebookui if we see anything that remotely resembles facebook activity
            if (Pagez.getRequest().getParameter("auth_token")!=null){
                Pagez.getUserSession().setIsfacebookui(true);
            }
            if (Pagez.getRequest().getParameter("fb_sig_session_key")!=null){
                Pagez.getUserSession().setIsfacebookui(true);
            }
            if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").indexOf("showsurvey")>-1){
                Pagez.getUserSession().setIsfacebookui(true);
            }

            //Need a session key
            boolean foundanewfacebooksessionkey = false;
            //auth_token should immediately be traded in for a valid fb_sig_session_key
            if ((Pagez.getRequest().getParameter("auth_token")!=null && !Pagez.getRequest().getParameter("auth_token").trim().equals(""))){
                logger.debug("auth_token found in request... will try to convert to session_key");
                try{
                    FacebookRestClient facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), Pagez.getUserSession().getFacebookSessionKey());
                    String facebooksessionkey = facebookRestClient.auth_getSession(Pagez.getRequest().getParameter("auth_token").trim());
                    if (facebooksessionkey!=null && !facebooksessionkey.equals("")){
                        Pagez.getUserSession().setFacebookSessionKey(facebooksessionkey);
                        foundanewfacebooksessionkey = true;
                    }
                } catch (Exception ex) {
                    logger.warn("failed to convert auth_token to facebooksessionkey",ex);
                }
            }
            //No auth_token was sent (it's only sent for new apps and new logins, etc) so look to session_key
            logger.debug("checking fb_sig_session_key which is much more reliable");
            if ((Pagez.getRequest().getParameter("fb_sig_session_key")!=null && !Pagez.getRequest().getParameter("fb_sig_session_key").trim().equals(""))){
                logger.debug("found a fb_sig_session_key in request");
                Pagez.getUserSession().setFacebookSessionKey((Pagez.getRequest().getParameter("fb_sig_session_key").trim()));
                foundanewfacebooksessionkey = true;
            } else {
                logger.debug("no fb_sig_session_key found in request either.");
            }
            //If we don't have a session key, abort
            if (!foundanewfacebooksessionkey){
                logger.debug("foundanewfacebooksessionkey=false so aborting FacebookAuthorization");
                return;
            }

            //This could cause problems.  The facebook user doesn't get to this point except on their first click from the facebook ui.
            //I try to pull their session from the cache (it won't be there).
            //Then I put the populated session back into the cache.  But it's already in the Jsf session... the cache here won't ever be used again.
            //And, the problem, if they do come back from the ui, they'll get a stale session... I don't think I need my own session cache for dNeero.

            //Pull userSession from cache
            boolean foundSessionInCache = false;
            //Turn cache on and off easily
            boolean usecache = true;
            if (usecache){
                Object obj = CacheFactory.getCacheProvider().get(Pagez.getUserSession().getFacebookSessionKey(), "FacebookUserSession");
                if (obj!=null && (obj instanceof UserSession)){
                    UserSession userSession = (UserSession)obj;
                    int secondsOld = 0;
                    secondsOld=DateDiff.dateDiff("second", Calendar.getInstance(), userSession.getCreatedate());
                    logger.debug("session in cache is "+secondsOld+" seconds old");
                    if (secondsOld>MAXSESSIONAGEINSECONDS){
                        refreshSomeTimeSensitiveElementsOfUserSession(userSession);
                    }
                    logger.debug("found a userSession in the cache");
                    Pagez.setUserSessionAndUpdateCache(userSession);
                    foundSessionInCache = true;
                } else {
                    logger.debug("no userSession in cache");
                }
            }

            //In general try not to handle request vars below this line
            //I only want to run this stuff when I see a new Facebook session key...
            if (!foundSessionInCache) {
                logger.debug("running heavy Facebook user setup with api calls due to new facebooksessionkey");

                //Go get some details on this facebookuser
                FacebookRestClient facebookRestClient = null;
                try {
                    facebookRestClient = new FacebookRestClient(SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_KEY), SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_API_SECRET), Pagez.getUserSession().getFacebookSessionKey());
                    int loggedinfacebookuid = 0;
                    Long loggedinfacebookuidLong = facebookRestClient.users_getLoggedInUser();
                    if (Num.isinteger(String.valueOf(loggedinfacebookuidLong))){
                        loggedinfacebookuid = Integer.parseInt(String.valueOf(loggedinfacebookuidLong));
                    }
                    Pagez.getUserSession().setFacebookUser(new FacebookUser(loggedinfacebookuid, Pagez.getUserSession().getFacebookSessionKey()));
                } catch (FacebookException fex) {
                    logger.error("Facebook Error fex", fex);
                }

                //If we have a facebook user to work with
                if (Pagez.getUserSession().getFacebookUser()!=null && !Pagez.getUserSession().getFacebookUser().getUid().trim().equals("")){
                    //Set facebookui
                    Pagez.getUserSession().setIsfacebookui(true);
                    //Save referral state
                    try{
                        if (Pagez.getUserSession().getReferredbyOnlyUsedForSignup()>0){
                            if (Pagez.getUserSession().getFacebookUser()!=null && Num.isinteger(Pagez.getUserSession().getFacebookUser().getUid())){
                                FacebookPendingReferrals.addReferredBy(Pagez.getUserSession().getReferredbyOnlyUsedForSignup(), Integer.parseInt(Pagez.getUserSession().getFacebookUser().getUid()));
                            }
                        }
                    } catch (Exception ex){
                        logger.error("",ex);
                    }
                    //Get user's friends
                    FacebookApiWrapper faw = new FacebookApiWrapper(Pagez.getUserSession());
                    Pagez.getUserSession().setFacebookFriends(faw.getFriends());
                    //See if we have this facebook user as a dNeero user
                    User user = null;
                    if (Num.isinteger(Pagez.getUserSession().getFacebookUser().getUid())){
                        user = getdNeeroUserFromFacebookUserid(Integer.parseInt(Pagez.getUserSession().getFacebookUser().getUid()));
                    }
                    if (user!=null && user.getUserid()>0){
                        //Is already a dNeero user
                        Pagez.getUserSession().setUser(user);
                        Pagez.getUserSession().setIsloggedin(true);
                        logger.debug("dNeero Facebook Login: "+ user.getFirstname() + " " + user.getLastname() + " ("+user.getEmail()+") (Facebook.userid="+user.getFacebookuserid()+")");
                        //If their account is marked as having removed the app but facebook says they've got it added, update the User object
                        if (Pagez.getUserSession().getFacebookUser().getHas_added_app() && user.getIsfacebookappremoved()){
                            user.setIsfacebookappremoved(false);
                            user.setFacebookappremoveddate(user.getCreatedate());
                            try {user.save();} catch (Exception ex) {logger.error("",ex);}
                        }
                        //Notify via XMPP
                        SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook Login: "+ user.getFirstname() + " " + user.getLastname() + " (email="+user.getEmail()+") (facebook.uid="+user.getFacebookuserid()+")");
                        xmpp.send();
                    } else {
                        //Is not a dNeero user yet... make sure there's no user in the session
                        Pagez.getUserSession().setUser(null);
                        Pagez.getUserSession().setIsloggedin(false);
                        logger.debug("Facebook user added app, considering taking surveys.  facebookSessionKey="+Pagez.getUserSession().getFacebookSessionKey());
                        //Notify via XMPP
                        SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook user '"+Pagez.getUserSession().getFacebookUser().getFirst_name()+" "+Pagez.getUserSession().getFacebookUser().getLast_name()+"' starts session.  Not yet a dNeero user.");
                        xmpp.send();
                        //Could redirect to Facebook new user welcome screen here.
                    }
                } else {
                    logger.debug("userSession.getFacebookUser() is empty after calling facebook api");
                }
                //Save UserSession in Cache
                if (usecache){
                    //Pagez.setUserSessionAndUpdateCache(Pagez.getUserSession());
                    CacheFactory.getCacheProvider().put(Pagez.getUserSession().getFacebookSessionKey(), "FacebookUserSession", Pagez.getUserSession());
                }
            } else {
                logger.debug("didn't find a new facebooksessionkey so didn't make api call to load facebook user");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("",ex);
        }

        //If is coming from facebook but hasn't added app, make them add it
        if (Pagez.getUserSession().getIsfacebookui() && Pagez.getUserSession().getFacebookUser()!=null && !Pagez.getUserSession().getFacebookUser().getHas_added_app()){
            //UrlSplitter urlSplitter = new UrlSplitter(Jsf.getHttpServletRequest());
            //If the showsurvey var isn't set in the incoming request, make them add it... this is currently the only exception
            if (Pagez.getRequest().getParameter("stoplooping")==null || !Pagez.getRequest().getParameter("stoplooping").equals("1")){
                logger.debug("redirecting to facebook add app page");
                try{Pagez.sendRedirect("/facebooklandingpage.jsp?stoplooping=1&action="+Pagez.getRequest().getParameter("action"));return;}catch(Exception ex){logger.error("",ex);}
            }
        }

        logger.debug("leaving FacebookAuthorization and isfacebookui="+Pagez.getUserSession().getIsfacebookui() +"");
    }



    private static User getdNeeroUserFromFacebookUserid(int facebookuserid){
        Logger logger = Logger.getLogger(FacebookAuthorizationJsp.class);
        logger.debug("looking for user with facebookid="+facebookuserid);
        List<User> users = HibernateUtil.getSession().createCriteria(User.class)
                                           .add(Restrictions.eq("facebookuserid", facebookuserid))
                                           .setCacheable(true)
                                           .list();
        if (users!=null && users.size()>1){
            logger.error("More than one dNeero user for facebookuserid="+facebookuserid);
        }
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            logger.debug("returning userid="+user.getUserid()+" for facebookid="+facebookuserid);
            return user;
        }
        logger.debug("returning null user for facebookid="+facebookuserid);
        return null;
    }

    private static void refreshSomeTimeSensitiveElementsOfUserSession(UserSession userSession){
        if (userSession!=null){
            User user = null;
            //Find the user
            if (Num.isinteger(userSession.getFacebookUser().getUid())){
                user = getdNeeroUserFromFacebookUserid(Integer.parseInt(userSession.getFacebookUser().getUid()));
            }
            //If there is a user
            if (user!=null && user.getUserid()>0){
                //Friends
                FacebookApiWrapper faw = new FacebookApiWrapper(userSession);
                userSession.setFacebookFriends(faw.getFriends());
            }
            //Reset the create date
            userSession.setCreatedate(Calendar.getInstance());
            if (userSession.getFacebookUser()!=null){
                //Notify via XMPP
                SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_DEBUG, "Facebook user '"+userSession.getFacebookUser().getFirst_name()+" "+userSession.getFacebookUser().getLast_name()+"' had session refreshed after "+MAXSESSIONAGEINSECONDS+" seconds of usage.");
                xmpp.send();
            }
        }
    }


}
