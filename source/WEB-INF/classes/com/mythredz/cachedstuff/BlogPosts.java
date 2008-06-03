package com.mythredz.cachedstuff;

import com.mythredz.htmluibeans.SurveyListItem;
import com.mythredz.htmluibeans.PublicBlog;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.dao.Blogpost;
import com.mythredz.util.DateDiff;
import com.mythredz.util.Time;
import com.mythredz.util.Str;

import java.io.Serializable;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class BlogPosts implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    public String getKey() {
        return "BlogPosts";
    }

    public void refresh() {
        StringBuffer out = new StringBuffer();

        out.append("<table cellpadding='3' cellspacing='0' border='0' width='100%'>");
        List<Blogpost> blogposts = HibernateUtil.getSession().createQuery("from Blogpost order by date DESC").setCacheable(true).setMaxResults(25).list();
        for (int i = 0; i < blogposts.size(); i++) {
            Blogpost blogpost = blogposts.get(i);
            //String bodyTmp = blogpost.getBody().replaceAll( PublicBlog.CARRIAGERETURN + PublicBlog.LINEBREAK, "<br>");
            //blogpost.setBody(bodyTmp);
            Calendar cal = Time.getCalFromDate(blogpost.getDate());
            cal = Time.convertFromOneTimeZoneToAnother(cal, cal.getTimeZone().getID(), "GMT");
            String ago = Time.agoText(cal);
            out.append("<tr>");
            out.append("<td>");
            out.append("<font class='tinyfont'>");
            out.append(ago);
            out.append("</font>");
            out.append("</td>");
            out.append("<td>");
            out.append("<font class='tinyfont'>");
            out.append("<a href=\"/blogpost.jsp?blogpostid="+blogpost.getBlogpostid()+"\">");
            out.append(blogpost.getTitle());
            out.append("</a>");
            out.append("</font>");
            out.append("</td>");
            out.append("</tr>");
        }
        out.append("</table>");

        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 30;
    }

    public String getHtml() {
        return html;
    }
}
