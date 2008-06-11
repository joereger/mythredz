package com.mythredz.timeline;

import com.mythredz.dao.User;
import com.mythredz.dao.Thred;
import com.mythredz.dao.Post;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

/**
 * User: joereger
 * Date: Jun 11, 2008
 * Time: 9:16:38 AM
 */
public class TimelineObjectFinder {

    public static ArrayList<TimelineObject> getTimelineObjects(User user, Calendar startdate, Calendar enddate){
        ArrayList<TimelineObject> out = new ArrayList<TimelineObject>();

        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", user.getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                .add(Restrictions.eq("thredid", thred.getThredid()))
                .addOrder(Order.desc("date"))
                .setMaxResults(4000)
                .setCacheable(true)
                .list();
            for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                Post post=iterator1.next();

                TimelineObjectSimple tos = new TimelineObjectSimple();
                tos.setPost(post);
                tos.setThred(thred);
                tos.setX1(0);
                tos.setX2(0);
                tos.setY1(0);
                tos.setY2(0);
            }
        }
        return out;
    }

}
