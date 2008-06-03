package com.mythredz.cachedstuff;


import com.mythredz.dao.User;
import com.mythredz.dao.hibernate.HibernateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Iterator;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class NewestUsers implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    public String getKey() {
        return "NewestUsers";
    }

    public void refresh() {
        StringBuffer out = new StringBuffer();

        out.append("<table cellpadding='3' cellspacing='0' border='0'>");
        List<User> users = HibernateUtil.getSession().createCriteria(User.class)
                                           .add(Restrictions.eq("isenabled", true))
                                           .addOrder(Order.desc("userid"))
                                           .setCacheable(true)
                                           .setMaxResults(20)
                                           .list();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            out.append("<tr>");
            out.append("<td>");
            out.append("<a href=\"/profile.jsp?userid="+user.getUserid()+"\">");
            out.append("<font class='tinyfont'>");
            out.append(user.getFirstname()+" "+user.getLastname());
            out.append("</font>");
            out.append("</a>");
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
        return 5;
    }

    public String getHtml() {
        return html;
    }
}
