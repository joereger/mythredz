package com.mythredz.smtp;

import com.mythredz.dao.Emailaddress;
import com.mythredz.dao.User;
import com.mythredz.dao.Thred;
import com.mythredz.dao.Post;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Str;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.twitter.TwitterUpdate;
import com.mythredz.helpers.AfterPostingTodo;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Iterator;


/**
 * User: joereger
 * Date: Dec 17, 2008
 * Time: 10:27:00 PM
 */
public class ProcessBodyTextOfEmail {

    public static void process(String body, Emailaddress emailaddress){
        Logger logger = Logger.getLogger(ProcessBodyTextOfEmail.class);
        logger.debug( "process() called");
        User user = User.get(emailaddress.getUserid());
        if (user.getIsenabled()){
            List<Thred> thredz = getThredsForUser(user);
            for (Iterator<Thred> iterator=thredz.iterator(); iterator.hasNext();) {
                Thred thred=iterator.next();
                if (thred.getUserid()==emailaddress.getUserid()){
                    try{
                        String updateText = getUpdateTextForSpecificThred(body, thred);
                        if (updateText!=null && !updateText.trim().equals("")){
                            if (updateText.indexOf("Replace with what happened.")<0 || updateText.indexOf("Replace with what happened.")>5){
                                //We've got ourselves an update!
                                logger.debug("updateText for (thredid="+thred.getThredid()+") = "+updateText);
                                Post post=new Post();
                                post.setContents(updateText);
                                post.setDate(emailaddress.getDate());
                                post.setThredid(thred.getThredid());
                                try {
                                    post.save();
                                } catch (Exception ex) {
                                    logger.error("", ex);
                                }
                                //Update stuff after posting
                                AfterPostingTodo.doAfterPost(user, thred, post, true, true);
                            } else {
                                logger.debug("updateText had 'Replace this with what happened.' in there too close to beginning");
                            }
                        } else {
                            logger.debug("updateText==null or ''");
                        }
                    } catch (Exception ex) {
                        logger.error("", ex);
                    }
                } else {
                    logger.debug("Somebody manually changed the thredid in the email but used their own emailaddress");
                }
            }
        } else {
            //User's not enabled... notify them that they should reactivate their account?
            logger.debug("user not enabled");
        }
    }

    private static List<Thred> getThredsForUser(User user){
        Logger logger = Logger.getLogger(ProcessBodyTextOfEmail.class);
        logger.debug("getThredsForUser() called");
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", user.getUserid()))
                .setCacheable(true)
                .list();
        return threds;
    }



    private static String getUpdateTextForSpecificThred(String body, Thred thred){
        Logger logger = Logger.getLogger(ProcessBodyTextOfEmail.class);
        logger.debug("getUpdateTextForSpecificThred() called");
        StringBuffer out = new StringBuffer();
        if (body!=null && !body.equals("")){
            try{
                String a = "Start("+thred.getThredid()+")>";
                int aStart = body.indexOf(a);
                int aEnd = aStart + a.length();
                String b = "<End("+thred.getThredid()+")";
                int bStart = body.indexOf(b);
                logger.debug("body.length()="+body.length()+" a="+a+" aStart="+aStart+" a.length()="+a.length()+" aEnd="+aEnd+" b="+b+" bStart="+bStart+"");
                if (aEnd>0 && bStart>0){
                    String outStr = body.substring( aEnd  , bStart );
                    logger.debug("returning outStr="+outStr);
                    return outStr;
                }
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
        logger.debug("returning empty string");
        return out.toString();
    }



}
