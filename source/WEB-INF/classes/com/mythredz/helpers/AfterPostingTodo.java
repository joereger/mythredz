package com.mythredz.helpers;

import com.mythredz.dao.User;
import com.mythredz.dao.Thred;
import com.mythredz.dao.Post;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.util.Str;
import com.mythredz.twitter.TwitterUpdate;
import com.mythredz.pingfm.PingfmUpdate;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * User: joereger
 * Date: Dec 22, 2008
 * Time: 10:46:13 AM
 */
public class AfterPostingTodo {

    public static void doAfterPost(User user, Thred thred, Post post, HttpServletRequest request){
        boolean posttotwitter = false;
        String posttotwitterStr = request.getParameter("threadid" + thred.getThredid()+"posttotwitter");
        if (posttotwitterStr!=null && posttotwitterStr.trim().equals("1")){
            posttotwitter = true;
        }
        boolean posttopingfm = false;
        String posttopingfmStr = request.getParameter("threadid" + thred.getThredid()+"posttopingfm");
        if (posttopingfmStr!=null && posttopingfmStr.trim().equals("1")){
            posttopingfm = true;
        }
        //Do post
        doAfterPost(user, thred, post, posttotwitter, posttopingfm);
    }

    public static void doAfterPost(User user, Thred thred, Post post, boolean posttotwitter, boolean posttopingfm){
        Logger logger = Logger.getLogger(AfterPostingTodo.class);
        try{
            //Clear the Javascript Embed cache
            String nameInCache="embedjavascriptservlet-u" + user.getUserid() + "-makeHttpsIfSSLIsOn" + false;
            String cacheGroup="embedjavascriptcache" + "/";
            CacheFactory.getCacheProvider().flush(nameInCache, cacheGroup);
            String nameInCacheVert="embedjavascriptverticalservlet-u" + user.getUserid() + "-makeHttpsIfSSLIsOn" + false;
            String cacheGroupVert="embedjavascriptcache" + "/";
            CacheFactory.getCacheProvider().flush(nameInCacheVert, cacheGroupVert);
            //Update twitter
            if (thred.getIstwitterupdateon() && posttotwitter) {
                TwitterUpdate tu = new TwitterUpdate(thred.getTwitterid(), thred.getTwitterpass(), Str.truncateString(post.getContents(), 140));
                tu.update();
            }
            //Update Ping.fm
            if (thred.getIspingfmupdateon() && posttopingfm) {
                PingfmUpdate pu = new PingfmUpdate(thred.getPingfmapikey(), post.getContents(), thred.getThredid());
                pu.update();
            }
        } catch (Exception ex){
            logger.error(ex);
        }
    }



}
