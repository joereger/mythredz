package com.mythredz.htmluibeans;

import com.mythredz.dao.Blogpost;
import com.mythredz.dao.hibernate.HibernateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Sep 21, 2006
 * Time: 8:47:39 AM
 */
public class PublicBlog implements Serializable {

    List<Blogpost> blogposts;
    public static String LINEBREAK = "\\n";
    public static String CARRIAGERETURN = "\\r";

    public PublicBlog(){
    }



    public void initBean(){
        blogposts = HibernateUtil.getSession().createQuery("from Blogpost order by date DESC").setCacheable(true).setMaxResults(50).list();
        for (int i = 0; i < blogposts.size(); i++) {
            Blogpost blogpost = blogposts.get(i);
            String bodyTmp = blogpost.getBody().replaceAll( CARRIAGERETURN + LINEBREAK, "<br>");
            blogpost.setBody(bodyTmp);
        }
    }


    public List<Blogpost> getBlogposts() {
        return blogposts;
    }

    public void setBlogposts(List<Blogpost> blogposts) {
        this.blogposts = blogposts;
    }
}
