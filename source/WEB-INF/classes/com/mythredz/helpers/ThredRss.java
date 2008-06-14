package com.mythredz.helpers;


import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.FeedException;
import com.mythredz.systemprops.BaseUrl;
import com.mythredz.dao.Blogpost;
import com.mythredz.dao.Thred;
import com.mythredz.dao.User;
import com.mythredz.dao.Post;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.htmluibeans.PublicBlog;
import com.mythredz.util.Num;
import com.mythredz.util.Str;

public class ThredRss extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());


        Thred thred = null;
        if (request.getParameter("thredid")!=null && Num.isinteger(request.getParameter("thredid"))){
            thred = Thred.get(Integer.parseInt(request.getParameter("thredid")));
        }

        if (thred==null || thred.getThredid()==0){
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write("Thred not found.".getBytes());
            outStream.close();
            return;
        }

        User user = User.get(thred.getUserid());


        StringBuffer fd = new StringBuffer();

        //Create Rome Feed Object
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");

        //Channel Title
        String channelTitle = thred.getName();
        feed.setTitle(channelTitle);

        //Channel Description
        String channelDesc = "";
        feed.setDescription(channelDesc);

        //Channel Link
        String channelLink = BaseUrl.get(false)+"user/"+user.getNickname()+"/";
        feed.setLink(channelLink);

        //Create Items
        List entries = new ArrayList();

        List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                .add(Restrictions.eq("thredid", thred.getThredid()))
                .addOrder(Order.desc("date"))
                .setMaxResults(25)
                .setCacheable(true)
                .list();
        for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
            Post blogpost=iterator1.next();

             //Create a Rome RSS Item
            SyndEntry entry = new SyndEntryImpl();

            //Date
            entry.setPublishedDate(blogpost.getDate());

            //Item Link
            entry.setLink(BaseUrl.get(false)+"user/"+user.getNickname()+"/?thredid="+thred.getThredid()+"&postid="+blogpost.getPostid());

            //Item title
            String itemTitle = Str.truncateString(blogpost.getContents(), 30);
            entry.setTitle(itemTitle);

            //Item Description
            //String bodyTmp = blogpost.getContents().replaceAll( PublicBlog.CARRIAGERETURN + PublicBlog.LINEBREAK, "<br>");
            String bodyTmp = blogpost.getContents();
            SyndContent desc = new SyndContentImpl();
            desc.setType("text/html");
            desc.setValue(bodyTmp);
            entry.setDescription(desc);

            //Item Author
            entry.setAuthor(user.getNickname());

            //Add the item to the list of items
            entries.add(entry);
       }


        //Add the list of items to the rss/channel
        feed.setEntries(entries);

        //Start the output of the feed
        try{
            SyndFeedOutput feedOut = new SyndFeedOutput();
            fd.append(feedOut.outputString(feed));
            logger.debug("Successfully output Rome RSS Feed");
        } catch (FeedException fe) {
            logger.error("Rome RSS Feed died:" + fe.toString(), fe);
        } catch (Exception e){
            logger.error("", e);
        }


        //Debug
        //logger.debug("Rss Feed=" + fd.toString());

        ServletOutputStream outStream = response.getOutputStream();
        response.setContentType("text/xml");
        outStream.write(fd.toString().getBytes());
        outStream.close();


    }






}
